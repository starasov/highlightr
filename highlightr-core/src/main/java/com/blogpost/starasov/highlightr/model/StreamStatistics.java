package com.blogpost.starasov.highlightr.model;

import org.springframework.util.Assert;

/**
 * @author starasov
 */
public class StreamStatistics {
    private final Stream stream;
    private final Statistics statistics;

    public StreamStatistics(Stream stream, Statistics statistics) {
        Assert.notNull(stream, "stream parameter can't be null.");
        Assert.notNull(statistics, "statistics parameter can't be null.");

        this.stream = stream;
        this.statistics = statistics;
    }

    public Stream getStream() {
        return stream;
    }

    public Statistics getStatistics() {
        return statistics;
    }
}
