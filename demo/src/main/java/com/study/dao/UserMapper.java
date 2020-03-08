package com.study.dao;

import com.study.dto.Condition;
import com.study.model.User;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 14:41
 */
public interface UserMapper {

    int insert(User user);

    List<User> queryAllUser();

    List<User> queryAllUserByName(String userName);

    int updateByCondition(Condition condition);

    int deleteByName(String name);

    int deleteAll();
}
