package com.study.spring.bean.factory.support;

import com.study.spring.bean.factory.BeanFactory;
import com.study.spring.bean.factory.config.BeanDefinition;

/**
 * @Author: yuqi
 * @Date: 2020-03-16 23:19
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    protected abstract <T> T createBean(String beanName, BeanDefinition bd);
}
