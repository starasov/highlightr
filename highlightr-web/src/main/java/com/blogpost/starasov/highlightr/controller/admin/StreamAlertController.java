package com.blogpost.starasov.highlightr.controller.admin;

import com.blogpost.starasov.highlightr.service.StreamAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author starasov
 */
@Controller
@RequestMapping("/admin/stream-alert")
public class StreamAlertController {

    @Autowired
    private StreamAlertService streamAlertService;

    @RequestMapping
    public ModelAndView get(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("admin-stream-alert-index");
        modelAndView.addObject("alertsPage", streamAlertService.getRecentAlerts(page, pageSize));
        return modelAndView;
    }
}
