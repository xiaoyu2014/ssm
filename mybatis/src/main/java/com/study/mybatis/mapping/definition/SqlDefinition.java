package com.study.mybatis.mapping.definition;

import com.study.mybatis.mapping.ParameterMapping;
import lombok.*;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 21:54
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SqlDefinition {

    private String sqlCommand;

    private String sqlId;

    private Class<?> resultType;

    private Class<?> parameterType;

    private String sql;
}
