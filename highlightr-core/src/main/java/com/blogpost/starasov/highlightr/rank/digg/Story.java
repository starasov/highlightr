package com.blogpost.starasov.highlightr.rank.digg;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: starasov
 * Date: 12/29/11
 * Time: 8:11 PM
 */
public class Story {
    private int diggs;

    public int getDiggs() {
        return diggs;
    }

    @JsonProperty
    public void setDiggs(int diggs) {
        this.diggs = diggs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Story");
        sb.append("{diggs=").append(diggs);
        sb.append('}');
        return sb.toString();
    }
}
