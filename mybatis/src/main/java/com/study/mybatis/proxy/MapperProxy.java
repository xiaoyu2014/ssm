package com.study.mybatis.proxy;

import com.study.mybatis.mapping.BoundSql;
import com.study.mybatis.mapping.MapperDefinitionRegister;
import com.study.mybatis.mapping.SqlCommand;
import com.study.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 00:32
 */
public class MapperProxy<T> implements InvocationHandler {

    private SqlSession sqlSession;
    private Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String sourceId = mapperInterface.getName() + "." + method.getName();
        System.out.println(sourceId);
        BoundSql boundSql = MapperDefinitionRegister.getBoundSql(sourceId);
        if (SqlCommand.INSERT.getValue().equals(boundSql.getSqlCommand())) {
            return sqlSession.insert(sourceId, args[0]);
        } else if (SqlCommand.SELECT.getValue().equals(boundSql.getSqlCommand())) {
            if (args == null || args.length == 0) {
                return sqlSession.selectList(sourceId);
            } else {
                return sqlSession.selectList(sourceId, args[0]);
            }
        }else if (SqlCommand.UPDATE.getValue().equals(boundSql.getSqlCommand())){
            if (args == null || args.length == 0) {
                return sqlSession.update(sourceId);
            } else {
                return sqlSession.update(sourceId, args[0]);
            }
        }else if(SqlCommand.DELETE.getValue().equals(boundSql.getSqlCommand())){
            if (args == null || args.length == 0) {
                return sqlSession.delete(sourceId);
            } else {
                return sqlSession.delete(sourceId, args[0]);
            }
        }
        throw new RuntimeException("mapper资源未注册");
    }
}
