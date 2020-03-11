package com.study.spring.bean.factory.processor;

import com.study.spring.bean.factory.support.BeanDefinitionRegistry;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 13:41
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry);
}
