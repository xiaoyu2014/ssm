package com.study.mybatis.mapping.parse;

import com.google.common.collect.Lists;
import com.study.mybatis.mapping.SqlCommand;
import com.study.mybatis.mapping.definition.MybatisXmlDefinition;
import com.study.mybatis.mapping.definition.SqlDefinition;
import com.study.mybatis.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.List;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 21:44
 */
public class MappingParse {

    private final static MappingParse parse = new MappingParse();

    private MappingParse() {
    }

    public static MappingParse getInstance() {
        return parse;
    }

    public List<MybatisXmlDefinition> parse(String fileName) {

        List<MybatisXmlDefinition> definitions = Lists.newArrayList();
        File file = new File(fileName);
        if (!file.exists()) {
            return definitions;
        }
        if (file.isFile()) {
            definitions.add(parse(XmlUtils.load(file)));
        } else {
            for (File subFile : file.listFiles()) {
                definitions.add(parse(XmlUtils.load(subFile)));
            }
        }
        return definitions;
    }

    private MybatisXmlDefinition parse(Document document) {
        Element root = document.getRootElement();
        String namespace = root.attributeValue("namespace");

        List<SqlDefinition> sqlDefinitions = Lists.newArrayList();
        List<Element> insertElements = root.elements(SqlCommand.INSERT.getValue());
        for (Element element : insertElements) {
            sqlDefinitions.add(parse(element, SqlCommand.INSERT.getValue()));
        }

        List<Element> selectElements = root.elements(SqlCommand.SELECT.getValue());
        for (Element element : selectElements) {
            sqlDefinitions.add(parse(element, SqlCommand.SELECT.getValue()));
        }

        List<Element> updateElements = root.elements(SqlCommand.UPDATE.getValue());
        for (Element element : updateElements) {
            sqlDefinitions.add(parse(element, SqlCommand.UPDATE.getValue()));
        }

        List<Element> deleteElements = root.elements(SqlCommand.DELETE.getValue());
        for (Element element : deleteElements) {
            sqlDefinitions.add(parse(element, SqlCommand.DELETE.getValue()));
        }

        return MybatisXmlDefinition.builder()
                .namespace(namespace)
                .sqlDefinitions(sqlDefinitions)
                .build();
    }

    private SqlDefinition parse(Element element, String sqlCommand) {

        String idValue = element.attributeValue("id");
        String resultType = element.attributeValue("resultType");
        String parameterType = element.attributeValue("parameterType");
        String sql = element.getStringValue();

        try {
            Class<?> resultClass = resultType != null ? Class.forName(resultType) : null;
            Class<?> parameterClass = parameterType != null ? Class.forName(parameterType) : null;
            return SqlDefinition.builder()
                    .parameterType(parameterClass)
                    .resultType(resultClass)
                    .sqlId(idValue)
                    .sqlCommand(sqlCommand)
                    .sql(sql)
                    .build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
