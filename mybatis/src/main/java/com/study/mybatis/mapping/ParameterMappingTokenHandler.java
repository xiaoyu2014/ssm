package com.study.mybatis.mapping;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 23:55
 */
@Getter
public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = Lists.newArrayList();

    @Override
    public String handleToken(String content) {
        parameterMappings.add(
                ParameterMapping.builder().property(content).build());
        return "?";
    }
}
