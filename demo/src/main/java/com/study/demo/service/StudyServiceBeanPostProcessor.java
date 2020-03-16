package com.study.demo.service;

import com.study.spring.bean.annotation.Component;
import com.study.spring.bean.factory.processor.BeanPostProcessor;

/**
 * @Author: yuqi
 * @Date: 2020-03-16 23:56
 */
@Component
public class StudyServiceBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("postProcessBeforeInitialization:"+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("postProcessAfterInitialization:"+beanName);
        return bean;
    }
}
