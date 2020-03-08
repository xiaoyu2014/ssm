package com.study.dao;

import com.study.dto.Condition;
import com.study.mappings.UserMapping;
import com.study.model.User;
import com.study.mybatis.proxy.MapperProxy;
import com.study.mybatis.session.DefaultSession;
import com.study.mybatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 01:17
 */
public class UserMapperTest {

    private SqlSession sqlSession = new DefaultSession();
    private UserMapper userMapper = null;

    @Before
    public void before(){
        UserMapping.init();
        InvocationHandler invocationHandler = new MapperProxy<User>(sqlSession, UserMapper.class);
        userMapper = (UserMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{UserMapper.class}, invocationHandler);
    }

    @Test
    public void testInsert() {
        //System.out.println(sqlSession.insert("com.study.dao.UserMapper.insert",user));
        User user = User.builder().name("xiaoyu").sex(1).build();
        System.out.println(userMapper.insert(user));
    }

    @Test
    public void testQueryAllUser() {
        //System.out.println(sqlSession.selectList("com.study.dao.UserMapper.queryAllUser"));
        System.out.println(userMapper.queryAllUser());
    }

    @Test
    public void testQueryAllUserByName(){
        //System.out.println(sqlSession.selectList("com.study.dao.UserMapper.queryAllUserByName", "xiaoyu"));
        System.out.println(userMapper.queryAllUserByName("xiaoyu"));
    }
    @Test
    public void updateByCondition() {
        System.out.println(userMapper.updateByCondition(Condition.builder().name("xiaozhang").sex(2).build()));
    }

    @Test
    public void deleteByName() {
        System.out.println(userMapper.deleteByName("xiaoyu"));
    }

    @Test
    public void deleteAll() {
        System.out.println(userMapper.deleteAll());
    }
}