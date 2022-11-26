package com.ql.entity.client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    String IP = "";
    String name = "";
    String password ="";
    String qun_now_in = "";
    Socket socket =null;

   public Client(String IP,String name,String password){
        this.IP = IP;
        this.name = name;
        this.password = password;
        try {
            socket = new Socket(IP,9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  boolean login_ask_sever(String name,String password) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(0);
            dos.writeUTF(name.trim());
            dos.writeUTF(password);
            dos.flush();

            System.out.println("login_ask_sever");
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            int sever_answer = dis.readInt();
            if(sever_answer==1){
                System.out.println("服务器回答：1");
                return true;
            }else{
                System.out.println("服务器回答："+sever_answer);
                return  false;
            }
        }catch (Exception e) {
            System.out.println("something wrong");
        }
        return false;
    }

    public  boolean register_ask_sever(String name,String password) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(-1);
            dos.writeUTF(name.trim());
            dos.writeUTF(password);
            dos.flush();
            System.out.println("register_ask_sever");
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int sever_answer = dis.readInt();
            if(sever_answer==1){
                System.out.println("服务器回答：1");
                return true;
            }else{
                System.out.println("服务器回答："+sever_answer);
                return  false;
            }
        }catch (Exception e) {
            System.out.println("can't regist");
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return IP;
    }

    public String getPassword() {
        return password;
    }

    public void create_qun_ask_sever(String qun_name){
        try {
            System.out.println("create_qun_ask_sever");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("开始写:");
            dos.writeInt(4);
            dos.writeUTF(qun_name.trim());
            dos.flush();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }

    }
    public void goin_qun_ask_sever(String qun_name ){
        try {
            System.out.println("goin_qun_ask_sever");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("开始写:");
            dos.writeInt(5);
            dos.writeUTF(qun_name.trim());
            dos.flush();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }
    public void qun_i_have_in(){
        try {
            System.out.println("goin_qun_ask_sever");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("开始写:");
            dos.writeInt(6);
            dos.flush();
            System.out.println("qun_i_have_in:waiting for reply");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }
    public void i_will_change(String new_qun){
        try {
            System.out.println("i_will_change");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("开始写:");
            dos.writeInt(7);
            dos.writeUTF(new_qun.trim());
            dos.flush();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }
    public void go_out_qun_ask_sever(String tui_qunname){
        try {
            System.out.println("go_out_qun_ask_sever");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("开始写:");
            dos.writeInt(8);
            dos.writeUTF(tui_qunname.trim());
            dos.flush();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setQun_now_in(String qun_now_in) {
        this.qun_now_in = qun_now_in;
    }

    public String getQun_now_in() {
        return qun_now_in;
    }
    public void uploadfile(String filepath){
        try {
            try(InputStream is = new FileInputStream(filepath)){
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                File file = new File(filepath);
                dos.writeInt(11);
                dos.writeUTF(filepath.substring(filepath.lastIndexOf(".")));
                dos.writeUTF(file.getName());
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) > 0){
                    dos.write(buffer,0,len);
                }
                dos.flush();
                System.out.println("=="+ filepath.substring(filepath.lastIndexOf("."))+"文件已成功发送==");
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e) {
            System.out.println("IP地址错误或图片不存在");
        }
}
    public void downloadfile(String filename){
        try {
            System.out.println("downloadfile");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("开始下载:");
            dos.writeInt(22);
            dos.writeUTF(filename);
            dos.flush();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }
    public void delete_file(String filename){
        try {
            System.out.println("delete_file");
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("准备删除:");
            dos.writeInt(23);
            dos.writeUTF(filename);
            dos.flush();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("something wrong");
        }
    }
}


