package com.blogpost.starasov.highlightr.matcher;

import org.springframework.util.Assert;

import java.net.URL;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 6:15 PM
 */
public class UrlMatcher implements SourceMatcher<URL> {
    private final String host;
    private final String path;

    public UrlMatcher(String host, String path) {
        Assert.hasLength(host, "host parameter can't be null or empty.");
        Assert.hasLength(path, "path parameter can't be null or empty.");

        this.host = host;
        this.path = path;
    }

    public boolean isMatched(URL source) {
        Assert.notNull(source, "source parameter can't be null.");
        String host = source.getHost();
        String path = source.getPath();

        return this.host.equals(host) && path.contains(this.path);
    }
}
