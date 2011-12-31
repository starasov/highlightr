package com.blogpost.starasov.highlightr.rank.google;

import com.blogpost.starasov.highlightr.rank.UrlRank;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: starasov
 * Date: 12/30/11
 * Time: 11:15 AM
 */
public class PlusOneRank implements UrlRank {
    private static final Logger logger = LoggerFactory.getLogger(PlusOneRank.class);

    private final RestTemplate restTemplate;
    private final String urlTemplate;

    public PlusOneRank(RestTemplate restTemplate, String urlTemplate) {
        Assert.notNull(restTemplate, "restTemplate parameter can't be null.");
        Assert.hasLength(urlTemplate, "urlTemplate parameter can't be null or empty.");

        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
    }

    public int get(URL url) {
        Assert.notNull(url, "url parameter can't be null.");
        logger.trace("[get] - begin - url: {}", url);

        HttpEntity<?> entity = createRequestEntity(url);
        ResponseEntity<JsonNode> result = restTemplate.postForEntity(urlTemplate, entity, JsonNode.class);

        int plusOneCount = parsePlusOneCount(result);

        logger.trace("[get] - plusOneCount: {}", plusOneCount);
        return plusOneCount;
    }

    private HttpEntity<?> createRequestEntity(URL url) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("method", "pos.plusones.get");
        map.put("id", "p");

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("nolog", true);
        params.put("id", url.toString());
        params.put("source", "widget");
        params.put("userId", "@viewer");
        params.put("groupId", "@self");

        map.put("params", params);
        map.put("jsonrpc", "2.0");
        map.put("key", "p");
        map.put("apiVersion", "v1");

        maps.add(map);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<List<Map<String, Object>>>(maps, headers);
    }

    private int parsePlusOneCount(ResponseEntity<JsonNode> result) {
        JsonNode node = result.getBody();
        JsonNode countNode = node.findValue("count");
        if (countNode != null) {
            return countNode.getValueAsInt();
        } else {
            logger.warn("[parsePlusOneCount] - Failed to read +1 count from result: '{}'", result.getBody());
            return 0;
        }
    }
}
