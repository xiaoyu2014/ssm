package com.study.mybatis.mapping;

import com.study.mybatis.mapping.definition.MybatisXmlDefinition;
import com.study.mybatis.mapping.definition.SqlDefinition;
import com.study.mybatis.mapping.parse.MappingParse;
import com.study.mybatis.utils.GenericTokenParser;

import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 00:21
 */
public class MappingLoader {

    private volatile boolean hasLoad = false;

    public void load(String fileName){

        if(hasLoad){
            return;
        }

        List<MybatisXmlDefinition> xmlDefinitions = MappingParse.getInstance().parse(fileName);
        for (MybatisXmlDefinition mybatisXmlDefinition : xmlDefinitions) {
            String namespace = mybatisXmlDefinition.getNamespace();
            for (SqlDefinition sqlDefinition : mybatisXmlDefinition.getSqlDefinitions()) {
                String sourceId = namespace + "." + sqlDefinition.getSqlId();
                ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
                GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
                String newSql = genericTokenParser.parse(sqlDefinition.getSql());
                MapperDefinitionRegister.register(sourceId,
                        BoundSql.builder()
                                .sql(newSql)
                                .sqlCommand(sqlDefinition.getSqlCommand())
                                .parameterMappings(tokenHandler.getParameterMappings())
                                .resultType(sqlDefinition.getResultType())
                                .build());
            }
        }

        hasLoad = true;
    }

}
