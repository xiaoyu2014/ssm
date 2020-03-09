package com.study.demo.model;

import lombok.*;

/**
 * @Author: yuqi
 * @Date: 2020-03-07 23:39
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;

    private String name;

    private int sex;
}
