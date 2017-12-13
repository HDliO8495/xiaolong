package com.xiaolong.Tests;

import com.xiaolong.domain.TestObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by xiaolong on 2017/12/8.
 */
public class springTest {

    @Test
    public void applicationContextTest(){

        String path = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println("path = " + path);
        String filepath = path + "/config/spring/beanContext.xml";

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring/beanContext.xml" );
        ApplicationContext applicationContext2 = new FileSystemXmlApplicationContext(filepath);
        TestObject userService2 = (TestObject)applicationContext.getBean("testObject");
        System.out.println(userService2.getName());
    }

}
