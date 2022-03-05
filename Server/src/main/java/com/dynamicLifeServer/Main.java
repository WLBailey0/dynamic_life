package com.dynamicLifeServer;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();
        try(ServerSocket serverSocket = new ServerSocket(5000)){
            while (true){
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, threadList);
                //start the thread
                threadList.add(serverThread);
                serverThread.start();
            }
        }
        catch (Exception e){
            System.out.println("Error in main: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
