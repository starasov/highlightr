console.log("xhr - init");

var _XMLHttpRequest = XMLHttpRequest;
window.XMLHttpRequest = function() {
    console.log("xhr - created");

    var xhr = new _XMLHttpRequest();
    var activeUrl = null;

    var _open = xhr.open;
    xhr.open = function(method, url, async, user, password) {
        console.log("xhr - open - " + url);
        activeUrl = url;
        _open.apply(xhr, arguments);
    };

    var _send = xhr.send;
    xhr.send = function() {
        console.log("xhr - send");

        var _onreadystatechange = xhr.onreadystatechange;
        if (_onreadystatechange) {
            xhr.onreadystatechange = function() {
                _onreadystatechange.apply(xhr, arguments);
                if (xhr.readyState == 4) {
                    console.log("xhr - done - firing event");

                    var event = document.createEvent('Events');
                    event.initEvent('HighlightrXhr', true, true);

                    var element = document.getElementById("highlightr-bridge");

                    localStorage['highlightr.response'] = xhr.responseText;
                    localStorage['highlightr.activeUrl'] = activeUrl;

                    element.dispatchEvent(event);
                }
            };
        }

        _send.apply(xhr, arguments);
    };

    return xhr;
};
