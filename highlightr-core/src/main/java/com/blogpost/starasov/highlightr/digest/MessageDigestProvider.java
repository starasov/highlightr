package com.blogpost.starasov.highlightr.digest;

import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: starasov
 * Date: 1/8/12
 * Time: 7:01 PM
 */
public class MessageDigestProvider {
    private final String algorithmName;

    public MessageDigestProvider(String algorithmName) {
        Assert.hasLength(algorithmName, "algorithmName parameter can't be null or empty.");
        this.algorithmName = algorithmName;
    }

    public MessageDigest get() {
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new MessageDigestProviderException(e);
        }
    }
}
