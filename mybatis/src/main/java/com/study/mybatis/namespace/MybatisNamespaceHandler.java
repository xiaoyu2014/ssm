package com.study.mybatis.namespace;

import com.study.spring.bean.factory.config.BeanDefinitionParser;
import com.study.spring.bean.factory.support.BeanDefinitionRegistry;
import com.study.spring.context.config.NamespaceHandler;
import org.dom4j.Element;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 00:56
 */
public class MybatisNamespaceHandler implements NamespaceHandler {

    private BeanDefinitionParser beanDefinitionParser;
    public MybatisNamespaceHandler() {
        beanDefinitionParser = new MapperScannerBeanParser();
    }

    @Override
    public void parse(Element element, BeanDefinitionRegistry beanDefinitionRegistry) {
        beanDefinitionParser.parse(element, beanDefinitionRegistry);
    }
}
