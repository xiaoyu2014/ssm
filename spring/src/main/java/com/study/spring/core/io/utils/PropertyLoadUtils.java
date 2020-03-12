package com.study.spring.core.io.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 01:16
 */
public class PropertyLoadUtils {

    public static Properties loadAllProperties(String resourceName) throws IOException {

        Enumeration<URL> urls = ClassLoader.getSystemResources(resourceName);
        Properties props = new Properties();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            try {
                props.load(is);
            } finally {
                is.close();
            }
        }
        return props;
    }

}
