package com.blogpost.starasov.highlightr.rank;

import com.blogpost.starasov.highlightr.model.Rank;

/**
 * User: starasov
 * Date: 12/28/11
 * Time: 7:20 AM
 */
public interface TopicRankFinder extends RankFinder<String> {
    Rank get(String topic);
}
