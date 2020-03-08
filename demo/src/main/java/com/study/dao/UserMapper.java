package com.study.dao;

import com.study.model.User;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 14:41
 */
public interface UserMapper {

    boolean insert(User user);

    List<User> queryAllUser();
}
