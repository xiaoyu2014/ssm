package com.study.mybatis.annotation;

import com.study.mybatis.proxy.MapperProxy;
import com.study.mybatis.session.DefaultSession;
import com.study.mybatis.session.SqlSession;
import com.study.spring.bean.beanfactory.DefaultBeanFactory;
import com.study.spring.bean.cache.ClassCache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 12:47
 */
public class RepositoryInitial {

    private SqlSession sqlSession = new DefaultSession();

    public void init(){

        Map<String, Class<?>> allClass = ClassCache.getAllClass();
        Set<Class<?>> allRepositoryClass = BeanAnnotationParse.loadRepositoryAnnotation(allClass.values().stream().collect(Collectors.toSet()));
        allRepositoryClass.forEach(repositoryClass -> {
            String name = repositoryClass.getName();
            name = name.substring(name.lastIndexOf(".")+1);
            name = name.substring(0,1).toLowerCase() + name.substring(1);
            DefaultBeanFactory.register(name, initial(repositoryClass));
        });
    }

    private Object initial(Class<?> repositoryClass){

        InvocationHandler ih = new MapperProxy<>(sqlSession, repositoryClass);

        return Proxy.newProxyInstance(repositoryClass.getClassLoader(), new Class[]{repositoryClass}, ih);
    }

}
