package com.ql.entity.server;

import com.ql.dao.server.UserDao;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerRunnableTarget implements Runnable {
    private final Socket socket;
    public ServerRunnableTarget(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            while (true){
                int flag = dis.readInt();
                if(flag == 2){
                    String newMsg = dis.readUTF();
                    String from_name = Server.onLineSockets.get(socket);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                    String msgFinal = from_name + " " + sdf.format(System.currentTimeMillis()) + "\r\n" + " " + newMsg + "\r\n";
                    String qun_name = UserDao.this_man_in(Server.onLineSockets.get(socket));
                    BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/admin/Desktop/word_mongo/"+qun_name+".txt", true));
                    bw.write(msgFinal);
                    bw.flush();
                    bw.close();
                    ArrayList<String> online_String   = UserDao.with_this_man_in_qun(from_name);
                    Set<Socket> set=  Server.onLineSockets.keySet();
                    Set<Socket> allOnLineSockets = new HashSet<>();
                    for (String str: online_String) {
                        for (Socket socket:set) {
                            if(Objects.equals(Server.onLineSockets.get(socket), str)){
                                allOnLineSockets.add(socket);
                            }
                        }
                    }
                    for (Socket sk : allOnLineSockets) {
                            DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                            dos.writeInt(2);
                            dos.writeUTF(msgFinal.toString());
                            dos.flush();
                    }
                }
                else if(flag == 3){
                    String newMsg = dis.readUTF();
                    String destName = dis.readUTF();
                    String from_name = Server.onLineSockets.get(socket);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                    String msgFinal = from_name + " " + sdf.format(System.currentTimeMillis()) +"私聊模式"+ "\r\n" +
                            " " + newMsg + "\r\n";
                    ArrayList<String> online_String   = UserDao.with_this_man_in_qun(from_name);
                    Set<Socket> set=  Server.onLineSockets.keySet();
                    Set<Socket> allOnLineSockets = new HashSet<>();
                    for (String str: online_String) {
                        for (Socket socket:set) {
                            if(Objects.equals(Server.onLineSockets.get(socket), str)){
                                allOnLineSockets.add(socket);
                            }
                        }
                    }
                        for (Socket sk : allOnLineSockets) {
                            if (Server.onLineSockets.get(sk).trim().equals(destName)||Server.onLineSockets.get(sk).trim().equals(from_name)) {
                                DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                                dos.writeInt(2);
                                dos.writeUTF(msgFinal.toString());
                                dos.flush();
                        }
                    }
                }
                else if(flag == 0){
                    String name = dis.readUTF();
                    String password = dis.readUTF();
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    if(UserDao.login(name,password)){
                        System.out.println("有人连上了!!!!!!!!");
                        System.out.println(name + "----->" + socket.getRemoteSocketAddress());
                        Server.onLineSockets.put(socket, name);
                        Set<Socket> set = Server.onLineSockets.keySet();
                        System.out.println(set.size());
                        dos.writeInt(1);
                    }
                    else
                        dos.writeInt(0);
                    dos.flush();
                }
                else if(flag==-1){
                    String name = dis.readUTF();
                    String password = dis.readUTF();
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    if(UserDao.register(name,password))
                        dos.writeInt(1);
                    else
                        dos.writeInt(0);
                    dos.flush();
                }
                else if(flag==4){
                    String qun_name = dis.readUTF();
                    String name = Server.onLineSockets.get(socket);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(4);
                    if(UserDao.new_qun(qun_name,name)){
                        System.out.println("可以建群！");
                        dos.writeInt(1);
                        ArrayList<String> qun_name_has_in = UserDao.qun_name_has_in(name);

                        for (String qun_name_in:qun_name_has_in) {
                            dos.writeUTF(qun_name_in);
                        }
                        dos.writeUTF("mei_you_la_ha_ha");
                        dos.flush();
                        String dirStr = "C:/Users/admin/Desktop/server/"+qun_name;
                        File directory = new File(dirStr);
                        directory.mkdir();
                        new File("C:/Users/admin/Desktop/word_mongo/"+qun_name+".txt").createNewFile();
                    }
                    else
                        dos.writeInt(0);
                    dos.flush();
                }
                else if(flag==5){
                    String qun_name = dis.readUTF();
                    String name = Server.onLineSockets.get(socket);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(5);
                    if(UserDao.goin_qun(qun_name,name)){
                        dos.writeInt(1);
                        ArrayList<String> qun_name_has_in = UserDao.qun_name_has_in(name);
                        for (String qun_name_in:qun_name_has_in) {
                            dos.writeUTF(qun_name_in);
                        }
                        dos.writeUTF("mei_you_la_ha_ha");
                        dos.flush();
                    }
                    else
                        dos.writeInt(0);

                    dos.flush();
                }
                else if(flag == 6){
                    String name = Server.onLineSockets.get(socket);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    ArrayList<String> qun_name_has_in = UserDao.qun_name_has_in(name);
                    dos.writeInt(6);
                    for (String qun_name:qun_name_has_in) {
                        dos.writeUTF(qun_name);
                    }
                    dos.writeUTF("mei_you_la_ha_ha");
                    dos.flush();
                }
                else if(flag==7){
                    String name = Server.onLineSockets.get(socket);
                    String new_qun = dis.readUTF();
                    String old_qun = UserDao.this_man_in(name);
                    UserDao.change_to_new_qun(name,new_qun);
                    StringBuilder rs2 = new StringBuilder();
                    Collection<String> onlineNames_oldqun =  UserDao.qun_name_online(old_qun);
                    System.out.println("old_qun_online: ");
                    int len = 0;
                    for (String name2 : onlineNames_oldqun) {
                        len+=1;
                        rs2.append(name2).append(Constants.SPILIT);
                    }
                    System.out.println(rs2);
                    if(len!=0){
                        String msg2 = rs2.substring(0, rs2.lastIndexOf(Constants.SPILIT));
                        System.out.println("广播:old_qun " + old_qun);
                        Set<Socket> set_allonline=  Server.onLineSockets.keySet();
                        Set<Socket> OnLineSockets_oldqun = new HashSet<>();
                        for (String name_online_oldqun: onlineNames_oldqun) {
                            for (Socket socket:set_allonline) {
                                if(Objects.equals(Server.onLineSockets.get(socket), name_online_oldqun)){
                                    OnLineSockets_oldqun.add(socket);
                                    System.out.println(socket);
                                }
                            }
                        }
                        for (Socket sk : OnLineSockets_oldqun) {
                            DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                            dos.writeInt(1);
                            dos.writeUTF(msg2);
                            System.out.println(msg2);
                            dos.flush();
                        }
                    }
                    StringBuilder rs = new StringBuilder();
                    Collection<String> onlineNames_newqun =  UserDao.qun_name_online(new_qun);
                    System.out.println("new_qun_online: ");
                    for (String name2 : onlineNames_newqun) {
                        rs.append(name2).append(Constants.SPILIT);
                    }
                    System.out.println(rs);
                    String msg = rs.substring(0, rs.lastIndexOf(Constants.SPILIT));
                    System.out.println("广播:new_qun " + new_qun);
                    Set<Socket> all_online_socket=  Server.onLineSockets.keySet();
                    Set<Socket> allOnLineSockets = new HashSet<>();
                    for (String name_online_newqun: onlineNames_newqun) {
                        for (Socket socket:all_online_socket) {
                            if(Objects.equals(Server.onLineSockets.get(socket), name_online_newqun)){
                                allOnLineSockets.add(socket);
                            }
                        }
                    }
                    for (Socket sk : allOnLineSockets) {
                        DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                        dos.writeInt(1);
                        dos.writeUTF(msg);
                        System.out.println(msg);
                        dos.flush();
                    }
                    File file = new File("C:/Users/admin/Desktop/server/"+new_qun);
                    File[] files = file.listFiles();
                    for(Socket sk:allOnLineSockets){
                        DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                        dos.writeInt(21);
                        if (files != null) {
                            for (File file1:files) {
                                dos.writeUTF("you_dong_xi");
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                                dos.writeUTF(file1.getName());
                                dos.writeUTF(file1.length() / 1024 +"kb");
                                dos.writeUTF(sdf.format(file1.lastModified()));
                            }
                        }
                        dos.writeUTF("mei_you_la_ha_ha");
                        dos.flush();
                    }
                    StringBuilder rs3 = new StringBuilder();
                    Collection<String> permit_newqun =  UserDao.permit_newqun(new_qun);
                    System.out.println("permit_newqun");
                    for (String name_permit:permit_newqun) {
                        System.out.println(name_permit);
                    }
                    System.out.println("over");
                    for (String permit_name : permit_newqun) {
                        rs3.append(permit_name).append(Constants.SPILIT);
                    }
                    String msg3 = rs3.substring(0, rs3.lastIndexOf(Constants.SPILIT));
                    for (Socket sk : allOnLineSockets) {
                        DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                        dos.writeInt(25);
                        dos.writeUTF(msg3);
                        dos.flush();
                    }

                    String qun_name = UserDao.this_man_in(Server.onLineSockets.get(socket));
                    BufferedReader br=new BufferedReader(new FileReader("C:/Users/admin/Desktop/word_mongo/"+qun_name+".txt"));
                    String str="";
                    StringBuilder whole_String = new StringBuilder();
                    while((str=br.readLine())!=null) {
                        whole_String.append(str).append("\n");
                    }
                    br.close();
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(26);
                    dos.writeUTF(whole_String.toString());
                    dos.flush();


                }
                else if(flag==8){
                    String name = Server.onLineSockets.get(socket);
                    String tui_qunname = dis.readUTF();
                    UserDao.tuiqun(name,tui_qunname);
                    ArrayList<String> qun_name_has_in = UserDao.qun_name_has_in(name);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeInt(8);
                    for (String qun_name:qun_name_has_in) {
                        dos.writeUTF(qun_name);
                    }
                    dos.writeUTF("mei_you_la_ha_ha");
                    dos.flush();
                }
                else if(flag == 11){
                    try {
                        String suffix = dis.readUTF();
                        String filename = dis.readUTF();
                        filename = filename.substring(0,filename.lastIndexOf('.'));
                        String qun_name = UserDao.this_man_in(Server.onLineSockets.get(socket));    //精彩不？
                        System.out.println("服务端已经成功接收到了文件类型：" + suffix);
                        OutputStream os = new
                                FileOutputStream("C:/Users/admin/Desktop/server/"
                                + qun_name+"/"+filename + suffix);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = dis.read(buffer)) > 0){
                            os.write(buffer,0,len);
                            if(len<1024)
                                break;
                        }
                        os.close();
                        System.out.println("服务端接收文件保存成功！");
                        Set<Socket> allOnLineSockets = new HashSet<>();
                        Collection<String> onlineNames =  UserDao.qun_name_online(qun_name);
                        Set<Socket> set=  Server.onLineSockets.keySet();
                        for (String name_online: onlineNames) {
                            for (Socket socket:set) {
                                if(Objects.equals(Server.onLineSockets.get(socket), name_online)){
                                    allOnLineSockets.add(socket);
                                    System.out.println(socket);
                                }
                            }
                        }
                        File file = new File("C:/Users/admin/Desktop/server/"+qun_name);
                        File[] files = file.listFiles();
                        for(Socket sk:allOnLineSockets){
                            DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                            dos.writeInt(21);
                            if (files != null) {
                                for (File file1:files) {
                                    dos.writeUTF("you_dong_xi");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                                    dos.writeUTF(file1.getName());
                                    dos.writeUTF(file1.length() / 1024 +"kb");
                                    dos.writeUTF(sdf.format(file1.lastModified()));
                                }
                            }
                            dos.writeUTF("mei_you_la_ha_ha");
                            dos.flush();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else if(flag ==22){
                    try {
                        String filename = dis.readUTF();
                        System.out.println(filename);
                        String qun_name = UserDao.this_man_in(Server.onLineSockets.get(socket));
                        String filepath_server = "C:/Users/admin/Desktop/server/"+qun_name+"/"+filename;
                        try(InputStream is = new FileInputStream(filepath_server)){
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeInt(22);
                            dos.writeUTF(filename);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = is.read(buffer)) > 0){
                                dos.write(buffer,0,len);
                            }
                            dos.flush();
                            System.out.println("=="+ filepath_server.substring(filepath_server.lastIndexOf("."))+"文件已成功发送==");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else if(flag == 23){
                    try {
                        String filename = dis.readUTF();
                        String qun_name = UserDao.this_man_in(Server.onLineSockets.get(socket));
                        File file_delete = new File("C:/Users/admin/Desktop/server/"+qun_name+"/"+filename);
                        if(file_delete.delete())
                            System.out.println("成功删除");
                        else
                            System.out.println("删除失败");
                        //refresh:
                        Set<Socket> allOnLineSockets = new HashSet<>();
                        Collection<String> onlineNames =  UserDao.qun_name_online(qun_name);
                        Set<Socket> set=  Server.onLineSockets.keySet();
                        for (String name_online: onlineNames) {
                            for (Socket socket:set) {
                                if(Objects.equals(Server.onLineSockets.get(socket), name_online)){
                                    allOnLineSockets.add(socket);
                                    System.out.println(socket);
                                }
                            }
                        }
                        File file = new File("C:/Users/admin/Desktop/server/"+qun_name);
                        File[] files = file.listFiles();
                        for(Socket sk:allOnLineSockets){
                            DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                            dos.writeInt(21);
                            if (files != null) {
                                for (File file1:files) {
                                    dos.writeUTF("you_dong_xi");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEE");
                                    dos.writeUTF(file1.getName());
                                    dos.writeUTF(file1.length() / 1024 +"kb");
                                    dos.writeUTF(sdf.format(file1.lastModified()));
                                }
                            }
                            dos.writeUTF("mei_you_la_ha_ha");
                            dos.flush();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            try {
            System.out.println("---有人下线了---");
            String quntuichu =  UserDao.xia_xian(Server.onLineSockets.get(socket));
            Server.onLineSockets.remove(socket);
            Set<Socket> set=  Server.onLineSockets.keySet();
            Set<Socket> allOnLineSockets = new HashSet<>();
            StringBuilder rs = new StringBuilder();
            Collection<String> onlineNames =  UserDao.qun_name_online(quntuichu);
            System.out.println("online: ");
            int len = 0;
            for (String name2 : onlineNames) {
                len+=1;
                rs.append(name2).append(Constants.SPILIT);
                System.out.println(name2);
            }
            if(len!=0){
                String msg = rs.substring(0, rs.lastIndexOf(Constants.SPILIT));
                for (String name_online: onlineNames) {
                    for (Socket socket:set) {
                        if(Objects.equals(Server.onLineSockets.get(socket), name_online)){
                            allOnLineSockets.add(socket);
                            System.out.println(socket);
                        }
                    }
                }
                for (Socket sk : allOnLineSockets) {
                    DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                    dos.writeInt(1);
                    dos.writeUTF(msg);
                    dos.flush();
                }
            }}catch (IOException ex) {
            ex.printStackTrace();
        }
        }
    }
    private void sendMsgToOne(String destName, String msg) throws Exception{
        Set<Socket> allOnLineSockets = Server.onLineSockets.keySet();
        for (Socket sk : allOnLineSockets) {
            if (Server.onLineSockets.get(sk).trim().equals(destName)) {
                DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                dos.writeInt(2);
                dos.writeUTF(msg);
                dos.flush();
            }
        }
    }
    private void sendMsgToAll(int flag, String msg,String qunname) throws Exception {
        Collection<String> onlineNames =  UserDao.qun_name_online(qunname);
        Set<Socket> set=  Server.onLineSockets.keySet();
        Set<Socket> allOnLineSockets = new HashSet<>();
        for (String str: onlineNames) {
            for (Socket socket:set) {
                if(Objects.equals(Server.onLineSockets.get(socket), str)){
                    allOnLineSockets.add(socket);
                }
            }
        }
        for (Socket sk : allOnLineSockets) {
            DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
            dos.writeInt(2);
            dos.writeUTF(msg);
            dos.flush();
        }
    }
}