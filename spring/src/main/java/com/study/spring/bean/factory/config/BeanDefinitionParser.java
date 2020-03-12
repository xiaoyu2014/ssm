package com.study.spring.bean.factory.config;

import com.study.spring.bean.factory.support.BeanDefinitionRegistry;
import org.dom4j.Element;

/**
 * @Author: yuqi
 * @Date: 2020-03-12 17:57
 */
public interface BeanDefinitionParser {
    void parse(Element element, BeanDefinitionRegistry beanDefinitionRegistry);
}
