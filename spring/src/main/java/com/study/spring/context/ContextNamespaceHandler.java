package com.study.spring.context;

import com.study.spring.bean.factory.config.BeanDefinitionParser;
import com.study.spring.bean.factory.support.AnnotationBeanDefinitionParser;
import com.study.spring.bean.factory.support.BeanDefinitionRegistry;
import com.study.spring.context.config.NamespaceHandler;
import org.dom4j.Element;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 00:53
 */
public class ContextNamespaceHandler implements NamespaceHandler {

    private BeanDefinitionParser beanDefinitionParser;
    public ContextNamespaceHandler(){
        beanDefinitionParser = new AnnotationBeanDefinitionParser();
    }

    @Override
    public void parse(Element element, BeanDefinitionRegistry beanDefinitionRegistry) {
        beanDefinitionParser.parse(element, beanDefinitionRegistry);
    }
}
