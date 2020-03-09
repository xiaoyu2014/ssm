package com.study.spring.bean.cache;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 12:35
 */
public class ClassCache {

    private static final Map<String, Class<?>> classMap = Maps.newHashMap();

    public static void register(String className, Class<?> classType) {
        classMap.put(className, classType);
    }

    public static Class<?> getClass(String className) {
        return classMap.get(className);
    }

    public static Map<String, Class<?>> getAllClass() {
        return classMap;
    }
}
