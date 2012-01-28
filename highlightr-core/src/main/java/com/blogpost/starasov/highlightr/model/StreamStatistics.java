package com.blogpost.starasov.highlightr.model;

/**
 * User: starasov
 * Date: 1/25/12
 * Time: 10:31 PM
 */
public class StreamStatistics {
    public static final StreamStatistics EMPTY = new StreamStatistics(0.0, 0.0, 0.0);

    private final double avg;
    private final double min;
    private final double max;

    public StreamStatistics(double avg, double min, double max) {
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
        sb.append("StreamStatistics");
        sb.append("{avg=").append(avg);
        sb.append(", min=").append(min);
        sb.append(", max=").append(max);
        sb.append('}');
        return sb.toString();
    }
}
