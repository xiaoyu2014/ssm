package com.study.mybatis.mapping;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 14:21
 */
@Setter
@Getter
@Builder
public class BoundSql {

    private String sql;

    private List<ParameterMapping> parameterMappings;

    private Class<?> resultType;

    private String sqlCommand;
}
