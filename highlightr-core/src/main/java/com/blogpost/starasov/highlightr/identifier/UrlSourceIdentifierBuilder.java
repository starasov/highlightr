package com.blogpost.starasov.highlightr.identifier;

import com.blogpost.starasov.highlightr.digest.MessageDigestProvider;

import java.net.URL;

/**
 * User: starasov
 * Date: 1/9/12
 * Time: 7:26 PM
 */
public class UrlSourceIdentifierBuilder extends BaseSourceIdentifierBuilder<URL> {
    public UrlSourceIdentifierBuilder(MessageDigestProvider digestProvider) {
        super(digestProvider);
    }

    @Override
    protected String getSourceString(URL source) {
        return source.toString();
    }
}
