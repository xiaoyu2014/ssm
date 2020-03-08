package com.study.mybatis.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 12:22
 */
public class PooledDataSource extends AbstractDataSource {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";


    @Override
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        try {
            return DriverManager.getConnection(JDBC_URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
