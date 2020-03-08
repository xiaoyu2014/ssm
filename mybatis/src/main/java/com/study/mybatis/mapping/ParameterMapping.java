package com.study.mybatis.mapping;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 14:22
 */
@Getter
@Setter
@Builder
public class ParameterMapping {

    private String property;
    private Class<?> javaType;
}
