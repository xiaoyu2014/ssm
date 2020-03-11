package com.study.spring.bean.factory.support;

import com.google.common.collect.Maps;
import com.study.spring.bean.factory.BeanFactory;
import com.study.spring.bean.factory.config.BeanDefinition;
import com.study.spring.bean.factory.config.BeanDefinitionReader;
import com.study.spring.bean.factory.processor.BeanDefinitionRegistryPostProcessor;
import com.study.spring.bean.factory.processor.BeanFactoryPostProcessor;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 10:58
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private static Map<String, Object> beans = Maps.newHashMap();
    private static Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();


    public DefaultBeanFactory(String packageName) {

        //step1 解析配置文件，得到beanDefinition
        //step2 注册beanDefinition到内存
        BeanDefinitionReader beanDefinitionReader = new AnnotationBeanDefinitionReader(this);
        beanDefinitionReader.loadBeanDefinition(packageName);
    }

    private void register(String name, Object bean) {
        beans.put(name, bean);
    }

    @Override
    public <T> T getBean(String name) {

        T bean = (T) beans.get(name);
        if(bean != null){
            return bean;
        }

        //step1 获取beanDefinition, 创建beanWrapper
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null){
            System.out.println(name + " is null");
            return null;
        }
        //step2 beanWrapper 注入属性值，形成bean
        try {
            T beanObject = null;
            if(beanDefinition.getConstructorParams() != null) {
                Constructor<T> constructor = (Constructor<T>) beanDefinition.getBeanClass().getConstructor(beanDefinition.getConstructorParams());
                beanObject = constructor.newInstance(beanDefinition.getConstructArgs());
            }else {
                beanObject = (T) beanDefinition.getBeanClass().newInstance();
            }
            //step3 注册bean
            register(name, beanObject);
            return beanObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {

        beanDefinitionMap.put(name, beanDefinition);

        if(BeanDefinitionRegistryPostProcessor.class.isAssignableFrom(beanDefinition.getBeanClass())){
            BeanDefinitionRegistryPostProcessor beanFactoryPostProcessor = getBean(beanDefinition.getBeanClassName());
            beanFactoryPostProcessor.postProcessBeanDefinitionRegistry(this);
        }
    }
}
