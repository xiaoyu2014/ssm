package com.study.spring;

import com.study.spring.core.io.scan.ClassScan;
import com.study.spring.core.io.scan.JdkScan;
import com.study.spring.core.io.utils.PropertyLoadUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Enumeration;

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

    @Test
    public void testLoad() throws Exception{
        System.out.println(PropertyLoadUtils.loadAllProperties("META-INFO/spring.handlers"));

        Enumeration<URL> urlEnumeration = ClassLoader.getSystemResources("mapper");
        while (urlEnumeration.hasMoreElements()) {
            System.out.println(urlEnumeration.nextElement().getFile());
        }
    }

}
