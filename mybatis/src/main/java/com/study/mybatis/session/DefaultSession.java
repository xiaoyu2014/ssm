package com.study.mybatis.session;

import com.google.common.collect.Lists;
import com.study.mybatis.datasource.PooledDataSource;
import com.study.mybatis.mapping.BoundSql;
import com.study.mybatis.mapping.DefinitionRegister;
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

    private Transaction transaction;

    public DefaultSession() {
        this.transaction = new JdbcTransaction(new PooledDataSource());
    }

    @Override
    public <E> List<E> selectList(String statement) {

        List<E> result = Lists.newArrayList();
        try {
            BoundSql boundSql = DefinitionRegister.getBoundSql(statement);
            Connection connection = transaction.getConnection();
            PreparedStatement ps = connection.prepareStatement(boundSql.getSql());
            ResultSet rs = ps.executeQuery();
            Class<E> resultClass = (Class<E>) boundSql.getResultType();

            while (rs.next()) {
                E o = resultClass.newInstance();
                for (ParameterMapping pm : boundSql.getParameterMappings()) {
                    Field field = resultClass.getDeclaredField(pm.getProperty());
                    field.setAccessible(true);
                    if (String.class.equals(pm.getJavaType())) {
                        String value = rs.getString(pm.getProperty());
                        field.set(o, value);
                    } else if (Integer.class.equals(pm.getJavaType())) {
                        Integer value = rs.getInt(pm.getProperty());
                        field.set(o, value);
                    } else if (Long.class.equals(pm.getJavaType())) {
                        Long value = rs.getLong(pm.getProperty());
                        field.set(o, value);
                    }
                }
                result.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> int insert(String statement, T parameterObject) {
        try {
            BoundSql boundSql = DefinitionRegister.getBoundSql(statement);
            Connection connection = transaction.getConnection();
            List<ParameterMapping> pms = boundSql.getParameterMappings();
            String[] columnNames = boundSql.getParameterMappings().stream()
                    .map(ParameterMapping::getProperty).toArray(String[]::new);
            Class<T> classType = (Class<T>) parameterObject.getClass();
            PreparedStatement ps = connection.prepareStatement(boundSql.getSql(), columnNames);

            for (int i = 0; i < pms.size(); i++) {
                ParameterMapping pm = pms.get(i);
                Field field = classType.getDeclaredField(pm.getProperty());
                field.setAccessible(true);
                if (String.class.equals(pm.getJavaType())) {
                    ps.setString(i + 1, (String) field.get(parameterObject));
                } else if (Integer.class.equals(pm.getJavaType())) {
                    ps.setInt(i + 1, (Integer) field.get(parameterObject));
                } else if (Long.class.equals(pm.getJavaType())) {
                    ps.setLong(i + 1, (Long) field.get(parameterObject));
                }
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void close() throws Exception {
        transaction.close();
    }
}
