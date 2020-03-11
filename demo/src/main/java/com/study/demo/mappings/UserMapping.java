package com.study.demo.mappings;

import com.study.spring.bean.annotation.Component;
import com.study.mybatis.mapping.*;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 13:39
 */
@Component
public class UserMapping {

    private final static String CURRENT_PATH = UserMapping.class.getClass().getResource("/").getPath();
    private final static String fileName = CURRENT_PATH + "/mapper";

    public UserMapping(){
        MappingLoader mappingLoader = new MappingLoader();
        mappingLoader.load(fileName);
    }
}
