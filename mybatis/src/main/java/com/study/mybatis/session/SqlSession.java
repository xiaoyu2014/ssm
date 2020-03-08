package com.study.mybatis.session;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 12:58
 */
public interface SqlSession extends AutoCloseable{

    <E> List<E> selectList(String statement);

    <T> T selectOne(String statement);

    <T> int insert(String statement, T parameterObject);
}
