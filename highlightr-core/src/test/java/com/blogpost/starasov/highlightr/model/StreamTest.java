package com.blogpost.starasov.highlightr.model;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: starasov
 * Date: 1/13/12
 * Time: 1:54 PM
 */
public class StreamTest {
    @Test
    public void shouldBuildIdentifierFromUrlWithEmptyPath() throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        Stream stream = Stream.fromUrlAndType(url, StreamType.URL);
        assertThat(stream.getIdentifier(), is("www.google.com/"));
    }

    @Test
    public void shouldBuildIdentifierFromUrlWithPath() throws MalformedURLException {
        URL url = new URL("http://www.google.com/test/me");
        Stream stream = Stream.fromUrlAndType(url, StreamType.URL);
        assertThat(stream.getIdentifier(), is("www.google.com/test/me/"));
    }

    @Test
    public void shouldBuildIdentifierFromUrlWithQuery() throws MalformedURLException {
        URL url = new URL("http://www.google.com/test/me/?test=1");
        Stream stream = Stream.fromUrlAndType(url, StreamType.URL);
        assertThat(stream.getIdentifier(), is("www.google.com/test/me/"));
    }
}
