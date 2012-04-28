package com.blogpost.starasov.highlightr.controller.admin;

import com.blogpost.starasov.highlightr.service.StreamStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author starasov
 */
@Controller
@RequestMapping("/admin/stream")
public class StreamController {

    @Autowired
    private StreamStatisticsService streamService;

    @RequestMapping
    public ModelAndView get() {
        ModelAndView modelAndView = new ModelAndView("admin-stream-index");
        modelAndView.addObject("statistics", streamService.findPoorStatisticStreams(10.0));
        return modelAndView;
    }
}
