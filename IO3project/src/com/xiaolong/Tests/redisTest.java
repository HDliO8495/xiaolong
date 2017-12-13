package com.xiaolong.Tests;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiaolong on 2017/12/3.
 */
public class redisTest {

    private Jedis jedis;

    @Before
    public void beforeConnect(){
        jedis = new Jedis("127.0.0.1",6379);

    }

    @Test
    public void redisTestOne(){

        Set<String> set = new HashSet<String>();
        set = jedis.keys("*");
        for(String s:set){
            System.out.println(s);
        }


    }

}
