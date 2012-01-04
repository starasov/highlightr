package com.blogpost.starasov.highlightr.rank;

import java.util.Map;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 7:26 AM
 */
public class TopicRankAggregator extends RankAggregator<String> {
    public TopicRankAggregator(Map<String, Rank<String>> ranks, Map<String, Integer> rankWeights) {
        super(ranks, rankWeights);
    }
}
