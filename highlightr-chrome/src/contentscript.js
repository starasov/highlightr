var debug = false;

var streams = {};
var activeStreamId = null;

var extensionUrl = chrome.extension.getURL("");
var highlightrServiceUrl = "http://ec2-23-22-38-192.compute-1.amazonaws.com";

debug && console.log("highlightr-cs-init: " + extensionUrl);

function injectScript(file) {
    var script = document.createElement("script");
    var parent = document.documentElement;

    script.src = extensionUrl + file;
    script.setAttribute("id", "highlightr-bridge");
    parent.appendChild(script);
}

function isFeedUrl(str) {
    var prefix = "/reader/api/0/stream/contents/feed/";
    return str && str.substring(0, prefix.length) === prefix;
}

function parseUrlFromStream(stream) {
    var prefix = "feed/";
    if (stream && stream.substring(0, prefix.length) === prefix) {
        return stream.substring(prefix.length)
    }

    return stream;
}

function retrieveStream(streamId) {
    if (!streams[streamId].avg) {
        $.get(highlightrServiceUrl + "/api/rank/url/stats", {"stream": streamId}, function(data, textStatus) {
            debug && console.log("[retrieveStream][success] data: " + data + ", textStatus: " + textStatus);
            streams[streamId].stats = data;
            retrieveRanks(streamId);
        }, 'json');
    } else {
        retrieveRanks(streamId);
    }
}

function retrieveRanks(streamId) {
    jQuery.each(streams[streamId].items, function(id, item) {
        if (item.status == 'pending') {
            $.get(highlightrServiceUrl + "/api/rank/url/stream", {"stream": streamId, "query": id}, function(data, textStatus) {
                debug && console.log("[retrieveRanks][success] data: " + data.rank + ",- textStatus: " + textStatus);
                item.rank = data.rank;
                item.status = 'ok';
                updateRank(item);
            }, 'json');
        } else {
            updateRank(item);
        }
    });
}

function updateRank(item) {
    if (item.target && isStreamActive(item.streamId)) {
        var target = item.target;
        item.target = null;

        var stream = streams[item.streamId];
        var stats = stream.stats;

        $('span.highlightr', target).remove();

        var rankRate = item.rank / stats.avg;
        if (isFinite(rankRate)) {
            if (rankRate >= 2.5) {
                $(".entry-title", target).append("<span class='highlightr' style='-webkit-border-radius: 3px; margin-left: 8px; padding: 2px 3px 2px 3px; width: 20px; height: 18px; background-color: #d14836; font-size: small; color: #ffffff;'>" + rankRate.toFixed(1) + "</span>");
            } else if (rankRate < 0.2) {
                $(".entry-title", target).css('color', "#999999").addClass('highlightr-grayed');
                $(".entry-title-link", target).css('color', "#999999").addClass('highlightr-grayed');
            } else if (rankRate >= 1.0) {
                $(".entry-title", target).append("<span class='highlightr' style='-webkit-border-radius: 3px; margin-left: 8px; padding: 2px 3px 2px 3px; width: 20px; height: 18px; border: solid 1px #d14836; font-size: small; color: #d14836;'>" + rankRate.toFixed(1) + "</span>");
            }
        }
    }
}

function updateStream(feedRawData) {
    var feedData = jQuery.parseJSON(feedRawData);

    var streamId = parseUrlFromStream(feedData.id);
    debug && console.log("HighlightrXhr - streamId - %s", streamId);

    streams[streamId] = streams[streamId] || {stats: {}, items: {}};
    debug && console.log("HighlightrXhr - stream - %s", streams[streamId]);

    jQuery.each(feedData.items, function(index, item) {
        var items = streams[streamId].items;
        var itemId = item.alternate[0].href;
        if (!items[itemId]) {
            items[itemId] = {
                id: itemId,
                streamId: streamId,
                status: 'pending',
                title: item.title
            };
        }
    });

    return streamId;
}

function isStreamActive(streamId) {
    var disabled = localStorage['highlightr/' + streamId + '/disabled'] || 'false';
    return streamId && disabled == 'false';
}

function disableStream(streamId) {
    localStorage['highlightr/' + streamId + '/disabled'] = 'true';
}

function enableStream(streamId) {
    localStorage['highlightr/' + streamId + '/disabled'] = 'false';
}

function updateToggleButtonState() {
    if (!activeStreamId) {
        $('#highlight-toggle').addClass('jfk-button-disabled');
    } else {
        $('#highlight-toggle').removeClass('jfk-button-disabled');
    }

    if (isStreamActive(activeStreamId)) {
        $('#highlight-toggle').addClass('jfk-button-checked');
    } else {
        $('#highlight-toggle').removeClass('jfk-button-checked');
    }
}

function toggleHighlightr() {
    if (isStreamActive(activeStreamId)) {
        disableStream(activeStreamId);
        $('span.highlightr').css('display', 'none');
        $('.highlightr-grayed').css('color', '');
    } else {
        enableStream(activeStreamId);
        retrieveStream(activeStreamId);
        $('span.highlightr').css('display', 'inline');
        $('.highlightr-grayed').css('color', '#999999');
    }

    updateToggleButtonState();
}

injectScript('xhr.js');

function updateItemTargetIfNeeded(targetUrl, element) {
    var stream = streams[activeStreamId];
    if (stream && targetUrl) {
        var item = stream.items[targetUrl];
        item.target = element;
        if (item.status == 'ok') {
            updateRank(item);
        }
    }
}

function handleExpandedView(element, activeStreamId) {
    var locked = false;

    $(element).bind("DOMNodeInserted", function(event) {
        if (!locked) {
            locked = true;
            var targetUrl = $("a.entry-title-link", element).attr("href");
            debug && console.log("[handleExpandedView][DOMNodeInserted][%s] - %s, targetUrl - %s", activeStreamId, element, targetUrl);
            updateItemTargetIfNeeded(targetUrl, element);
            locked = false;
        }
    });
}

$(document).ready(function() {
    $.ajaxSetup({
        'beforeSend': function(xhr) { xhr.setRequestHeader("Accept", "application/json"); }
    });

    var html = '<div id="highlight-toggle" role="button" class="goog-inline-block jfk-button jfk-button-standard jfk-button-narrow" tabindex="0" aria-pressed="true" style="-webkit-user-select: none;" title="Turns Highlightr ON and OFF"><img src="' + extensionUrl + '/toggle-icon.png" style="width: 21px; height: 21px;" class="jfk-button-img"></div>';
    $('#stream-view-options-container').prepend(html);
    $('#highlight-toggle').click(toggleHighlightr).hover(function() {
        $(this).toggleClass('jfk-button-hover');
    });

    $("#entries").bind("DOMNodeInserted", function(event) {
        var element = event.target;
        if ($(element).hasClass('entry')) {
            var targetUrl = $("a.entry-original", element).attr("href");
            if (!targetUrl) {
                handleExpandedView(element, activeStreamId);
            } else {
                debug && console.log("[DOMNodeInserted][%s] - %s, targetUrl - %s", activeStreamId, element, targetUrl);
                updateItemTargetIfNeeded(targetUrl, element);
            }
        }
    });

    updateToggleButtonState();
});

$("#highlightr-bridge").bind("HighlightrXhr", function() {
    debug && console.log("HighlightrXhr - xhr event handled");

    var url = localStorage['highlightr.activeUrl'];
    var feedRawData = localStorage['highlightr.response'];

    if (isFeedUrl(url) && feedRawData) {
        var streamId = updateStream(feedRawData);
        activeStreamId = streamId;

        updateToggleButtonState();

        if (isStreamActive(activeStreamId)) {
            retrieveStream(streamId);
        }
    }
});
