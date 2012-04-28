package com.blogpost.starasov.highlightr.controller.api;

/**
 * User: starasov
 * Date: 1/21/12
 * Time: 5:22 PM
 */
public class AvgModel {
    private int avg;

    public AvgModel(int avg) {
        this.avg = avg;
    }

    public static AvgModel fromInt(int avg) {
        return new AvgModel(avg);
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }
}
