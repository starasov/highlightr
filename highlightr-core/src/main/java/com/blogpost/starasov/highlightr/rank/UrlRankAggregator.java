package com.blogpost.starasov.highlightr.rank;

import java.net.URL;
import java.util.Map;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 6:50 AM
 */
public class UrlRankAggregator extends RankAggregator<URL> {
    public UrlRankAggregator(Map<String, Rank<URL>> ranks, Map<String, Integer> rankWeights) {
        super(ranks, rankWeights);
    }
}
