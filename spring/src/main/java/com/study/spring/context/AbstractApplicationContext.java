package com.study.spring.context;

import com.study.spring.bean.factory.BeanFactory;
import com.study.spring.bean.factory.support.DefaultBeanFactory;
import com.study.spring.context.config.ApplicationContext;

/**
 * @Author: yuqi
 * @Date: 2020-03-12 15:59
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    public abstract DefaultBeanFactory getBeanFactory();

    public abstract void loadBeanDefinition(DefaultBeanFactory defaultBeanFactory);

    public abstract void refreshBeanFactory();

    @Override
    public <T> T getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    public void refresh(){

        refreshBeanFactory();

        loadBeanDefinition(getBeanFactory());
    }
}
