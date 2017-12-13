package com.xiaolong.Tests;

/**
 * Created by xiaolong on 2017/11/30.
 */
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaolong.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;

    static{
        try{
            //reader    = Resources.getResourceAsReader("config/mybatis/mybatis-config.xml");
            reader    = Resources.getResourceAsReader("config/spring/applicationContext.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }

    public static void main(String[] args) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            //PageHelper.startPage(1,1);
            List<User> list=session.selectList("config.mybatis.mysql.User_SqlMap.selectUserByID",null,new RowBounds(3,1));
            //PageInfo pageInfo = new PageInfo(list);
            //System.out.println(user.getUserAddress());
            System.out.println(list);
            for(User u:list){
                System.out.println(u.getId());
                System.out.println(u.getUserName());
                System.out.println(u.getUserAge());
                System.out.println(u.getUserAddress());
            }
            //System.out.println("++++++++++++++++++++++"+pageInfo);
        } finally {
            session.close();
        }
    }

}