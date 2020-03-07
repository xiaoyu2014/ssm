package com.study.dao;

import com.study.model.User;
import org.junit.Test;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 00:22
 */
class UserDaoTest {

    UserDao userDao = null;

    @Test
    void insert() {
        User user = new User();
        user.setName("xiaoyu");
        user.setSex(1);
        userDao.insert(user);
    }

}