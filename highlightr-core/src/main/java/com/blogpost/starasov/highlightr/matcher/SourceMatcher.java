package com.blogpost.starasov.highlightr.matcher;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 6:14 PM
 */
public interface SourceMatcher<S> {
    boolean isMatched(S source);
}
