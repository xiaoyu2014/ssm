package com.study.spring.bean.factory.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 10:35
 */
@Builder
@Getter
@Setter
public class BeanDefinition {

    private String beanClassName;

    private Class<?> beanClass;

    private Class<?> beanImplClass;

    private Object[] constructArgs;

    Class<?>[] constructorParams;

    private List<PropertyValue> propertyValues;
}
