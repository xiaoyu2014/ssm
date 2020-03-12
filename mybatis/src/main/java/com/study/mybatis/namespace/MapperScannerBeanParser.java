package com.study.mybatis.namespace;

import com.google.common.base.Strings;
import com.study.mybatis.annotation.MapperScanner;
import com.study.mybatis.annotation.Repository;
import com.study.mybatis.mapping.MappingLoader;
import com.study.mybatis.proxy.MapperProxy;
import com.study.mybatis.session.DefaultSession;
import com.study.mybatis.session.SqlSession;
import com.study.spring.bean.factory.config.BeanDefinition;
import com.study.spring.bean.factory.config.BeanDefinitionParser;
import com.study.spring.bean.factory.support.BeanDefinitionRegistry;
import org.dom4j.Element;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * @Author: yuqi
 * @Date: 2020-03-13 01:43
 */
public class MapperScannerBeanParser implements BeanDefinitionParser {

    private SqlSession sqlSession;
    private MapperScanner mapperScanner;
    private MappingLoader mappingLoader;

    public MapperScannerBeanParser() {
        mapperScanner = new MapperScanner();
        sqlSession = new DefaultSession();
        mappingLoader = new MappingLoader();
    }

    @Override
    public void parse(Element element, BeanDefinitionRegistry beanDefinitionRegistry) {

        if(!element.getName().equals("mapper-scan")){
            return;
        }

        String packageName = element.attributeValue("base-package");
        Set<Class<?>> allClasses = mapperScanner.scan(packageName);
        allClasses.stream().filter(beanClass -> beanClass.isAnnotationPresent(Repository.class)).forEach(beanClass ->{
            Repository repository = beanClass.getAnnotation(Repository.class);
            String beanName = repository.name();
            if(Strings.isNullOrEmpty(beanName)){
                beanName = beanClass.getName();
                beanName = beanName.substring(beanName.lastIndexOf(".") + 1);
                beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);

                InvocationHandler ih = new MapperProxy<>(sqlSession, beanClass);
                Class<?> proxyClass = Proxy.getProxyClass(beanClass.getClassLoader(), new Class[]{beanClass});
                BeanDefinition beanDefinition = BeanDefinition.builder()
                        .beanClass(proxyClass).beanClassName(beanName)
                        .constructArgs(new Object[]{ih})
                        .constructorParams(new Class<?>[]{InvocationHandler.class})
                        .build();
                beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
            }
        });

        String fileName = element.attributeValue("location");
        try {
            mappingLoader.load(ClassLoader.getSystemResources(fileName).nextElement().getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
