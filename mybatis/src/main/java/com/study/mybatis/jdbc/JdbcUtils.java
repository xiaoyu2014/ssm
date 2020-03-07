package com.study.mybatis.jdbc;

import java.sql.*;

/**
 * JDBC操作类
 * 分下列几步骤
 * 1、加载数据库驱动
 * 2、获取数据库连接
 * 3、创建Statement
 * 4、执行SQL语句
 * 5、处理结果
 * 6、关闭JDBC对象
 *
 * @Author: yuqi
 * @Date: 2020-03-07 23:41
 */
public class JdbcUtils {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            //加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param connection
     */
    public static void closeConnection(Connection connection) {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建statement
     *
     * @param connection
     * @return
     */
    public static Statement createStatement(Connection connection) {

        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 关闭statement
     * @param statement
     */
    public static void closeStatement(Statement statement) {

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建PStatement
     *
     * @param connection
     * @param sql
     * @return
     */
    public static PreparedStatement createPStatement(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
