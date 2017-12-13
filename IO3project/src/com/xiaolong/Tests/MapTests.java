package com.xiaolong.Tests;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaolong on 2017/12/7.
 */
public class MapTests {

    @Test
    public void removeElement(){
        Map<String,String> map  = new HashMap<String,String>();
        map.put("1","123");
        map.put("2","123");
        System.out.println(map.get("1")+"+++++"+map.size());
        map.remove("1");
        System.out.println(map.get("1")+"+++++"+map.size());
    }

}
