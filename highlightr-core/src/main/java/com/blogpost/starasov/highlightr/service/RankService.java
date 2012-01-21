package com.blogpost.starasov.highlightr.service;

import com.blogpost.starasov.highlightr.identifier.SourceIdentifierBuilder;
import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import com.blogpost.starasov.highlightr.rank.RankFinder;
import com.blogpost.starasov.highlightr.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * User: starasov
 * Date: 1/11/12
 * Time: 7:19 PM
 */
public class RankService<S> {

    @Autowired
    private RankRepository rankRepository;

    private RankFinder<S> rankFinder;
    private SourceIdentifierBuilder<S> identifierBuilder;

    @Transactional(readOnly = true)
    public Rank findAverageRank(S source) {
        Assert.notNull(source, "source parameter can't be null.");
        String identifier = identifierBuilder.build(source);

        List<Rank> ranks = rankRepository.findByIdentifier(identifier);
        return new Rank(Rank.toAverageRank(ranks), identifier);
    }

    @Transactional(readOnly = true)
    public Rank findRank(S source, String streamIdentifier, StreamType streamType) {
        Assert.notNull(source, "source parameter can't be null.");

        String identifier = identifierBuilder.build(source);
        Rank storedRank = rankRepository.findByIdentifierAndStream(identifier, streamIdentifier, streamType);
        if (storedRank == null) {
            storedRank = new Rank();
        }

        return storedRank;
    }

    public Rank fetchRank(S source) {
        Assert.notNull(source, "source parameter can't be null.");
        return rankFinder.get(source);
    }

    @Required
    public void setRankFinder(RankFinder<S> rankFinder) {
        this.rankFinder = rankFinder;
    }

    @Required
    public void setIdentifierBuilder(SourceIdentifierBuilder<S> identifierBuilder) {
        this.identifierBuilder = identifierBuilder;
    }
}
