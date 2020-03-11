package com.study.spring.bean.factory.support;

import com.study.spring.bean.factory.config.BeanDefinition;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 10:43
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
