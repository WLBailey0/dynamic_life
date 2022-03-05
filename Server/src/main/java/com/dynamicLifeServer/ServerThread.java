package com.dynamicLifeServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                String outputString = input.readLine();
                if(outputString.equals("exit")){
                    break;
                }
                printToAllClients(outputString);
                System.out.println("Server Received: " + outputString);
            }
        }
        catch (Exception e){
            System.out.println("Error in dynamicLifeServer.ServerThread: " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void printToAllClients(String outputString) {
        for(ServerThread thread : threadList){
            thread.output.println(outputString);
        }
    }
}
