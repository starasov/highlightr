package com.blogpost.starasov.highlightr.rank.digg;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: starasov
 * Date: 12/29/11
 * Time: 8:07 PM
 */
public class QueryResults {

    private List<Story> stories = new ArrayList<Story>();

    @JsonProperty
    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public int getTotalDiggs() {
        int totalDiggs = 0;

        for (Story story : stories) {
            totalDiggs += story.getDiggs();
        }

        return totalDiggs;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("QueryResults");
        sb.append("{stories=").append(stories);
        sb.append('}');
        return sb.toString();
    }
}
