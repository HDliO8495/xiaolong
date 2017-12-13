package com.xiaolong.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolong.domain.User;
import com.xiaolong.service.UserService;
import com.xiaolong.util.ApplicationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.Addressing;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaolong on 2017/12/7.
 */
@RestController
@RequestMapping("/xiaolong/userController")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/selectUsers",method = RequestMethod.GET)
    public List<User> selectUsers(User user, Integer pageNum, Integer pageSize){
        List<User> list = new ArrayList<User>();
        list=userService.selectUsers(user,pageNum,pageSize);
        return list;
    }

    @RequestMapping(value = "/selectUsers2",method = RequestMethod.GET)
    public List<User> selectUsers2(User user, Integer ifPage, Integer pageNum, Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println("path = " + path);
        String filepath = path + "/config/spring/beanContext.xml";




        //ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring/beanContext.xml");
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filepath);
        //ApplicationHelper applicationHelper = (ApplicationHelper)applicationContext.getBean("applicationHelper");userServiceImpl
        //UserService userService = (UserService) applicationHelper.getApplicationContext().getBean("userService");

        UserService userService = (UserService)applicationContext.getBean("GeneralBaseTranTwo");
        List<User> list = new ArrayList<User>();
        list=userService.selectUsers2(user,ifPage,pageNum,pageSize);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);

        String callback = request.getParameter("callback");
        //PrintWriter printWriter = response.getWriter();
        //printWriter.write(callback+"("+json+")");
        Map<String,String> map = new HashMap<>();
        map.put(callback,"1234");
        return list;
    }

    //是否存在用户
    @RequestMapping(value = "/selectUserExists",method = RequestMethod.GET)
    public Integer selectUserExists(User user){
        return userService.selectUserExists(user);
    }

}
