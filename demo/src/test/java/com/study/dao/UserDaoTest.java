package com.study.dao;

import com.study.mappings.UserMapping;
import com.study.model.User;
import com.study.mybatis.session.DefaultSession;
import com.study.mybatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 01:36
 */
public class UserDaoTest {

    private UserDao userDao = UserDao.getInstance();

    private SqlSession sqlSession = new DefaultSession();

    @Before
    public void before(){
        UserMapping.init();
    }

    @Test
    public void testInsert() {
        User user = User.builder().name("xiaoyu").sex(1).build();
        //userDao.insert(user);
        System.out.println(sqlSession.insert("UserMapper.insert",user));
    }

    @Test
    public void queryAllUser() {
        //System.out.println(userDao.queryAllUser());
        System.out.println(sqlSession.selectList("UserMapper.queryAllUser"));
    }

}