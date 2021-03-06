package com.dynamicLifeClient;

import com.dynamicLifeClient.ClientThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 5000)){
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            String userInput, response;
            String clientName = "empty";
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            do{
                if(clientName.equals("empty")){
                    System.out.println("Enter a name");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    output.println(userInput);
                    if(userInput.equals("exit")){
                        break;
                    }
                }
                else {
                    String message = ("(" + clientName + "): ");
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println(message + userInput);
                    if(userInput.equals("exit")){
                        break;
                    }
                }
            }
            while (!userInput.equals("exit"));
        }
        catch (Exception e){
            System.out.println("Error in Client/main: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
