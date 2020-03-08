package com.study.mybatis.transaction.jdbc;

import com.study.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 12:51
 */
public class JdbcTransaction implements Transaction {

    private DataSource dataSource;
    private Connection connection;

    public JdbcTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = dataSource.getConnection();
            //connection.setAutoCommit(false);
        }
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public int getTimeOut() throws SQLException {
        return 0;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        if (!connection.getAutoCommit()) {
            connection.setAutoCommit(true);
        }
    }
}
