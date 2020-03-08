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
                for (Field field : resultClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(o, getValueFromRs(rs, field.getType(), field.getName()));
                }
                result.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private <T> T getValueFromRs(ResultSet rs, Class<T> keyType, String keyName) throws Exception {
        if (String.class.equals(keyType)) {
            return (T) rs.getString(keyName);
        } else if (Integer.class.equals(keyType)
                || int.class.equals(keyType)) {
            return (T) Integer.valueOf(rs.getInt(keyName));
        } else if (Long.class.equals(keyType)
                || long.class.equals(keyType)) {
            return (T) Long.valueOf(rs.getLong(keyName));
        }
        return null;
    }

    private void setValueToPs(PreparedStatement ps, int index, Class<?> keyType, Object value) throws Exception {
        if (String.class.equals(keyType)) {
            ps.setString(index, (String) value);
        } else if (Integer.class.equals(keyType)) {
            ps.setInt(index, (Integer) value);
        } else if (Long.class.equals(keyType)) {
            ps.setLong(index, (Long) value);
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameterObject) {
        List<E> result = Lists.newArrayList();
        try {
            BoundSql boundSql = DefinitionRegister.getBoundSql(statement);
            Connection connection = transaction.getConnection();
            PreparedStatement ps = connection.prepareStatement(boundSql.getSql());
            List<ParameterMapping> pms = boundSql.getParameterMappings();

            if (parameterObject instanceof String) {
                ps.setString(1, (String) parameterObject);
            } else {
                Class classType = parameterObject.getClass();
                for (int i = 0; i < pms.size(); i++) {
                    ParameterMapping pm = pms.get(i);
                    Field field = classType.getDeclaredField(pm.getProperty());
                    field.setAccessible(true);
                    this.setValueToPs(ps, i + 1, pm.getJavaType(), field.get(parameterObject));
                }
            }

            ResultSet rs = ps.executeQuery();
            Class<E> resultClass = (Class<E>) boundSql.getResultType();
            while (rs.next()) {
                E o = resultClass.newInstance();
                for (Field field : resultClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(o, getValueFromRs(rs, field.getType(), field.getName()));
                }
                result.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insert(String statement, Object parameterObject) {
        try {
            BoundSql boundSql = DefinitionRegister.getBoundSql(statement);
            Connection connection = transaction.getConnection();
            List<ParameterMapping> pms = boundSql.getParameterMappings();
            String[] columnNames = boundSql.getParameterMappings().stream()
                    .map(ParameterMapping::getProperty).toArray(String[]::new);
            Class classType = parameterObject.getClass();
            PreparedStatement ps = connection.prepareStatement(boundSql.getSql(), columnNames);

            for (int i = 0; i < pms.size(); i++) {
                ParameterMapping pm = pms.get(i);
                Field field = classType.getDeclaredField(pm.getProperty());
                field.setAccessible(true);
                this.setValueToPs(ps, i + 1, pm.getJavaType(), field.get(parameterObject));
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
