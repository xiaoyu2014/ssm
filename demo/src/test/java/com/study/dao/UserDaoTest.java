package com.study.dao;

import com.study.mappings.UserMapping;
import com.study.model.User;
import com.study.mybatis.session.DefaultSession;
import com.study.mybatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 01:36
 */
public class UserDaoTest {

    private SqlSession sqlSession = new DefaultSession();

    @Before
    public void before(){
        UserMapping.init();
    }

    @Test
    public void testInsert() {
        User user = User.builder().name("xiaozhang").sex(1).build();
        System.out.println(sqlSession.insert("UserMapper.insert",user));
    }

    @Test
    public void testQueryAllUser() {
        System.out.println(sqlSession.selectList("UserMapper.queryAllUser"));
    }

    @Test
    public void testQueryAllUserByName(){
        System.out.println(sqlSession.selectList("UserMapper.queryAllUserByName", "xiaoyu"));
    }
}