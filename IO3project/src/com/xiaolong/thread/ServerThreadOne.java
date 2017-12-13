package com.xiaolong.thread;

/**
 * Created by xiaolong on 2017/12/6.
 */
import com.xiaolong.domain.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
 *   服务器线程的作用主要是:
 *   1.接收来自客户端的信息
 *   2.将接收到的信息解析，并转发给目标客户端
 * */
public class ServerThreadOne extends Thread {

    private User user;
    private List<User> list;

    public ServerThreadOne(User user, List<User> list) {
        this.user = user;
        this.list = list;
    }

    public void run() {
        try {
            while (true) {
                // 信息的格式：(login||logout||say),发送人,收发人,信息体
                //不断地读取客户端发过来的信息
                String msg= user.getBr().readLine();
                System.out.println(msg);
                String[] str = msg.split(",");
                switch (str[0]) {
                    case "logout":
                        remove(user);// 移除用户
                        break;
                    case "say":
                        sendToClient(str[1], msg); // 转发信息给特定的用户
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("异常");
        } finally {
            try {
                user.getBr().close();
                user.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToClient(String username, String msg) {

        for (User user : list) {
            if (user.getUserName().equals(username)) {
                try {
                    PrintWriter pw =user.getPw();
                    pw.println(msg);
                    pw.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void remove(User user2) {
        list.remove(user2);
    }
}
