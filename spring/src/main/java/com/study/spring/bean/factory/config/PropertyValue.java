package com.study.spring.bean.factory.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 10:40
 */
@Builder
@Getter
@Setter
public class PropertyValue {

    private String name;

    private Object value;
}
