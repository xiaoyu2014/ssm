package com.study.dao;

import com.study.model.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 01:36
 */
public class UserDaoTest {

    private UserDao userDao = UserDao.getInstance();

    @Test
    public void testInsert() {
        User user = User.builder().name("xiaoyu").sex(1).build();
        Assert.assertTrue(userDao.insert(user));
    }

    @Test
    public void queryAllUser() {
        System.out.println(userDao.queryAllUser());
    }
}