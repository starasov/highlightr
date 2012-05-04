package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.Statistics;
import com.blogpost.starasov.highlightr.model.StreamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.net.URL;

/**
 * User: starasov
 * Date: 1/18/12
 * Time: 8:23 PM
 */

@Transactional(readOnly = false)
public class TrackingService<S> {
    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);

    private RankService<S> rankService;
    private StreamService streamService;
    private StreamType streamType;

    @Cacheable(value="tracking")
    public Rank trackSource(S source, URL streamUrl) {
        Assert.notNull(source, "source parameter can't be null.");
        Assert.notNull(streamUrl, "streamUrl parameter can't be null.");

        logger.debug("[trackSource] - source: {}, streamUrl: {}", source, streamUrl);

        String streamIdentifier = Stream.buildIdentifier(streamUrl);
        Rank existingRank = rankService.findRank(source, streamIdentifier, streamType);

        Rank newRank = rankService.fetchRank(source);
        existingRank.update(newRank);

        streamService.updateStream(streamIdentifier, streamType, existingRank);

        return existingRank;
    }

    @Transactional(readOnly = true)
    @Cacheable(value="tracking")
    public Statistics getStreamStatistics(URL streamUrl) {
        Assert.notNull(streamUrl, "streamUrl parameter can't be null.");

        String streamIdentifier = Stream.buildIdentifier(streamUrl);
        return streamService.findStreamStatistics(streamIdentifier, streamType);
    }

    @Required
    public void setStreamType(StreamType streamType) {
        this.streamType = streamType;
    }

    @Required
    public void setRankService(RankService<S> rankService) {
        this.rankService = rankService;
    }

    @Autowired
    public void setStreamService(StreamService streamService) {
        this.streamService = streamService;
    }
}
