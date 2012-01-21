package com.blogpost.starasov.highlightr.controller;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import com.blogpost.starasov.highlightr.service.RankService;
import com.blogpost.starasov.highlightr.service.StreamService;
import com.blogpost.starasov.highlightr.service.TrackingService;
import com.blogpost.starasov.highlightr.util.UrlSanitizer;
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
import java.util.Date;

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

    @RequestMapping(method = RequestMethod.GET, value = "/avg")
    public @ResponseBody Object getAverageUrlRankForStream(@RequestParam(value = "stream", required = true) URL streamUrl) {
        logger.debug("[getAverageUrlRankForStream] - streamUrl: {}", streamUrl);

        int averageRankForStream = trackingService.getAverageRankForStream(streamUrl);
        logger.debug("[getAverageUrlRankForStream] - averageRankForStream: {}", averageRankForStream);

        return AvgModel.fromInt(averageRankForStream);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stream")
    public @ResponseBody Object getUrlRankForStream(@RequestParam(value = "stream", required = true) URL streamUrl,
                                                    @RequestParam(value = "query", required = true) URL query) {
        logger.trace("[getUrlRankForStream] - streamUrl: {}, query: {}", streamUrl, query);

        URL sanitizedQuery = UrlSanitizer.sanitize(query);
        Rank rank = trackingService.trackSource(sanitizedQuery, streamUrl);
        logger.debug("[getUrlRankForStream] - rank: {}", rank);

        return new RankModel(rank.getRank());
    }
}
