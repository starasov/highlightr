package com.blogpost.starasov.highlightr.controller;

import com.blogpost.starasov.highlightr.model.Rank;

/**
 * User: starasov
 * Date: 1/5/12
 * Time: 6:19 AM
 */
public class RankModel {
    private int rank;

    public RankModel(int rank) {
        this.rank = rank;
    }

    public static RankModel fromRank(Rank rank) {
        return new RankModel(rank.getRank());
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
