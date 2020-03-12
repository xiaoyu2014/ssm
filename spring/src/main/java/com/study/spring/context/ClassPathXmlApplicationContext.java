package com.study.spring.context;

import com.study.spring.bean.factory.config.BeanDefinitionReader;
import com.study.spring.bean.factory.support.DefaultBeanFactory;
import com.study.spring.bean.factory.support.XmlBeanDefinitionReader;
import com.study.spring.context.config.ApplicationContext;

import java.io.IOException;

/**
 * @Author: yuqi
 * @Date: 2020-03-12 15:58
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory beanFactory;
    private String[] locations;

    public ClassPathXmlApplicationContext(String[] locations) {
        this.locations = locations;
    }

    public ClassPathXmlApplicationContext(String location) {
        this(new String[]{location});
        this.refresh();
    }

    @Override
    public DefaultBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void loadBeanDefinition(DefaultBeanFactory defaultBeanFactory) {
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(defaultBeanFactory);
        try {
            beanDefinitionReader.loadBeanDefinition(ClassLoader.getSystemResources(locations[0]).nextElement().getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshBeanFactory() {
        this.beanFactory = new DefaultBeanFactory();
    }
}
