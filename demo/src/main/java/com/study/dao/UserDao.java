package com.study.dao;

import com.google.common.collect.Lists;
import com.study.model.User;
import com.study.mybatis.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-07 23:41
 */
public class UserDao {

    private static UserDao userDao = new UserDao();

    private UserDao() {

    }

    public static UserDao getInstance() {
        return userDao;
    }

    public boolean insert(User user) {
        String sql = "insert into user value(?,?,?)";
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement ps = JdbcUtils.createPStatement(connection, sql)) {
            ps.setString(1, null);
            ps.setString(2, user.getName());
            ps.setInt(3, user.getSex());
            connection.setAutoCommit(false);
            ps.executeUpdate();
            connection.rollback();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> queryAllUser() {
        List<User> users = Lists.newArrayList();
        String sql = "select * from user";
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement ps = JdbcUtils.createPStatement(connection, sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                int sex = rs.getInt(3);
                System.out.println(name + ":" + sex);
                users.add(User.builder().id(id).name(name).sex(sex).build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
