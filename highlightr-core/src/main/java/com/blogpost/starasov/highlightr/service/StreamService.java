package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamStatistics;
import com.blogpost.starasov.highlightr.model.StreamType;
import com.blogpost.starasov.highlightr.repository.RankRepository;
import com.blogpost.starasov.highlightr.repository.StreamRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

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

    public StreamStatistics findStreamStatistics(final String streamIdentifier, final StreamType type) {
        Assert.notNull(streamIdentifier, "streamIdentifier parameter can't be null.");
        Assert.notNull(type, "type parameter can't be null.");

        final DateTime lastDate = new DateTime().minusDays(15);

        Page<Rank> ranksPage = rankRepository.findAll(new Specification<Rank>() {
            public Predicate toPredicate(Root<Rank> rankRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(
                        cb.equal(rankRoot.get("stream").get("identifier"), streamIdentifier),
                        cb.equal(rankRoot.get("stream").get("type"), type),
                        cb.greaterThan(rankRoot.get("timestamp").as(Date.class), lastDate.toDate()));
            }
        }, new PageRequest(0, 1000, new Sort(Sort.Direction.DESC, "rank")));

        List<Rank> ranks = ranksPage.getContent();
        double averageRank = ranks.isEmpty() ? 0.0 : Rank.toAverageRank(ranks);
        double maxRank = ranks.isEmpty() ? 0.0 : ranks.get(0).getRank();
        double minRank = ranks.isEmpty() ? 0.0 : ranks.get(ranks.size() - 1).getRank();

        return new StreamStatistics(averageRank, minRank, maxRank);

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
