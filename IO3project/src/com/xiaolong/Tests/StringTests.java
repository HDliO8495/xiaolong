package com.xiaolong.Tests;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.xiaolong.domain.User;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaolong on 2017/12/5.
 */
public class StringTests {

    @Test
    public void stringCharTest(){
        String str = "jfi123494n1sfk3k3ks9k";
        int z = 0;
        for(int i=0;i<str.length();i++){
            String theBoolean = "false";
            if(Character.isDigit(str.charAt(i))){
                z++;
                theBoolean = "true";
            }
            System.out.println(str.charAt(i)+"++++"+theBoolean);
        }
        System.out.println("++++++++++++"+z);
    }

    @Test
    public void StringToCharArrayTest(){
        String str = "k281h89shr89dh3if8";
        String []s = new String[2];
        char chars[] = new char[str.length()+1];
        System.out.println("++++++++++++"+chars.length);
        chars=str.toCharArray();
        for(char c:chars){
            System.out.println(c+"++++");
        }
        System.out.println(str.length()+"++++"+chars.length);
    }

    @Test
    public void StringSpiltTest(){
        String str = "893n,jsif,8o3,98a,d,ioafe4,sf";
        String []strings=str.split(",");
        for(String s:strings){
            System.out.println(s);
        }
    }

    @Test
    public void StringBuffTest(){
        StringBuffer stringBuffer = new StringBuffer();
        String strAppend = "++++";
        List list = new ArrayList();
        User user = new User();
        user.setUserName("xiaolong");
        Boolean b = false;
        String str = "hello";
        int digital = 101;
        list.add(user);
        list.add(b);
        list.add(str);
        list.add(digital);
        for(Object o:list){
            stringBuffer.append(o).append(strAppend);
        }
        stringBuffer.delete(stringBuffer.length()-strAppend.length(),stringBuffer.length());
        System.out.println(stringBuffer);
    }

    @Test
    public void StringFourTest(){
        String one = "12";
        String two = "12";
        System.out.println(one == two);

        A a = new A();
        B b = new B();

    }

    class A {
        public void a(){
            System.out.println("aaaa");
        }
    }

    class B extends StringTests.A{
        public void a(){
            System.out.println("bbbb");
        }
    }

}
