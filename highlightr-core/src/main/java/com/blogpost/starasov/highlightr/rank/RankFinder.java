package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.model.Rank;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 7:22 AM
 */
public interface RankFinder<S> {
    Rank get(S source);
}
