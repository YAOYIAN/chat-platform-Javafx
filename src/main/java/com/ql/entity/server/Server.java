package com.ql.entity.server;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
   // public static Map<String,Map<Socket,String>> onLine_all_qun_Sockets = new HashMap<>();
    //空间名，Socket，人名
    public static Map<Socket,String> onLineSockets = new HashMap<>();
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);
            HandlerSocketServerPool pool = new HandlerSocketServerPool(90,160);
            while (true){
                Socket socket = serverSocket.accept();
                Runnable target = new ServerRunnableTarget(socket);
                pool.execute(target);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
