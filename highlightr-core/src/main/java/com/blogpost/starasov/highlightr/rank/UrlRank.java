package com.blogpost.starasov.highlightr.rank;

import java.net.URL;

/**
 * User: starasov
 * Date: 12/29/11
 * Time: 7:47 AM
 */
public interface UrlRank extends Rank<URL> {
    int get(URL url);
}
