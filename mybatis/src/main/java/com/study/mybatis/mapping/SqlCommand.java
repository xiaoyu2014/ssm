package com.study.mybatis.mapping;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 01:28
 */
public enum SqlCommand {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    SELECT("select");

    String value;

    SqlCommand(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
