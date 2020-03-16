package com.study.spring.bean.factory.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.study.spring.bean.factory.BeanFactory;
import com.study.spring.bean.factory.aware.Aware;
import com.study.spring.bean.factory.aware.BeanFactoryAware;
import com.study.spring.bean.factory.aware.BeanNameAware;
import com.study.spring.bean.factory.aware.InitialingBean;
import com.study.spring.bean.factory.config.BeanDefinition;
import com.study.spring.bean.factory.config.Propertydependency;
import com.study.spring.bean.factory.processor.BeanDefinitionRegistryPostProcessor;
import com.study.spring.bean.factory.processor.BeanFactoryPostProcessor;
import com.study.spring.bean.factory.processor.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 10:58
 */
public class DefaultBeanFactory  extends  AbstractBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, Object> beans = Maps.newHashMap();
    private final Set<String> beanNames = Sets.newHashSet();
    private final Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = Lists.newArrayList();
    private final List<BeanDefinitionRegistryPostProcessor> beanDefinitionRegistryPostProcessors = Lists.newArrayList();
    private final List<BeanPostProcessor> beanPostProcessors = Lists.newArrayList();

    public DefaultBeanFactory() {

    }

    private void register(String name, Object bean) {
        beans.put(name, bean);
    }

    @Override
    public <T> T getBean(String name) {

        T bean = (T) beans.get(name);
        if (bean != null) {
            return bean;
        }

        //step1 获取beanDefinition, 创建beanWrapper
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            System.out.println(name + " is null");
            return null;
        }
        //step2 创建bean
        T beanObject = createBean(name, beanDefinition);
        //step3 注册bean
        register(name, beanObject);
        return beanObject;

    }

    private <T> T populateBean(BeanDefinition beanDefinition){
        //step2 beanWrapper 注入属性值，形成bean
        T beanObject = null;
        try {
            if (beanDefinition.getConstructorParams() != null) {
                Constructor<T> constructor = (Constructor<T>) beanDefinition.getBeanClass().getConstructor(beanDefinition.getConstructorParams());
                beanObject = constructor.newInstance(beanDefinition.getConstructArgs());
            } else {
                beanObject = (T) beanDefinition.getBeanClass().newInstance();
            }
            for (Propertydependency dependency : beanDefinition.getPropertydependencies()){
                Field field = beanDefinition.getBeanClass().getDeclaredField(dependency.getPropertyName());
                field.setAccessible(true);
                field.set(beanObject, getBean(dependency.getPropertyRef()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanObject;
    }

    private void initialBean(Object beanObject, String name){

        invokeAwareBeans(beanObject, name);

        applyBeanPostProcessorsBeforeInitialization(beanObject, name);

        invokeInitMethods(beanObject, name);

        applyBeanPostProcessorsAfterInitialization(beanObject, name);
    }

    private void invokeAwareBeans(Object beanObject, String name){

        if(!(beanObject instanceof Aware)){
            return;
        }

        if(beanObject instanceof BeanNameAware){
            ((BeanNameAware)beanObject).setBeanName(name);
        }
        if(beanObject instanceof BeanFactoryAware){
            ((BeanFactoryAware)beanObject).setBeanFactory(this);
        }

    }

    private Object applyBeanPostProcessorsBeforeInitialization(Object beanObject, String name){
        Object result = beanObject;
        for(BeanPostProcessor bp : getBeanPostProcessors()){
           Object current = bp.postProcessBeforeInitialization(result, name);
           if(current == null){
               return result;
           }
           result = current;
        }
        return result;
    }

    private void invokeInitMethods(Object beanObject, String name){
        if(!(beanObject instanceof InitialingBean)){
            return;
        }

        ((InitialingBean) beanObject).afterPropertiesSet();
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object beanObject, String name){
        Object result = beanObject;
        for(BeanPostProcessor bp : getBeanPostProcessors()){
            Object current = bp.postProcessAfterInitialization(result, name);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {

        if(beanNames.contains(name)){
            return;
        }
        beanDefinitionMap.put(name, beanDefinition);
        beanNames.add(name);

        if (BeanDefinitionRegistryPostProcessor.class.isAssignableFrom(beanDefinition.getBeanClass())) {
            BeanDefinitionRegistryPostProcessor beanFactoryPostProcessor = getBean(beanDefinition.getBeanClassName());
            beanFactoryPostProcessor.postProcessBeanDefinitionRegistry(this);
        }
        if(BeanPostProcessor.class.isAssignableFrom(beanDefinition.getBeanClass())){
            BeanPostProcessor beanPostProcessor = getBean(beanDefinition.getBeanClassName());
            this.addBeanPostProcessor(beanPostProcessor);
        }
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    protected <T> T createBean(String beanName, BeanDefinition bd) {
        T beanObject = populateBean(bd);
        initialBean(beanObject, beanName);
        return beanObject;
    }

    public Set<String> getBeanNames() {
        return beanNames;
    }
}
