package com.blogpost.starasov.highlightr.util;

import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * User: starasov
 * Date: 12/30/11
 * Time: 3:52 PM
 */
public final class UrlSanitizer {
    private UrlSanitizer() {
    }

    public static URL sanitize(URL url) {
        Assert.notNull(url, "url parameter can't be null.");
        try {
            String sanitizedUrl = url.toString();
            String path = url.getPath();
            if (StringUtils.isEmpty(path) || !path.endsWith("/")) {
                sanitizedUrl += "/";
            }

            return new URL(sanitizedUrl);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static URL cleanQuery(URL url) {
        Assert.notNull(url, "url parameter can't be null.");

        try {
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            String path = url.getPath();

            StringBuilder builder = new StringBuilder();
            builder.append(protocol).append("://");
            builder.append(host);
            if (port != -1) {
                builder.append(':').append(port);
            }

            builder.append(path);

            return sanitize(new URL(builder.toString()));
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }
}
