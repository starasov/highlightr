package com.blogpost.starasov.highlightr.transform;

import com.blogpost.starasov.highlightr.matcher.SourceMatcher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 6:13 PM
 */
public class AggregateTransformer<S> implements Transformer<S> {
    private final Map<SourceMatcher<S>, Transformer<S>> transformers;

    public AggregateTransformer(Map<SourceMatcher<S>, Transformer<S>> transformers) {
        Assert.notNull(transformers, "transformers parameter can't be null.");
        this.transformers = transformers;
    }

    @Cacheable(value="default")
    public S transform(S source) throws TransformerException {
        Transformer<S> transformer = findTransformer(source);
        return transformer.transform(source);
    }

    private Transformer<S> findTransformer(S source) {
        for (Map.Entry<SourceMatcher<S>, Transformer<S>> entry : transformers.entrySet()) {
            SourceMatcher<S> key = entry.getKey();
            if (key.isMatched(source)) {
                return entry.getValue();
            }
        }

        return NullTransformer.get();
    }
}
