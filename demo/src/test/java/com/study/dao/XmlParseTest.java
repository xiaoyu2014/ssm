package com.study.dao;

import com.study.model.User;
import com.study.mybatis.mapping.definition.MybatisXmlDefinition;
import com.study.mybatis.mapping.parse.MappingParse;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 22:31
 */
public class XmlParseTest {

    MappingParse mappingParse = MappingParse.getInstance();
    String currentPath = this.getClass().getResource("/").getPath();

    @Test
    public void testXmlParse(){

        String fileName =  currentPath+"/mapper";
        List<MybatisXmlDefinition> definitions = mappingParse.parse(fileName);
        System.out.println(definitions);
    }

}
