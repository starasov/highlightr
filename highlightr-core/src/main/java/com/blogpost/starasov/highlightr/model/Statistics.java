package com.blogpost.starasov.highlightr.model;

import java.io.Serializable;

/**
 * User: starasov
 * Date: 1/25/12
 * Time: 10:31 PM
 */
public class Statistics implements Serializable {
    public static final Statistics EMPTY = new Statistics(0.0, 0.0, 0.0);

    private final double avg;
    private final double min;
    private final double max;

    public Statistics(double avg, double min, double max) {
        this.avg = avg;
        this.min = min;
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Statistics");
        sb.append("{avg=").append(avg);
        sb.append(", min=").append(min);
        sb.append(", max=").append(max);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Statistics that = (Statistics) o;

        if (Double.compare(that.avg, avg) != 0) return false;
        if (Double.compare(that.max, max) != 0) return false;
        if (Double.compare(that.min, min) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = avg != +0.0d ? Double.doubleToLongBits(avg) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = min != +0.0d ? Double.doubleToLongBits(min) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = max != +0.0d ? Double.doubleToLongBits(max) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
