package com.blogpost.starasov.highlightr.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author starasov
 */
@Controller
@RequestMapping("/admin")
public class IndexController {

    @RequestMapping
    public String get() {
        return "admin-index";
    }

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }
}
