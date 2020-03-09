package com.study.spring.bean.scan;

import com.google.common.collect.Sets;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 11:21
 */
public class JdkScan implements ClassScan {


    public Set<Class<?>> scan(String packageName) {

        Set<Class<?>> classes = Sets.newHashSet();
        try {
            String dir = packageName.replace(".", "/");
            System.out.println("dir:" + dir);
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(dir);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if ("file".equals(url.getProtocol())) {
                    System.out.println("file:" + url.getFile());
                    System.out.println("path:" + url.getPath());
                    String filePath = url.getFile();

                    File file = new File(filePath);
                    scan(packageName, file, classes);
                }
            }
        } catch (IOException e) {

        }
        return classes;
    }

    private void scan(String packageName, File dir, Set<Class<?>> allClass) {

        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(".class");
            }
        });

        for (File subFile : files) {
            if (subFile.isFile()) {

                String className = packageName + '.' +subFile.getName().replace(".class", "");
                try {
                    allClass.add(Thread.currentThread().getContextClassLoader().loadClass(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                scan(packageName + "."+ subFile.getName(), subFile, allClass);
            }
        }
    }
}
