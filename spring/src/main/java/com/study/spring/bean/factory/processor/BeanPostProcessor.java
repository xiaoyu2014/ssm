package com.study.spring.bean.factory.processor;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 11:45
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName);

    Object postProcessAfterInitialization(Object bean, String beanName);
}
