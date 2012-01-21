package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import com.blogpost.starasov.highlightr.repository.RankRepository;
import com.blogpost.starasov.highlightr.repository.StreamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.net.URL;

/**
 * User: starasov
 * Date: 1/6/12
 * Time: 7:53 AM
 */
@Service
@Transactional(readOnly = true)
public class StreamService {
    private static final Logger logger = LoggerFactory.getLogger(StreamService.class);

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private RankRepository rankRepository;

    @Transactional(readOnly = false)
    public void updateStream(String streamIdentifier, StreamType streamType, Rank rank) {
        Assert.notNull(rank, "rank parameter can't be null.");
        Assert.hasLength(streamIdentifier, "streamIdentifier parameter can't be null or empty.");
        Assert.notNull(streamType, "streamType parameter can't be null.");

        Rank existingRank = rankRepository.findByIdentifierAndStream(rank.getIdentifier(), streamIdentifier, streamType);
        if (existingRank != null) {
            updateSourceRank(rank, existingRank);
        } else {
            Stream stream = findStream(streamIdentifier, streamType);
            createSourceRank(rank, stream);
        }

        logger.debug("[updateStream] - end");
    }

    public Stream findStream(String streamIdentifier, StreamType type) {
        Assert.hasLength(streamIdentifier, "streamIdentifier parameter can't be null or empty.");
        Assert.notNull(type, "type parameter can't be null.");

        Stream stream = streamRepository.findByIdentifierAndType(streamIdentifier, type);
        Stream resultingStream = stream == null ? new Stream(streamIdentifier, type) : stream;

        logger.debug("[getStream] - resultingStream: {}", resultingStream);
        return resultingStream;
    }

    private void updateSourceRank(Rank rank, Rank existingRank) {
        logger.debug("[updateSourceRank] - begin - rank: {}, existingRank: {}", rank, existingRank);

        existingRank.setRank(rank.getRank());
        rankRepository.save(existingRank);

        logger.debug("[updateSourceRank] - end");
    }

    private void createSourceRank(Rank rank, Stream stream) {
        logger.debug("[createSourceRank] - rank: {}, stream: {}", rank, stream);

        stream.addRank(rank);
        streamRepository.save(stream);

        logger.debug("[createSourceRank] - end");
    }
}
