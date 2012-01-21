package com.blogpost.starasov.highlightr.rank.digg;

import com.blogpost.starasov.highlightr.identifier.SourceIdentifierBuilder;
import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.rank.BaseRankFinder;
import com.blogpost.starasov.highlightr.rank.TopicRankFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * User: starasov
 * Date: 12/28/11
 * Time: 7:21 AM
 */
public class DiggTopicRankFinder extends BaseRankFinder<String> {
    private static final Logger logger = LoggerFactory.getLogger(DiggTopicRankFinder.class);

    private final RestTemplate restTemplate;
    private final String urlTemplate;

    public DiggTopicRankFinder(RestTemplate restTemplate, String urlTemplate, SourceIdentifierBuilder<String> identifierBuilder) {
        super(identifierBuilder);

        Assert.notNull(restTemplate, "restTemplate parameter can't be null.");
        Assert.hasLength(urlTemplate, "urlTemplate parameter can't be null or empty.");

        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
    }

    public Rank get(String topic) {
        Assert.hasLength(topic, "topic parameter can't be null or empty.");
        logger.trace("[get] - begin - topic: '{}'", topic);

        QueryResults queryResults = restTemplate.getForObject(urlTemplate, QueryResults.class, topic);
        int totalDiggs = queryResults.getTotalDiggs();

        logger.trace("[get] - end - totalDiggs: {}", totalDiggs);
        return createRankFrom(totalDiggs, topic);
    }
}
