package com.xiaolong.controller;

/**
 * Created by xiaolong on 2017/12/6.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolong.domain.User;
import com.xiaolong.service.UserService;
import com.xiaolong.service.impl.UserServiceImpl;
import com.xiaolong.util.ApplicationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


//@ServerEndpoint("/webSocketListen")
@ServerEndpoint(value = "/webSocketListen/{userName}/{userPass}")
public class WebSocketListen{

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private String name;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    //private static CopyOnWriteArraySet<Map<String,Session>> webSocketSet = new CopyOnWriteArraySet<Map<String,Session>>();
    //socketId对应用户名
    private static ConcurrentMap<String,String> socketIdUserNameMap = new ConcurrentHashMap<String,String>();
    //用户名对应session
    private static ConcurrentMap<String,HashSet> userNameSessionSetMap = new ConcurrentHashMap<String,HashSet>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private ObjectMapper objectMapper = new ObjectMapper();

    private static UserService userService;
    /**
     * 连接建立成功调用的方法
     * @param session
     * 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen()
    public void onOpen(@PathParam(value="userName") String userName,@PathParam(value="userPass") String userPass,Session session){

        if(null == userService){
            String path = this.getClass().getClassLoader().getResource("").getPath();
            System.out.println("path = " + path);
            String filepath = path + "/config/spring/beanContext.xml";
            ApplicationContext applicationContext = new FileSystemXmlApplicationContext(filepath);
            userService = (UserService)applicationContext.getBean("GeneralBaseTranTwo");
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserPass(userPass);
        Integer result = userService.selectUserExists(user);
        if(result == 0){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socketIdUserNameMap.put(session.getId(),user.getUserName());
        if(null != userNameSessionSetMap.get(user.getUserName())){
            HashSet<Session> sessionSet = (HashSet<Session>)userNameSessionSetMap.get(user.getUserName());
            sessionSet.add(session);
        }else{
            HashSet<Session> sessionSet = new HashSet<Session>();
            sessionSet.add(session);
            userNameSessionSetMap.put(user.getUserName(),sessionSet);
        }
        addOnlineCount(); // 在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        //webSocketSet.remove(this); // 从set中删除
        String socketId = session.getId();
        String userName=socketIdUserNameMap.get(socketId);
        socketIdUserNameMap.remove(socketId);

        HashSet<Session> sessionSet = userNameSessionSetMap.get(userName);
        if(sessionSet.size()==1){
            userNameSessionSetMap.remove(userName);
        }else{
            for(Session s: sessionSet){
                if(s.getId().equals(session.getId())){
                    sessionSet.remove(s);
                    break;
                }
            }
        }
        subOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     * @param session
     *            可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //信息格式：message = {"users":["user1","user2","user3"],"msg":"news.."}
        // 群发消息
/*        for (Map<String,Object> item : webSocketSet) {
            //if((Session)item.get("session") != session){过滤后自己收不到
                try {
                    sendMessage((Session)item.get("session"),message);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            //}
        }*/

        try {
            Map<String,Object> map = objectMapper.readValue(message,Map.class);
            String msg = map.get("msg").toString();
            List<String> userLists = (ArrayList)map.get("users");
            for(String s:userLists){
                sendMessage(userNameSessionSetMap.get(s),msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param sessionSet
     * @param message
     * @throws IOException
     */
    public void sendMessage(HashSet<Session> sessionSet,String message) throws IOException {
        //this.session.getBasicRemote().sendText(message);
        for(Session session:sessionSet){
            session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketListen.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketListen.onlineCount--;
    }
}