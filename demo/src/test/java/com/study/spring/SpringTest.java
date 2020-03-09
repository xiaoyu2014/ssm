package com.study.spring;

import com.study.spring.bean.scan.ClassScan;
import com.study.spring.bean.scan.JdkScan;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 11:59
 */
public class SpringTest {

    private ClassScan classScan;

    @Before
    public void init(){
        classScan = new JdkScan();
    }

    @Test
    public void testScan(){
        System.out.println(classScan.scan("com.study.demo"));
    }

}
