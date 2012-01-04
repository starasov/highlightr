package com.blogpost.starasov.highlightr.rank;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 7:20 AM
 */
public class RankAggregator<S> {
    private static final Logger logger = LoggerFactory.getLogger(UrlRankAggregator.class);

    private final Map<String, Rank<S>> ranks;
    private final Map<String, Integer> rankWeights;

    public RankAggregator(Map<String, Rank<S>> ranks, Map<String, Integer> rankWeights) {
        Assert.notNull(ranks, "ranks parameter can't be null.");
        Assert.notNull(rankWeights, "rankWeights parameter can't be null.");

        this.ranks = ranks;
        this.rankWeights = rankWeights;
    }

    public int getAggregatedRank(S source) {
        logger.trace("[getAggregatedRank] - begin - source: {}", source);

        SummaryStatistics statistics = new SummaryStatistics();
        for (Map.Entry<String, Rank<S>> urlRankEntry : ranks.entrySet()) {
            Rank<S> urlRank = urlRankEntry.getValue();
            int rawRank = urlRank.get(source);
            int adjustedRank = rawRank * getRankWeight(urlRankEntry.getKey());
            logger.debug("[getAggregatedRank] - rankId: {}, adjustedRank: {}", urlRankEntry.getKey(), adjustedRank);

            statistics.addValue(adjustedRank);
        }

        int aggregatedRank = (int) statistics.getMean();
        logger.trace("[getAggregatedRank] - end - aggregatedRank: {}", aggregatedRank);

        return aggregatedRank;
    }

    private int getRankWeight(String rankId) {
        Integer weight = rankWeights.get(rankId);
        return weight == null ? 1 : weight;
    }
}
