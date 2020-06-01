package com.study.demo.service;

import com.study.demo.dao.UserMapper;
import com.study.demo.dto.Condition;
import com.study.demo.model.User;
import com.study.spring.context.ClassPathXmlApplicationContext;
import com.study.spring.context.config.ApplicationContext;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: yuqi
 * @Date: 2020-03-09 01:17
 */
public class MyStudyServiceTest {

    private MyStudyService myStudyService;

    @Before
    public void before(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        myStudyService = applicationContext.getBean("myStudyService");
    }

    @Test
    public void testInsert() {
        User user = User.builder().name("xiaoyu").sex(1).build();
        System.out.println(myStudyService.insert(user));
    }

    @Test
    public void testQueryAllUser() {
        System.out.println(myStudyService.queryAllUser());
    }

    @Test
    public void testQueryAllUserByName(){
        System.out.println(myStudyService.queryAllUserByName("xiaoyu"));
    }
    @Test
    public void updateByCondition() {
        System.out.println(myStudyService.updateByCondition(Condition.builder().name("xiaoyu").sex(1).build()));
    }

    @Test
    public void deleteByName() {
        System.out.println(myStudyService.deleteByName("xiaoyu"));
    }

    @Test
    public void deleteAll() {
        System.out.println(myStudyService.deleteAll());
    }
}