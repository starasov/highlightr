package com.blogpost.starasov.highlightr.rank;

import org.springframework.core.NestedRuntimeException;

/**
 * User: starasov
 * Date: 12/30/11
 * Time: 4:49 PM
 */
public class RankRetrievalException extends NestedRuntimeException {
    public RankRetrievalException(String msg) {
        super(msg);
    }

    public RankRetrievalException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
