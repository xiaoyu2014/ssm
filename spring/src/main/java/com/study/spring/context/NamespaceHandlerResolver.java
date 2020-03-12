package com.study.spring.context;

import com.google.common.collect.Maps;
import com.study.spring.context.config.NamespaceHandler;
import com.study.spring.core.io.utils.PropertyLoadUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 00:32
 */
public class NamespaceHandlerResolver {

    private static Map<String, NamespaceHandler> namespaceHandlerMap = Maps.newHashMap();
    private static final String NAMESPACE_MAPPING_LOCATION = "META-INFO/spring.handlers";

    public NamespaceHandlerResolver(){
        try {
            Properties properties = PropertyLoadUtils.loadAllProperties(NAMESPACE_MAPPING_LOCATION);
            for(String key : properties.stringPropertyNames()){
                Class<?> clazz = Class.forName(properties.getProperty(key));
                namespaceHandlerMap.put(key, (NamespaceHandler) clazz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NamespaceHandler getNamespaceHandler(String uri){
        return namespaceHandlerMap.get(uri);
    }

}
