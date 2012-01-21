package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.net.URL;
import java.util.Date;

/**
 * User: starasov
 * Date: 1/18/12
 * Time: 8:23 PM
 */

public class TrackingService<S> {
    private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);

    private RankService<S> rankService;
    private StreamService streamService;
    private StreamType streamType;

    public Rank trackSource(S source, URL streamUrl) {
        Assert.notNull(source, "source parameter can't be null.");
        Assert.notNull(streamUrl, "streamUrl parameter can't be null.");

        String streamIdentifier = Stream.buildIdentifier(streamUrl);
        Rank rank = rankService.findRank(source, streamIdentifier, streamType);
        if (!rank.isUpToDate(new Date())) {
            logger.debug("[trackSource] - rank is out of date.");

            Rank newRank = rankService.fetchRank(source);
            rank.update(newRank);

            streamService.updateStream(streamIdentifier, streamType, rank);
        }

        return rank;
    }

    @Transactional(readOnly = true)
    public int getAverageRankForStream(URL streamUrl) {
        Assert.notNull(streamUrl, "streamUrl parameter can't be null.");

        String streamIdentifier = Stream.buildIdentifier(streamUrl);
        Stream stream = streamService.findStream(streamIdentifier, streamType);
        return stream.getAverageRank();
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
