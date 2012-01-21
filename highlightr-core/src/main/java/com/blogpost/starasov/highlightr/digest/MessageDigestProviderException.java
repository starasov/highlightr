package com.blogpost.starasov.highlightr.digest;

import java.security.NoSuchAlgorithmException;

/**
 * User: starasov
 * Date: 1/8/12
 * Time: 7:02 PM
 */
public class MessageDigestProviderException extends RuntimeException {
    public MessageDigestProviderException(Throwable e) {
        super(e);
    }
}
