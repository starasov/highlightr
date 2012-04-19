package com.blogpost.starasov.highlightr.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * User: starasov
 * Date: 1/29/12
 * Time: 7:36 PM
 */
public class CacheBuilderConcurrentMapCacheFactoryBean implements FactoryBean<ConcurrentMap<Object, Object>> {

    private final int cacheExpirationInHours;
    private final int cacheMaximumSize;

    public CacheBuilderConcurrentMapCacheFactoryBean(int cacheExpirationInHours, int cacheMaximumSize) {
        this.cacheExpirationInHours = cacheExpirationInHours;
        this.cacheMaximumSize = cacheMaximumSize;
    }

    public ConcurrentMap<Object, Object> getObject() throws Exception {
        return CacheBuilder.newBuilder().expireAfterWrite(cacheExpirationInHours, TimeUnit.HOURS).maximumSize(cacheMaximumSize).build().asMap();
    }

    public Class<?> getObjectType() {
        return ConcurrentMap.class;
    }

    public boolean isSingleton() {
        return false;
    }
}

