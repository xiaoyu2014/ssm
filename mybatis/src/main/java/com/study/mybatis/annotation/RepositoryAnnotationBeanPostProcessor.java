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

    private SqlSession sqlSession = new DefaultSession();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {

        MapperScanner mapperScanner = new MapperScanner();
        Set<Class<?>> allClasses = mapperScanner.scan("com.study.demo.dao");
        allClasses.stream().filter(beanClass -> beanClass.isAnnotationPresent(Repository.class)).forEach(beanClass ->{
            Repository repository = beanClass.getAnnotation(Repository.class);
            String beanName = repository.name();
            if(Strings.isNullOrEmpty(beanName)){
                beanName = beanClass.getName();
                beanName = beanName.substring(beanName.lastIndexOf(".") + 1);
                beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

                InvocationHandler ih = new MapperProxy<>(sqlSession, beanClass);
                Class<?> proxyClass = Proxy.getProxyClass(beanClass.getClassLoader(), new Class[]{beanClass});
                BeanDefinition beanDefinition = BeanDefinition.builder()
                        .beanClass(proxyClass).beanClassName(beanName)
                        .constructArgs(new Object[]{ih})
                        .constructorParams(new Class<?>[]{InvocationHandler.class})
                        .build();
                registry.registerBeanDefinition(beanName, beanDefinition);
            }
        });
    }
}
