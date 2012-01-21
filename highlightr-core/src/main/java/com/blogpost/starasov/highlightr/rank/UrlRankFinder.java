package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.model.Rank;

import java.net.URL;

/**
 * User: starasov
 * Date: 12/29/11
 * Time: 7:47 AM
 */
public interface UrlRankFinder extends RankFinder<URL> {
    Rank get(URL url);
}
