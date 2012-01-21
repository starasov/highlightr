package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.identifier.SourceIdentifierBuilder;
import com.blogpost.starasov.highlightr.model.Rank;
import org.springframework.util.Assert;

/**
 * User: starasov
 * Date: 1/8/12
 * Time: 6:43 PM
 */
public abstract class BaseRankFinder<S> implements RankFinder<S> {
    private final SourceIdentifierBuilder<S> identifierBuilder;

    protected BaseRankFinder(SourceIdentifierBuilder<S> identifierBuilder) {
        Assert.notNull(identifierBuilder, "identifierBuilder parameter can't be null.");
        this.identifierBuilder = identifierBuilder;
    }

    protected Rank createRankFrom(int rank, S source) {
        return new Rank(rank, identifierBuilder.build(source));
    }
}
