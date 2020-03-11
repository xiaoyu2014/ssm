package com.study.spring.bean.factory.aware;

import com.study.spring.bean.factory.BeanFactory;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 13:22
 */
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory beanFactory);
}
