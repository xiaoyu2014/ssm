package com.study.spring.bean.beanfactory;

import com.google.common.collect.Maps;
import com.study.spring.bean.annotation.BeanAnnotationParse;
import com.study.spring.bean.cache.ClassCache;
import com.study.spring.bean.scan.ClassScan;
import com.study.spring.bean.scan.JdkScan;

import java.lang.reflect.InvocationHandler;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 10:58
 */
public class DefaultBeanFactory implements BeanFactory {

    private static Map<String, Object> beans = Maps.newHashMap();
    ClassScan classScan = new JdkScan();

    public DefaultBeanFactory(String packageName) {
        Set<Class<?>> allClasses = classScan.scan(packageName);
        allClasses.forEach(beanClass -> ClassCache.register(beanClass.getName(), beanClass));
    }

    public static void register(String name, Object bean) {
        beans.put(name, bean);
    }

    @Override
    public <T> T getBean(String name) {
        return (T) beans.get(name);
    }
}
