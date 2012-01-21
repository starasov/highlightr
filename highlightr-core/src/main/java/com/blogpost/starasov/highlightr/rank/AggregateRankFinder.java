package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.identifier.SourceIdentifierBuilder;
import com.blogpost.starasov.highlightr.model.Rank;
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
public class AggregateRankFinder<S> extends BaseRankFinder<S> {
    private static final Logger logger = LoggerFactory.getLogger(AggregateRankFinder.class);

    private final Map<String, RankFinder<S>> ranks;
    private final Map<String, Integer> rankWeights;

    public AggregateRankFinder(Map<String, RankFinder<S>> ranks, Map<String, Integer> rankWeights, SourceIdentifierBuilder<S> sourceIdentifierBuilder) {
        super(sourceIdentifierBuilder);

        Assert.notNull(ranks, "ranks parameter can't be null.");
        Assert.notNull(rankWeights, "rankWeights parameter can't be null.");

        this.ranks = ranks;
        this.rankWeights = rankWeights;
    }


    public Rank get(S source) {
        logger.trace("[get] - begin - source: {}", source);

        SummaryStatistics statistics = new SummaryStatistics();
        for (Map.Entry<String, RankFinder<S>> urlRankEntry : ranks.entrySet()) {
            RankFinder<S> urlRankFinder = urlRankEntry.getValue();
            int rawRank = urlRankFinder.get(source).getRank();
            int adjustedRank = rawRank * getRankWeight(urlRankEntry.getKey());
            logger.debug("[get] - rankId: {}, adjustedRank: {}", urlRankEntry.getKey(), adjustedRank);

            statistics.addValue(adjustedRank);
        }

        int aggregatedRank = (int) statistics.getMean();
        logger.trace("[get] - end - aggregatedRank: {}", aggregatedRank);

        return createRankFrom(aggregatedRank, source);
    }

    private int getRankWeight(String rankId) {
        Integer weight = rankWeights.get(rankId);
        return weight == null ? 1 : weight;
    }
}
