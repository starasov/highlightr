package com.blogpost.starasov.highlightr.util;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: starasov
 * Date: 12/30/11
 * Time: 3:53 PM
 */
public class UrlSanitizerTest {
    @Test
    public void shouldAppendTrailingSlashWhenPathIsEmpty() throws MalformedURLException {
        URL url = UrlSanitizer.sanitize(new URL("http://www.google.com"));
        assertThat(url.toString(), is("http://www.google.com/"));
    }

    @Test
    public void shouldNotAppendTrailingSlashWhenPathIsNotEmpty() throws MalformedURLException {
        URL url = UrlSanitizer.sanitize(new URL("http://www.google.com/"));
        assertThat(url.toString(), is("http://www.google.com/"));
    }

    @Test
    public void shouldNotAppendTrailingSlashWhenPathIsComplex() throws MalformedURLException {
        URL url = UrlSanitizer.sanitize(new URL("http://www.google.com/docs/index.html"));
        assertThat(url.toString(), is("http://www.google.com/docs/index.html/"));
    }
}
