package com.blogpost.starasov.highlightr.transform;

import com.blogpost.starasov.highlightr.util.UrlSanitizer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 5:45 PM
 */
public class DZoneFeedTransformer implements Transformer<URL> {
    private static final Logger logger = LoggerFactory.getLogger(DZoneFeedTransformer.class);

    public URL transform(URL source) throws TransformerException {
        logger.debug("[transform] - source: {}", source);

        HttpClient client = new HttpClient();
        GetMethod method = createMethod(source);
        try {
            URL url = doTransform(source, client, method);
            logger.debug("[transform] - transformed url: {}", url);

            return url;
        } catch (IOException e) {
            throw new TransformerException(e);
        } finally {
            method.releaseConnection();
        }
    }

    private URL doTransform(URL source, HttpClient client, GetMethod method) throws IOException, TransformerException {
        int statusCode = client.executeMethod(method);
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            String feedLocation = method.getResponseHeader("Location").getValue();
            logger.debug("[doTransform] - feedLocation: {}", feedLocation);

            URL feedUrl;
            try {
                feedUrl = new URL(feedLocation);
            } catch (MalformedURLException e) {
                return UrlSanitizer.cleanQuery(source);
            }

            return transform(feedUrl);
        } else {
            return UrlSanitizer.cleanQuery(source);
        }
    }

    private GetMethod createMethod(URL source) {
        GetMethod method = new GetMethod(source.toString());
        method.setFollowRedirects(false);
        return method;
    }
}
