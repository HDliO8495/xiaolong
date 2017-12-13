package com.xiaolong.domain;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by xiaolong on 2017/11/30.
 */
public class User {
        private Integer id;
        private String userName;
        private String userPass;
        private String userAge;
        private String userAddress;
        private Socket socket;
        private BufferedReader br;
        private PrintWriter pw;

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        public BufferedReader getBr() {
            return br;
        }

        public void setBr(BufferedReader br) {
            this.br = br;
        }

        public PrintWriter getPw() {
            return pw;
        }

        public void setPw(PrintWriter pw) {
            this.pw = pw;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getUserPass() {
            return userPass;
        }
        public void setUserPass(String userPass) {
            this.userPass = userPass;
        }
        public String getUserAge() {
            return userAge;
        }
        public void setUserAge(String userAge) {
            this.userAge = userAge;
        }
        public String getUserAddress() {
            return userAddress;
        }
        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public User(){

        }

        public User(String name, final Socket socket){
            this.setUserName(name);
            this.socket = socket;
            try {
                this.br = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.pw = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
