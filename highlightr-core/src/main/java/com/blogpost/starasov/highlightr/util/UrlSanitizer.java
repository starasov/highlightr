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
}
