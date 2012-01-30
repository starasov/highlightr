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

    @Test
    public void shouldCleanupUrlWithQueryParameters() throws MalformedURLException {
        URL url = new URL("http://java.dzone.com/articles/assignment-algorithms-improve?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+javalobby%2Ffrontpage+%28Javalobby+%2F+Java+Zone%29");
        URL cleanedUrl = UrlSanitizer.cleanQuery(url);
        assertThat(cleanedUrl.toString(), is("http://java.dzone.com/articles/assignment-algorithms-improve/"));
    }

    @Test
    public void shouldCleanupUrlWithoutQueryParameters() throws MalformedURLException {
        URL url = new URL("http://java.dzone.com/articles/assignment-algorithms-improve");
        URL cleanedUrl = UrlSanitizer.cleanQuery(url);
        assertThat(cleanedUrl.toString(), is("http://java.dzone.com/articles/assignment-algorithms-improve/"));
    }

    @Test
    public void shouldCleanupUrlWithPort() throws MalformedURLException {
        URL url = new URL("http://java.dzone.com:8080/articles/assignment-algorithms-improve");
        URL cleanedUrl = UrlSanitizer.cleanQuery(url);
        assertThat(cleanedUrl.toString(), is("http://java.dzone.com:8080/articles/assignment-algorithms-improve/"));
    }

    @Test
    public void shouldCleanupUrlWithProtocol() throws MalformedURLException {
        URL url = new URL("https://java.dzone.com/articles/assignment-algorithms-improve");
        URL cleanedUrl = UrlSanitizer.cleanQuery(url);
        assertThat(cleanedUrl.toString(), is("https://java.dzone.com/articles/assignment-algorithms-improve/"));
    }
}
