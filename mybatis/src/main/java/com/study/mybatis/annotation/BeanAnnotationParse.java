package com.study.mybatis.annotation;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 11:09
 */
public class BeanAnnotationParse{

    public static Set<Class<?>> loadRepositoryAnnotation(Set<Class<?>> allClass){

        return allClass.stream()
                .filter(beanClass -> beanClass.isAnnotationPresent(Repository.class))
                .collect(Collectors.toSet());
    }
}
