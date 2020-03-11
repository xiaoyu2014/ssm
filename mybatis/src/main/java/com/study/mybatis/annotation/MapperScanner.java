package com.study.mybatis.annotation;

import com.study.spring.core.io.scan.ClassScan;
import com.study.spring.core.io.scan.JdkScan;

import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-11 13:55
 */
public class MapperScanner {

    private ClassScan classScan;
    public MapperScanner() {
        classScan = new JdkScan();
    }

    public Set<Class<?>> scan(String packageName){
        return classScan.scan(packageName);
    }
}
