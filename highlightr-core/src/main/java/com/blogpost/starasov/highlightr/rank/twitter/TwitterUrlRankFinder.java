package com.blogpost.starasov.highlightr.rank.twitter;

import com.blogpost.starasov.highlightr.identifier.SourceIdentifierBuilder;
import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.rank.BaseRankFinder;
import com.blogpost.starasov.highlightr.rank.UrlRankFinder;
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
public class TwitterUrlRankFinder extends BaseRankFinder<URL> {
    private static final Logger logger = LoggerFactory.getLogger(TwitterUrlRankFinder.class);

    private final RestTemplate restTemplate;
    private final String urlTemplate;

    public TwitterUrlRankFinder(RestTemplate restTemplate, String urlTemplate, SourceIdentifierBuilder<URL> urlSourceIdentifierBuilder) {
        super(urlSourceIdentifierBuilder);

        Assert.notNull(restTemplate, "restTemplate parameter can't be null.");
        Assert.hasLength(urlTemplate, "urlTemplate parameter can't be null or empty.");

        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
    }

    public Rank get(URL url) {
        Assert.notNull(url, "url parameter can't be null.");
        logger.trace("[get] - begin - url: {}", url);

        JsonNode queryResults = restTemplate.getForObject(urlTemplate, JsonNode.class, url.toString());
        JsonNode countNode = queryResults.findValue("count");
        if (countNode != null) {
            int count = countNode.getValueAsInt();
            logger.trace("[get] - end - count: {}", count);
            return createRankFrom(count, url);
        } else {
            logger.warn("[get] - end - Failed to get twitted URL count. queryResults: {}", queryResults);
            return createRankFrom(0, url);
        }
    }
}
