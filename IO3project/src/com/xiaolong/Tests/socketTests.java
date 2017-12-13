package com.xiaolong.Tests;

import com.xiaolong.domain.User;
import com.xiaolong.thread.ClientThread;
import com.xiaolong.thread.ServerThreadOne;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by xiaolong on 2017/12/5.
 */
public class socketTests {

    @Test
    public void testOne() throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        inetAddress.getHostName();
        inetAddress.getHostAddress();
        byte[] bytes = inetAddress.getAddress();
        System.out.println("");

        URL baidu = new URL("https://www.baidu.com");
        URL url = new URL(baidu,"/s?ie=utf-8&f=8&rsv_bp=1&tn=monline_3_dg&wd=java%20socket&oq=socket&rsv_pq=b050f6610000757f&rsv_t=6a4atQAdJIkRcPry5CY6%2BO1Rl6ljQNnUhHFvK7AHxtcFjxMOlrNQLzjpUlVUcoe5g2Ey&rqlang=cn&rsv_enter=1&rsv_sug3=3&rsv_sug1=3&rsv_sug7=100&bs=socket");
        url.getProtocol();//获取协议
        url.getHost();//获取主机
        url.getPort();//如果没有指定端口号，根据协议不同使用默认端口。此时getPort()方法的返回值为 -1
        url.getPath();//获取文件路径
        url.getFile();//文件名，包括文件路径+参数
        url.getRef();//相对路径，就是锚点，即#号后面的内容
        url.getQuery();//查询字符串，即参数

        InputStream inputStream = baidu.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String data;
        while(null != (data=bufferedReader.readLine())){
            System.out.println(data);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        ServerSocket serverSocket = new ServerSocket(10086);
        Socket socket = serverSocket.accept();
        InputStream inputStream1 = socket.getInputStream();
        InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream1);
        BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader);
        while(null != (data=bufferedReader1.readLine())){
            System.out.println("我是服务器");
        }
        socket.shutdownOutput();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.write("Hello,What the fucking horse.");
        printWriter.flush();

    }

    @Test
    public void socketServerTest(){
        // 实例化一个list,用于保存所有的User
        List<User> list = new ArrayList<User>();
        // 创建绑定到特定端口的服务器套接字
        @SuppressWarnings("resource")
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9997);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务端正在开始~");
        // 循环监听客户端连接
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("shoudao");
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 每接受一个线程，就随机生成一个一个新用户
            User user = new User("user" + Math.round(Math.random() * 100),socket);
            System.out.println(user.getUserName() + "正在登录。。。");
            list.add(user);
            // 创建一个新的线程，接收信息并转发
            ServerThreadOne thread = new ServerThreadOne(user, list);
            thread.start();
        }
    }

    @Test
    public void socketClientTestOne(){
        try {
            Socket socket = new Socket("localhost", 9997);
            //开启一个线程接收信息，并解析
            ClientThread thread=new ClientThread(socket);
            thread.start();
            //主线程用来发送信息
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out=new PrintWriter(socket.getOutputStream());
            while(true)
            {
                String s=br.readLine();
                out.println(s);
                //         out.write(s+"\n");
                out.flush();
            }
        }catch(Exception e){
            System.out.println("服务器异常");
        }
    }

    @Test
    public void socketClientTestTwo(){
        try {
            Socket socket = new Socket("localhost", 9997);
            //开启一个线程接收信息，并解析
            ClientThread thread=new ClientThread(socket);
            thread.start();
            //主线程用来发送信息
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out=new PrintWriter(socket.getOutputStream());
            while(true)
            {
                String s=br.readLine();
                out.println(s);
                //         out.write(s+"\n");
                out.flush();
            }
        }catch(Exception e){
            System.out.println("服务器异常");
        }
    }

    @Test
    public void socketClientTestTo(){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(s);
    }



}
