package com.xiaolong.Tests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolong.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaolong on 2017/12/1.
 */
public class JunitTests {
    @Test
    public void applicationContexBean(){
        System.out.println("hello");
        String s = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println(s);
        s=s+"/config/spring/applicationContext.xml";
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(s);
        User u = (User)applicationContext.getBean("user");
        System.out.println(u.getUserName());
    }

    @Test
    public void jdbcTemplateTestJdbc(){
        System.out.println("sfsd ");
        JdbcTemplate jdbcTemplate;
        String s = this.getClass().getClassLoader().getResource("").getPath()+"/config/spring/applicationContext.xml";
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(s);
        jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
        RowMapper rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                return user;
            }
        };
        List<User> list = jdbcTemplate.query("select * from user",rowMapper);
        for(User u : list){
            String name=u.getUserName();
            System.out.println(name);
        }
    }

    @Test
    public void jsonText() throws IOException {
        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setId(12);
        user.setUserName("xiaolong");
        user.setUserAge("24");
        user.setUserAddress("guangdong");
        User user1 = new User();
        user1.setId(13);
        user1.setUserName("huangren");
        user1.setUserAge("34");
        user1.setUserAddress("shenzhen");
        list.add(user);
        list.add(user1);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(list);
        System.out.println(s);
        List<User> listUser = objectMapper.readValue(s,ArrayList.class);
        User[] arrayUser = objectMapper.readValue(s,User[].class);
        System.out.println(listUser);
        System.out.println(arrayUser);
        List<HashMap<String,Object>> mapUser=objectMapper.readValue(s,ArrayList.class);
        System.out.println(mapUser);
        s=objectMapper.writeValueAsString(arrayUser);
        System.out.println(s+"++++++++");
        s=objectMapper.writeValueAsString(mapUser);
        System.out.println(s+"++++++++");
    }

    @Test
    public void jacksonTestTwo() throws Exception{
        User u = new User();
        u.setUserName("xiaolong");
        u.setUserAge("18");
        String str = "{\n" +
                "    id: 12,\n" +
                "    userName: xiaolong,\n" +
                "    userPass: null\n" +
                "}";
        String str2 = "{\"id\": \"12\",\"userName\": \"xiaolong\",\"userPass\": \"null\"}";
        String str3 = "{\"users\":[\"user1\",\"user2\",\"user3\"],\"msg\":\"news\"}";
        String str4 = "{\"msg\":\"news\",\"users\":\"[\"user1\",\"user2\",\"user3\"]\"}";
        String str5 = "[user1,user2,user3]";
        String str6 = "{\"users\":[\"summer\"],\"msg\":\"news..\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Map<String,Object> map = objectMapper.readValue(str6,Map.class);
        System.out.println(map.get("users").toString());
        List users = (ArrayList)map.get("users");

        String []s = new String[]{"user1","user2","user3"};
        Map map1 = new HashMap();
        map1.put("msg","news");
        map1.put("users",s);
        String map1String = objectMapper.writeValueAsString(map1);
        System.out.println(map1String);


        Object[] strings = objectMapper.readValue(str5,Object[].class);
        System.out.println("");
    }

    @Test
    public void jackFiveText() throws IOException {
        String str = "[{\"id\":1,\"userName\":\"summer\",\"userPass\":\"12\",\"userAge\":\"100\",\"userAddress\":\"shanghai,pudong\",\"socket\":null,\"br\":null,\"pw\":null},{\"id\":2,\"userName\":\"xiaolong\",\"userPass\":\"1234\",\"userAge\":\"98\",\"userAddress\":\"guangdong_shenzhen\",\"socket\":null,\"br\":null,\"pw\":null},{\"id\":3,\"userName\":\"huangren\",\"userPass\":\"456\",\"userAge\":\"66\",\"userAddress\":\"guangdong_guangzhou\",\"socket\":null,\"br\":null,\"pw\":null},{\"id\":5,\"userName\":\"huawei\",\"userPass\":null,\"userAge\":\"61\",\"userAddress\":\"beijing\",\"socket\":null,\"br\":null,\"pw\":null},{\"id\":6,\"userName\":\"xiaoyu\",\"userPass\":null,\"userAge\":\"88\",\"userAddress\":\"huangshan\",\"socket\":null,\"br\":null,\"pw\":null}]";
        ObjectMapper objectMapper = new ObjectMapper();
        Object[] strings = objectMapper.readValue(str,Object[].class);
        String s = objectMapper.writeValueAsString(strings);
        System.out.println(s);
    }

}
