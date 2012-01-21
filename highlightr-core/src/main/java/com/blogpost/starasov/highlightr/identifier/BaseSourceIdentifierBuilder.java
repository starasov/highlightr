package com.blogpost.starasov.highlightr.identifier;

import com.blogpost.starasov.highlightr.digest.MessageDigestProvider;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * User: starasov
 * Date: 1/9/12
 * Time: 7:27 PM
 */
public abstract class BaseSourceIdentifierBuilder<S> implements SourceIdentifierBuilder<S> {
    private final MessageDigestProvider digestProvider;

    protected BaseSourceIdentifierBuilder(MessageDigestProvider digestProvider) {
        Assert.notNull(digestProvider, "digestProvider parameter can't be null.");
        this.digestProvider = digestProvider;
    }

    public String build(S source) {
        Assert.notNull(source, "source parameter can't be null.");

        MessageDigest messageDigest = digestProvider.get();
        String sourceString = getSourceString(source);
        byte[] digest = messageDigest.digest(sourceString.getBytes());

        return convertToHex(digest);
    }

    protected abstract String getSourceString(S source);

    private static String convertToHex(byte[] data) {
        return new BigInteger(1, data).toString(16);
    }
}
