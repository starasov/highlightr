package com.blogpost.starasov.highlightr.controller;

import com.blogpost.starasov.highlightr.model.Rank;
import com.blogpost.starasov.highlightr.model.Stream;
import com.blogpost.starasov.highlightr.model.StreamType;
import com.blogpost.starasov.highlightr.service.RankService;
import com.blogpost.starasov.highlightr.service.StreamService;
import com.blogpost.starasov.highlightr.service.TrackingService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.net.URL;
import java.util.Date;

/**
 * User: starasov
 * Date: 1/4/12
 * Time: 7:36 AM
 */
@Controller
@RequestMapping(value = "/api/rank/topic")
public class TopicRankController {
    private static final Logger logger = LoggerFactory.getLogger(TopicRankController.class);

    @Autowired
    @Qualifier("topicTrackingService")
    private TrackingService<String> trackingService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException ex, HttpServletRequest request) {
        logger.debug("[handleValidationException] - ex: {}", ex.getMessage());
        return new ModelAndView(new MappingJacksonJsonView(), "error", new ErrorModel(ex.getMessage(), request.getRequestURL().toString()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/avg")
    public @ResponseBody Object getAverageUrlRankForStream(@RequestParam(value = "stream", required = true) URL streamUrl) {
        logger.debug("[getAverageUrlRankForStream] - streamUrl: {}", streamUrl);

        int averageRankForStream = trackingService.getAverageRankForStream(streamUrl);
        logger.debug("[getAverageUrlRankForStream] - averageRankForStream: {}", averageRankForStream);

        return AvgModel.fromInt(averageRankForStream);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stream")
    public @ResponseBody Object getTopicRankForStream(@RequestParam(value = "stream", required = true) URL streamUrl,
                                                      @RequestParam(value = "query", required = true) String query) {
        logger.debug("[getTopicRankForStream] - streamUrl: {}, query: {}", streamUrl, query);

        validateQuery(query);
        Rank rank = trackingService.trackSource(query, streamUrl);
        logger.debug("[getTopicRankForStream] - rank: {}", rank);

        return RankModel.fromRank(rank);
    }

    private void validateQuery(String query) {
        if (StringUtils.isBlank(query)) {
            throw new ValidationException("Query value can't be blank.");
        }
    }
}
