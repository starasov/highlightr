package com.blogpost.starasov.highlightr.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author starasov
 */
@Controller
@RequestMapping("/admin/cache")
public class CacheController {

    @RequestMapping
    public String get() {
        return "admin-cache-index";
    }
}
