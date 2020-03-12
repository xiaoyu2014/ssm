package com.study.spring.context.config;

import com.study.spring.bean.factory.support.BeanDefinitionRegistry;
import org.dom4j.Element;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 00:31
 */
public interface NamespaceHandler {
    void parse(Element element, BeanDefinitionRegistry beanDefinitionRegistry);
}
