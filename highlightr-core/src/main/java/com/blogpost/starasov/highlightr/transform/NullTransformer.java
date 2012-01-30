package com.blogpost.starasov.highlightr.transform;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 6:19 PM
 */
public class NullTransformer<S> implements Transformer<S> {
    private static final NullTransformer ME = new NullTransformer();

    @SuppressWarnings({"unchecked"})
    public static <S> NullTransformer<S> get() {
        return (NullTransformer<S>) ME;
    }

    public S transform(S source) throws TransformerException {
        return source;
    }
}
