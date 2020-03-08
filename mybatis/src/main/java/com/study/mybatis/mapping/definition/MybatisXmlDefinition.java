package com.study.mybatis.mapping.definition;

import lombok.*;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 21:52
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@ToString
public class MybatisXmlDefinition {

    private String namespace;

    private List<SqlDefinition> sqlDefinitions;

}
