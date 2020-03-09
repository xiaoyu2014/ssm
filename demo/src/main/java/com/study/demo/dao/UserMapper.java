package com.study.demo.dao;

import com.study.demo.dto.Condition;
import com.study.demo.model.User;
import com.study.mybatis.annotation.Repository;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 14:41
 */
@Repository
public interface UserMapper {

    int insert(User user);

    List<User> queryAllUser();

    List<User> queryAllUserByName(String userName);

    int updateByCondition(Condition condition);

    int deleteByName(String name);

    int deleteAll();
}
