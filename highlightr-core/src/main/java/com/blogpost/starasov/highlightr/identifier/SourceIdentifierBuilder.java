package com.blogpost.starasov.highlightr.identifier;

/**
 * User: starasov
 * Date: 1/8/12
 * Time: 6:46 PM
 */
public interface SourceIdentifierBuilder<S> {
    String build(S source);
}
