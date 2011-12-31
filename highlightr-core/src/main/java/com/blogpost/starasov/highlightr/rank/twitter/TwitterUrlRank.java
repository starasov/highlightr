package com.blogpost.starasov.highlightr.rank.twitter;

import com.blogpost.starasov.highlightr.rank.UrlRank;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * User: starasov
 * Date: 12/31/11
 * Time: 2:17 PM
 */
public class TwitterUrlRank implements UrlRank {
    private static final Logger logger = LoggerFactory.getLogger(TwitterUrlRank.class);

    private final RestTemplate restTemplate;
    private final String urlTemplate;

    public TwitterUrlRank(RestTemplate restTemplate, String urlTemplate) {
        Assert.notNull(restTemplate, "restTemplate parameter can't be null.");
        Assert.hasLength(urlTemplate, "urlTemplate parameter can't be null or empty.");

        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
    }

    public int get(URL url) {
        Assert.notNull(url, "url parameter can't be null.");
        logger.trace("[get] - begin - url: {}", url);

        JsonNode queryResults = restTemplate.getForObject(urlTemplate, JsonNode.class, url.toString());
        JsonNode countNode = queryResults.findValue("count");
        if (countNode != null) {
            int count = countNode.getValueAsInt();
            logger.trace("[get] - end - count: {}", count);
            return count;
        } else {
            logger.warn("[get] - end - Failed to get twitted URL count. queryResults: {}", queryResults);
            return 0;
        }
    }
}
