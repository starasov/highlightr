package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.model.Rank;

/**
 * User: starasov
 * Date: 1/13/12
 * Time: 2:16 PM
 */
public class NullRankFinder<S> implements RankFinder<S> {
    private int rank = 1;

    public Rank get(S source) {
        return new Rank(rank++, source.toString());
    }
}
