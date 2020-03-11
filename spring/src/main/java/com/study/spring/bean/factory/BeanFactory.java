package com.study.spring.bean.factory;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 10:56
 */
public interface BeanFactory {

    <T> T getBean(String name);
}
