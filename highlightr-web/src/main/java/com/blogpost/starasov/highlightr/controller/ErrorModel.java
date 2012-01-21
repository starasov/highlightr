package com.blogpost.starasov.highlightr.controller;

/**
 * User: starasov
 * Date: 1/5/12
 * Time: 6:19 AM
 */
public class ErrorModel {
    private String message;
    private String url;

    public ErrorModel(String message, String url) {
        this.message = message;
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
