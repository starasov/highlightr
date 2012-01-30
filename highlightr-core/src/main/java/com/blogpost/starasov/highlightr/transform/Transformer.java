package com.blogpost.starasov.highlightr.transform;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 4:44 PM
 */
public interface Transformer<S> {
    S transform(S source) throws TransformerException;
}
