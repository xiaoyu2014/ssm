package com.study.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 12:48
 */
public interface Transaction extends AutoCloseable {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    int getTimeOut() throws SQLException;
}
