package com.blogpost.starasov.highlightr.context;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.FactoryBean;

/**
 * User: starasov
 * Date: 12/29/11
 * Time: 8:35 PM
 */
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

    public ObjectMapper getObject() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
