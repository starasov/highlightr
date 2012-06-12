package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.identifier.SourceIdentifierBuilder;
import com.blogpost.starasov.highlightr.model.Rank;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 7:20 AM
 */
public class AggregateRankFinder<S> extends BaseRankFinder<S> {
    private static final Logger logger = LoggerFactory.getLogger(AggregateRankFinder.class);

    private final Map<String, RankFinder<S>> ranks;
    private final Map<String, Integer> rankWeights;
    private final AsyncTaskExecutor executorService;

    public AggregateRankFinder(Map<String, RankFinder<S>> ranks,
                               Map<String, Integer> rankWeights,
                               SourceIdentifierBuilder<S> sourceIdentifierBuilder,
                               AsyncTaskExecutor executorService) {

        super(sourceIdentifierBuilder);

        Assert.notNull(ranks, "ranks parameter can't be null.");
        Assert.notNull(rankWeights, "rankWeights parameter can't be null.");
        Assert.notNull(executorService, "executorService parameter can't be null.");

        this.ranks = ranks;
        this.rankWeights = rankWeights;
        this.executorService = executorService;
    }


    public Rank get(S source) throws RankFinderException {
        try {
            return getInternal(source);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RankFinderException(e);
        } catch (ExecutionException e) {
            throw new RankFinderException(e);
        }
    }

    private Rank getInternal(S source) throws InterruptedException, ExecutionException {
        logger.trace("[getInternal] - begin - source: {}", source);

        List<Future<Integer>> futures = submitRankSearch(source);
        SummaryStatistics statistics = new SummaryStatistics();

        for (Future<Integer> future : futures) {
            int adjustedRank = future.get();
            statistics.addValue(adjustedRank);
        }

        int aggregatedRank = (int) statistics.getMean();
        logger.trace("[getInternal] - end - aggregatedRank: {}", aggregatedRank);

        return createRankFrom(aggregatedRank, source);
    }

    private List<Future<Integer>> submitRankSearch(final S source) {
        List<Future<Integer>> result = new ArrayList<Future<Integer>>();

        for (final Map.Entry<String, RankFinder<S>> urlRankEntry : ranks.entrySet()) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    RankFinder<S> urlRankFinder = urlRankEntry.getValue();
                    int rawRank = urlRankFinder.get(source).getRank();
                    return rawRank * getRankWeight(urlRankEntry.getKey());
                }
            });

            result.add(future);
        }

        return result;
    }

    private int getRankWeight(String rankId) {
        Integer weight = rankWeights.get(rankId);
        return weight == null ? 1 : weight;
    }
}
