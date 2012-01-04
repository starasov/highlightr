package com.blogpost.starasov.highlightr.rank;

import java.net.URL;

/**
 * User: starasov
 * Date: 12/28/11
 * Time: 7:20 AM
 */
public interface TopicRank extends Rank<String> {
    int get(String topic);
}
