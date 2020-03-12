package com.study.spring.bean.factory.support;

import com.google.common.base.Strings;
import com.study.spring.bean.annotation.Component;
import com.study.spring.bean.factory.config.BeanDefinition;
import com.study.spring.bean.factory.config.BeanDefinitionParser;
import com.study.spring.core.io.scan.ClassScan;
import com.study.spring.core.io.scan.JdkScan;
import org.dom4j.Element;

import java.util.Set;


/**
 * @Author: yuqi
 * @Date: 2020-03-11 12:08
 */
public class AnnotationBeanDefinitionParser implements BeanDefinitionParser {

    private ClassScan classScan;

    public AnnotationBeanDefinitionParser() {
        classScan = new JdkScan();
    }


    @Override
    public void parse(Element element, BeanDefinitionRegistry beanDefinitionRegistry) {

        if(!"component-scan".equals(element.getName())){
            return;
        }

        String packageName = element.attributeValue("base-package");

        Set<Class<?>> allClasses = classScan.scan(packageName);

        for(Class<?> beanClass : allClasses) {
            if (beanClass.isInterface()) {
                continue;
            }
            if (beanClass.isAnnotationPresent(Component.class)) {
                Component component = beanClass.getAnnotation(Component.class);
                String beanName = component.name();
                if (Strings.isNullOrEmpty(beanName)) {
                    beanName = beanClass.getName();
                    beanName = beanName.substring(beanName.lastIndexOf(".") + 1);
                    beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

                    BeanDefinition beanDefinition = BeanDefinition.builder()
                            .beanClass(beanClass).beanClassName(beanName)
                            .build();
                    beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }

        }
}
