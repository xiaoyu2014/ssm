package com.study.demo.dao;

import com.study.demo.dto.Condition;
import com.study.demo.model.User;
import com.study.spring.context.ClassPathXmlApplicationContext;
import com.study.spring.context.config.ApplicationContext;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 01:17
 */
public class UserMapperTest {

    private UserMapper userMapper;

    @Before
    public void before(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        userMapper = applicationContext.getBean("userMapper");
    }

    @Test
    public void testInsert() {
        //System.out.println(sqlSession.insert("com.study.demo.dao.UserMapper.insert",user));
        User user = User.builder().name("xiaoyu").sex(1).build();
        System.out.println(userMapper.insert(user));
    }

    @Test
    public void testQueryAllUser() {
        //System.out.println(sqlSession.selectList("com.study.demo.dao.UserMapper.queryAllUser"));
        System.out.println(userMapper.queryAllUser());
    }

    @Test
    public void testQueryAllUserByName(){
        //System.out.println(sqlSession.selectList("com.study.demo.dao.UserMapper.queryAllUserByName", "xiaoyu"));
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