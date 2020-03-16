package com.study.demo.service;

import com.study.demo.dao.UserMapper;
import com.study.demo.dto.Condition;
import com.study.demo.model.User;
import com.study.spring.bean.annotation.Component;
import com.study.spring.bean.factory.BeanFactory;
import com.study.spring.bean.factory.aware.BeanFactoryAware;
import com.study.spring.bean.factory.aware.BeanNameAware;
import com.study.spring.bean.factory.aware.InitialingBean;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-16 23:53
 */
@Component
public class MyStudyService implements BeanNameAware, BeanFactoryAware, InitialingBean{

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    int insert(User user){
        return userMapper.insert(user);
    }

    List<User> queryAllUser(){
        return userMapper.queryAllUser();
    }

    List<User> queryAllUserByName(String userName){
        return userMapper.queryAllUserByName(userName);
    }

    int updateByCondition(Condition condition){
        return userMapper.updateByCondition(condition);
    }

    int deleteByName(String name){
        return userMapper.deleteByName(name);
    }

    int deleteAll(){
        return userMapper.deleteAll();
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        System.out.println("setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("setBeanName");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }
}
