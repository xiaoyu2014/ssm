package com.study.spring.bean.factory.aware;

/**
 * @Author: yuqi
 * @Date: 2020-03-16 23:37
 */
public interface BeanNameAware extends Aware{

    void setBeanName(String name);
}
