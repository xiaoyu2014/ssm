package com.study.mybatis.session;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 12:58
 */
public interface SqlSession extends AutoCloseable{

    <E> List<E> selectList(String statement);

    <E> List<E> selectList(String statement, Object parameterObject);

    int insert(String statement, Object parameterObject);

    int update(String statement, Object parameterObject);

    int update(String statement);

    int delete(String statement);

    int delete(String statement, Object parameterObject);
}
