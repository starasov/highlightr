package com.blogpost.starasov.highlightr;

/**
 * User: starasov
 * Date: 12/23/11
 * Time: 7:06 AM
 */
public class Glow {
    private final int id;
    private final int rank;
    private final String googlePlusOnes;

    public Glow(int id, int rank, String googlePlusOnes) {
        this.id = id;
        this.rank = rank;
        this.googlePlusOnes = googlePlusOnes;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public String getGooglePlusOnes() {
        return googlePlusOnes;
    }
}
