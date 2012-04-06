var debug = true;

var streams = {};
var activeStreamId = null;

var extensionUrl = chrome.extension.getURL("");
var highlightrServiceUrl = "http://highlightr.jelastic.dogado.eu";

debug && console.log("highlightr-cs-init: " + extensionUrl);

function injectScript(file) {
    var script = document.createElement("script");
    var parent = document.documentElement;

    script.src = extensionUrl + file;
    script.setAttribute("id", "highlightr-bridge");
    parent.appendChild(script);
}

function isUrl(str) {
    var http = "http";
    return str.substring(0, http.length) === http;
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
        });
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
            });
        } else {
            updateRank(item);
        }
    });
}

function updateRank(item) {
    if (item.target) {
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
                $(".entry-title", target).css('color', "#999999");
                $(".entry-title-link", target).css('color', "#999999");
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
});

$("#highlightr-bridge").bind("HighlightrXhr", function() {
    debug && console.log("HighlightrXhr - xhr event handled");

    var url = localStorage['highlightr.activeUrl'];
    var feedRawData = localStorage['highlightr.response'];

    if (isFeedUrl(url) && feedRawData) {
        var streamId = updateStream(feedRawData);
        activeStreamId = streamId;
        retrieveStream(streamId);
    }
});
