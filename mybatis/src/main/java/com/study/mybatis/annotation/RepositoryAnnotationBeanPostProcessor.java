package com.study.mybatis.annotation;

import com.google.common.base.Strings;
import com.mysql.cj.util.StringUtils;
import com.study.mybatis.proxy.MapperProxy;
import com.study.mybatis.session.DefaultSession;
import com.study.mybatis.session.SqlSession;
import com.study.spring.bean.factory.config.BeanDefinition;
import com.study.spring.bean.factory.processor.BeanDefinitionRegistryPostProcessor;
import com.study.spring.bean.factory.support.BeanDefinitionRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 13:22
 */
public class RepositoryAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {


    }
}
