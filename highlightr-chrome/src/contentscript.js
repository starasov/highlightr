var streams = {};
var extensionUrl = chrome.extension.getURL("");
//var highlightrServiceUrl = "http://high.herokuapp.com";
var highlightrServiceUrl = "http://localhost:8080";

console.log("highlightr-cs-init: " + extensionUrl);

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
            console.log("[retrieveStream][success] data: " + data + ", textStatus: " + textStatus);
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
                console.log("[retrieveRanks][success] data: " + data.rank + ",- textStatus: " + textStatus);
                item.status = 'ok';
                item.rank = data.rank;
                updateRank(item);
            });
        } else {
            updateRank(item);
        }
    });
}

function updateRank(item) {
    $("#entries > div.entry").each(function() {
        var highlighted = $(this).data("highlighted");
        var targetUrl = $("a.entry-original", this).attr("href");
        if (!highlighted && isUrl(targetUrl) && targetUrl == item.id) {
            var stream = streams[item.streamId];
            var stats = stream.stats;

            var rankRate = item.rank / stats.avg;
            if (isFinite(rankRate)) {
                if (rankRate >= 2.5) {
                    $(".entry-title", this).append("&nbsp;<span style='-webkit-border-radius: 6px; padding: 4px; width: 20px; height: 20px; background-color: #d14836; font-size: small; color: #ffffff;'>+" + rankRate.toFixed(1) + "</span>");
                } else if (rankRate < 0.2) {
                    $(".entry-title", this).css('color', "#999999");
                } else if (rankRate >= 1.0) {
                    $(".entry-title", this).append("&nbsp;<span style='-webkit-border-radius: 6px; padding: 4px; width: 20px; height: 20px; border: solid 1px #d14836; font-size: small; color: #d14836;'>+" + rankRate.toFixed(1) + "</span>");
                }
            } else {
                $(".entry-title", this).append("&nbsp;<span style='-webkit-border-radius: 6px; padding: 4px; width: 20px; height: 20px; border: solid 1px #d14836; font-size: small; color: #d14836;'>?</span>");
            }

            $(this).data("highlighted", true);
        }
    });
}

function updateStream(feedRawData) {
    var feedData = jQuery.parseJSON(feedRawData);

    var streamId = parseUrlFromStream(feedData.id);
    console.log("HighlightrXhr - streamId - %s", streamId);

    streams[streamId] = streams[streamId] || {stats: {}, items: {}};
    console.log("HighlightrXhr - stream - %s", streams[streamId]);

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

$("#highlightr-bridge").bind("HighlightrXhr", function() {
    console.log("HighlightrXhr - xhr event handled");

    var url = localStorage['highlightr.activeUrl'];
    var feedRawData = localStorage['highlightr.response'];

    if (isFeedUrl(url) && feedRawData) {
        var streamId = updateStream(feedRawData);
        retrieveStream(streamId);
    }
});

