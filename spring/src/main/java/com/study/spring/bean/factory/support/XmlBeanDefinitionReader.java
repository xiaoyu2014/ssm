package com.study.spring.bean.factory.support;

import com.study.spring.bean.factory.config.BeanDefinitionReader;
import com.study.spring.context.NamespaceHandlerResolver;
import com.study.spring.core.io.scan.ClassScan;
import com.study.spring.core.io.scan.JdkScan;
import com.study.spring.core.io.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 00:59
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private ClassScan classScan;
    private BeanDefinitionRegistry beanDefinitionRegistry;
    private NamespaceHandlerResolver namespaceHandlerResolver;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        classScan = new JdkScan();
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        namespaceHandlerResolver = new NamespaceHandlerResolver();
    }

    @Override
    public void loadBeanDefinition(String location) {

       Document document = XmlUtils.load(location);
       Element root = document.getRootElement();
       String [] nameSpaceUris = root.attribute("namespace").getStringValue().split(",");
       for(Element element : (List<Element>)root.elements()){
           for(String nameSpaceUri : nameSpaceUris){
                namespaceHandlerResolver.getNamespaceHandler(nameSpaceUri).parse(element, beanDefinitionRegistry);
           }
       }
    }
}
