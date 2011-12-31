package com.blogpost.starasov.highlightr.rank.digg;

import com.blogpost.starasov.highlightr.rank.TopicRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * User: starasov
 * Date: 12/28/11
 * Time: 7:21 AM
 */
public class DiggTopicRank implements TopicRank {
    private static final Logger logger = LoggerFactory.getLogger(DiggTopicRank.class);

    private final RestTemplate restTemplate;
    private final String urlTemplate;

    public DiggTopicRank(RestTemplate restTemplate, String urlTemplate) {
        Assert.notNull(restTemplate, "restTemplate parameter can't be null.");
        Assert.hasLength(urlTemplate, "urlTemplate parameter can't be null or empty.");

        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
    }

    public int get(String topic) {
        Assert.hasLength(topic, "topic parameter can't be null or empty.");
        logger.trace("[get] - begin - topic: '{}'", topic);

        QueryResults queryResults = restTemplate.getForObject(urlTemplate, QueryResults.class, topic);
        int totalDiggs = queryResults.getTotalDiggs();

        logger.trace("[get] - end - totalDiggs: {}", totalDiggs);
        return totalDiggs;
    }
}
