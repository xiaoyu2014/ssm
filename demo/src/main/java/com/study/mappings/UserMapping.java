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

        List<ParameterMapping> insertParameterMappings = Lists.newArrayList();

        insertParameterMappings.add(ParameterMapping
                .builder()
                .javaType(Integer.class)
                .property("sex")
                .build());

        insertParameterMappings.add(ParameterMapping
                .builder()
                .javaType(String.class)
                .property("name")
                .build());

        DefinitionRegister.register("UserMapper.insert",
                BoundSql.builder()
                        .sql("insert into user(`sex`,`name`) value(?,?)")
                        .parameterMappings(insertParameterMappings)
                        .resultType(User.class)
                        .build());
        DefinitionRegister.register("UserMapper.queryAllUser",
                BoundSql.builder()
                        .sql("select * from user")
                        .resultType(User.class)
                        .build());


        List<ParameterMapping> selectParameterMappings = Lists.newArrayList();
        selectParameterMappings.add(ParameterMapping
                .builder()
                .javaType(String.class)
                .property("name")
                .build());

        DefinitionRegister.register("UserMapper.queryAllUserByName",
                BoundSql.builder()
                        .sql("select * from user where name = ?")
                        .parameterMappings(selectParameterMappings)
                        .resultType(User.class)
                        .build());
    }

}
