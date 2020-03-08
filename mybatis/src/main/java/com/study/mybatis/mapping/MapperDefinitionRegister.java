package com.study.mybatis.mapping;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 14:30
 */
public class MapperDefinitionRegister {

    public static Map<String,BoundSql> boundSqlMap = Maps.newHashMap();

    public static void register(String sourceId, BoundSql boundSql){
        boundSqlMap.put(sourceId, boundSql);
    }

    public static BoundSql getBoundSql(String sourceId){
        return boundSqlMap.get(sourceId);
    }
}
