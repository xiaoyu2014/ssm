package com.study.mappings;

import com.google.common.collect.Lists;
import com.study.model.User;
import com.study.mybatis.mapping.BoundSql;
import com.study.mybatis.mapping.DefinitionRegister;
import com.study.mybatis.mapping.ParameterMapping;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 13:39
 */
public class UserMapping {

    public static void init(){

        List<ParameterMapping> parameterMappings = Lists.newArrayList();

        parameterMappings.add(ParameterMapping
                .builder()
                .javaType(Long.class)
                .property("id")
                .build());

        parameterMappings.add(ParameterMapping
                .builder()
                .javaType(Integer.class)
                .property("sex")
                .build());

        parameterMappings.add(ParameterMapping
                .builder()
                .javaType(String.class)
                .property("name")
                .build());

        DefinitionRegister.register("UserMapper.insert",
                BoundSql.builder()
                        .sql("insert into user(`id`,`sex`,`name`) value(?,?,?)")
                        .parameterMappings(parameterMappings)
                        .resultType(User.class)
                        .build());
        DefinitionRegister.register("UserMapper.queryAllUser",
                BoundSql.builder()
                        .sql("select * from user")
                        .parameterMappings(parameterMappings)
                        .resultType(User.class)
                        .build());
    }

}
