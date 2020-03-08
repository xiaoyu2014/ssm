package com.study.mybatis.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * @Author: yuqi
 * @Date: 2020-03-08 21:49
 */
public class XmlUtils {

    public static Document load(String fileName) {
        File file = new File(fileName);
        return load(file);
    }

    public static Document load(File file) {
        try {
            SAXReader saxReader = new SAXReader();
            return saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }


}
