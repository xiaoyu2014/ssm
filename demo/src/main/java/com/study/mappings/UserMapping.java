package com.study.mappings;

import com.google.common.collect.Lists;
import com.study.model.User;
import com.study.mybatis.mapping.*;
import com.study.mybatis.mapping.definition.MybatisXmlDefinition;
import com.study.mybatis.mapping.definition.SqlDefinition;
import com.study.mybatis.mapping.parse.MappingParse;
import com.study.mybatis.utils.GenericTokenParser;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 13:39
 */
public class UserMapping {

    private final static String CURRENT_PATH = UserMapping.class.getClass().getResource("/").getPath();
    private final static String fileName = CURRENT_PATH + "/mapper";

    public static void init() {
        MappingLoader mappingLoader = new MappingLoader();
        mappingLoader.load(fileName);
    }

}
