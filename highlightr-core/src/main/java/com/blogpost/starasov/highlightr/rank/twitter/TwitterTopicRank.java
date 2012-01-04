package com.blogpost.starasov.highlightr.rank.twitter;

import com.blogpost.starasov.highlightr.rank.TopicRank;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * User: starasov
 * Date: 12/31/11
 * Time: 3:23 PM
 */
public class TwitterTopicRank implements TopicRank {
    private static final Logger logger = LoggerFactory.getLogger(TwitterTopicRank.class);

    private final RestTemplate restTemplate;
    private final String urlTemplate;
    private final String paginationUrlTemplate;
    private final int maxPages;

    public TwitterTopicRank(RestTemplate restTemplate, String urlTemplate, String paginationUrlTemplate, int maxPages) {
        Assert.notNull(restTemplate, "restTemplate parameter can't be null.");
        Assert.hasLength(urlTemplate, "urlTemplate parameter can't be null or empty.");
        Assert.hasLength(paginationUrlTemplate, "paginationUrlTemplate parameter can't be null or empty.");
        Assert.state(maxPages > 0, "maxPages parameter should be greater than zero.");

        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
        this.paginationUrlTemplate = paginationUrlTemplate;
        this.maxPages = maxPages;
    }

    public int get(String topic) {
        Assert.hasLength(topic, "topic parameter can't be null or empty.");
        logger.trace("[get] - topic: {}", topic);


        String initialQueryUrl = String.format(urlTemplate, topic);
        logger.debug("[get] - initialQueryUrl: {}", initialQueryUrl);

        return doGet(initialQueryUrl, 0, 0);
    }

    private int doGet(String url, int currentPage, int totalTweets) {
        logger.trace("[doGet] - begin - url: {}, currentPage: {}, totalTweets: {}", new Object[] {url, currentPage, totalTweets});

        JsonNode queryResults = restTemplate.getForObject(url, JsonNode.class);
        int pageTotalTweets = queryResults.findValues("id").size();

        JsonNode nextPageQueryNode = queryResults.findValue("next_page");
        if (nextPageQueryNode != null && currentPage < maxPages) {
            String fullUrl = String.format(paginationUrlTemplate, nextPageQueryNode.getValueAsText());
            return doGet(fullUrl, currentPage + 1, totalTweets + pageTotalTweets);
        }

        return pageTotalTweets + totalTweets;
    }
}
