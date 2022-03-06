package com.example.dynamiclifeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    SocketThread socketThread;
    int health = 20;
    int opponentHealth = 20;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView healthTotal = (TextView) findViewById(R.id.healthTextView);
        TextView opponentTotal = (TextView) findViewById(R.id.opponent);
        Button healthUp = (Button) findViewById(R.id.lifeUpButton);
        Button healthDown = (Button) findViewById(R.id.lifeDownButton);

        healthUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                health++;
                healthTotal.setText(String.valueOf(health));
                socketThread = new SocketThread();
                new Thread(socketThread).start();
            }
        });

        healthDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                health--;
                healthTotal.setText(String.valueOf(health));
                socketThread = new SocketThread();
                new Thread(socketThread).start();

            }
        });
        opponentTotal.setText(String.valueOf(opponentHealth));
    }
    private class SocketThread implements Runnable{

        private String message = String.valueOf(health);
        Socket socket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        String response;


        @Override
        public void run() {
            try {
                socket = new Socket("192.168.0.159", 5000);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(message);
                dataOutputStream.close();
                dataOutputStream.flush();
                dataInputStream = new DataInputStream(socket.getInputStream());
                response = dataInputStream.readUTF();
                opponentHealth = Integer.parseInt(response);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
//    public static void connect() {
//        try (Socket socket = new Socket("192.168.0.159", 5000)) {
//            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            PrintWriter output = new PrintWriter(socket.getOutputStream());
////            ClientThread clientThread = new ClientThread(socket);
////            clientThread.start();
//            try {
//                output.println("Socket Connection");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("sent output to server");
//            output.println("Socket Connection");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}