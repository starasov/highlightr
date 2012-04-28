package com.blogpost.starasov.highlightr.controller.api;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Statistics;
import com.blogpost.starasov.highlightr.service.TrackingService;
import com.blogpost.starasov.highlightr.transform.Transformer;
import com.blogpost.starasov.highlightr.transform.TransformerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URL;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 7:37 AM
 */
@Controller
@RequestMapping(value = "/api/rank/url")
public class UrlRankController {
    private static final Logger logger = LoggerFactory.getLogger(UrlRankController.class);

    @Autowired
    @Qualifier("urlTrackingService")
    private TrackingService<URL> trackingService;

    @Autowired
    private Transformer<URL> urlTransformer;

    @RequestMapping(method = RequestMethod.GET, value = "/stats")
    public @ResponseBody Object getUrlStatisticsForStream(@RequestParam(value = "stream", required = true) URL streamUrl) {
        logger.debug("[getUrlStatisticsForStream] - streamUrl: {}", streamUrl);

        Statistics statistics = trackingService.getStreamStatistics(streamUrl);
        logger.debug("[getUrlStatisticsForStream] - statistics: {}", statistics);

        return statistics;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stream")
    public @ResponseBody Object getUrlRankForStream(@RequestParam(value = "stream", required = true) URL streamUrl,
                                                    @RequestParam(value = "query", required = true) URL query) throws TransformerException {
        logger.trace("[getUrlRankForStream] - streamUrl: {}, query: {}", streamUrl, query);

        URL targetQuery = urlTransformer.transform(query);
        Rank rank = trackingService.trackSource(targetQuery, streamUrl);
        logger.debug("[getUrlRankForStream] - rank: {}", rank);

        return new RankModel(rank.getRank());
    }
}
