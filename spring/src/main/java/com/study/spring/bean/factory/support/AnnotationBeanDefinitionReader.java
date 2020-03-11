package com.study.spring.bean.factory.support;

import com.google.common.base.Strings;
import com.study.spring.bean.annotation.Component;
import com.study.spring.bean.factory.config.BeanDefinition;
import com.study.spring.bean.factory.config.BeanDefinitionReader;
import com.study.spring.bean.factory.processor.BeanDefinitionRegistryPostProcessor;
import com.study.spring.bean.factory.processor.BeanFactoryPostProcessor;
import com.study.spring.core.io.scan.ClassScan;
import com.study.spring.core.io.scan.JdkScan;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 12:08
 */
public class AnnotationBeanDefinitionReader implements BeanDefinitionReader {

    private ClassScan classScan;
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotationBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        classScan = new JdkScan();
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void loadBeanDefinition(String packageName) {

        Set<Class<?>> allClasses = classScan.scan(packageName);

        for(Class<?> beanClass : allClasses){
            if(beanClass.isInterface()){
                continue;
            }
            if(BeanDefinitionRegistryPostProcessor.class.isAssignableFrom(beanClass)){
                String beanName = beanClass.getName();
                beanName = beanName.substring(beanName.lastIndexOf(".") + 1);
                beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

                BeanDefinition beanDefinition = BeanDefinition.builder()
                        .beanClass(beanClass).beanClassName(beanName)
                        .build();
                beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
            }else if(beanClass.isAnnotationPresent(Component.class)){
                Component component = beanClass.getAnnotation(Component.class);
                String beanName = component.name();
                if(Strings.isNullOrEmpty(beanName)){
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
