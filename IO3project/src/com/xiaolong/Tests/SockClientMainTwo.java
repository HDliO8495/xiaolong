package com.xiaolong.Tests;

import com.xiaolong.thread.ClientThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by xiaolong on 2017/12/6.
 */
public class SockClientMainTwo {

    public static void main(String []agrs){
        // 信息的格式：(login||logout||say),发送人,收发人,信息体
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

}
