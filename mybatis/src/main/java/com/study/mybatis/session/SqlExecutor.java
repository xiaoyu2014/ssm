package com.study.mybatis.session;

import com.study.mybatis.mapping.BoundSql;
import com.study.mybatis.transaction.Transaction;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 00:55
 */
public interface SqlExecutor {

    <E> List<E> selectList(BoundSql boundSql, Object parameterObject);

    int update(BoundSql boundSql, Object parameterObject);

    Transaction getTransaction();

}
