package com.study.mybatis.session;

import com.google.common.collect.Lists;
import com.study.mybatis.datasource.PooledDataSource;
import com.study.mybatis.mapping.BoundSql;
import com.study.mybatis.mapping.MapperDefinitionRegister;
import com.study.mybatis.mapping.ParameterMapping;
import com.study.mybatis.transaction.Transaction;
import com.study.mybatis.transaction.jdbc.JdbcTransaction;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 13:01
 */
public class DefaultSession implements SqlSession {

    private SqlExecutor sqlExecutor;

    public DefaultSession() {
        this.sqlExecutor = new BaseSqlExecutor(new JdbcTransaction(new PooledDataSource()));
    }

    @Override
    public <E> List<E> selectList(String statement) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.selectList(boundSql, null);
    }


    @Override
    public <E> List<E> selectList(String statement, Object parameterObject) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.selectList(boundSql, parameterObject);
    }

    @Override
    public int insert(String statement, Object parameterObject) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.update(boundSql, parameterObject);
    }

    @Override
    public int update(String statement, Object parameterObject) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.update(boundSql, parameterObject);
    }

    @Override
    public int update(String statement) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.update(boundSql, null);
    }

    @Override
    public int delete(String statement) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.update(boundSql, null);
    }

    @Override
    public int delete(String statement, Object parameterObject) {
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(statement);
        return sqlExecutor.update(boundSql, parameterObject);
    }


    @Override
    public void close() throws Exception {
        sqlExecutor.getTransaction().close();
    }
}
