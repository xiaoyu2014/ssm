package com.study.spring.core.io.scan;

import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 11:22
 */
public interface ClassScan {

    Set<Class<?>> scan(String packageName);
}
