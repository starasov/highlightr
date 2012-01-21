package com.blogpost.starasov.highlightr.identifier;

import com.blogpost.starasov.highlightr.digest.MessageDigestProvider;
import org.springframework.util.Assert;

import java.security.MessageDigest;

/**
 * User: starasov
 * Date: 1/8/12
 * Time: 6:47 PM
 */
public class StringSourceIdentifierBuilder extends BaseSourceIdentifierBuilder<String> {
    public StringSourceIdentifierBuilder(MessageDigestProvider digestProvider) {
        super(digestProvider);
    }

    @Override
    protected String getSourceString(String source) {
        return source;
    }
}
