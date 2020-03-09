package com.study.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 01:13
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Condition {
    private String name;
    private int sex;
}
