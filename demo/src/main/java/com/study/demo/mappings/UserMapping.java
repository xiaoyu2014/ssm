package com.study.demo.mappings;

import com.study.mybatis.annotation.RepositoryInitial;
import com.study.mybatis.mapping.*;

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

        RepositoryInitial repositoryInitial = new RepositoryInitial();
        repositoryInitial.init();
    }

}
