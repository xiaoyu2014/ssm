package com.study.dao;

import com.study.model.User;
import com.study.mybatis.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: yuqi
 * @Date: 2020-03-07 23:41
 */
public class UserDao {

    public void insert(User user) {
        String sql = "insert into user value(null,?,?)";
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement ps = JdbcUtils.createPStatement(connection, sql)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getSex());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        User user = new User();
        user.setName("xiaoyu");
        user.setSex(1);
        new UserDao().insert(user);
    }

}
