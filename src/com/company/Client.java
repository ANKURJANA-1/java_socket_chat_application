package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client() {
        try {
            System.out.println("Sending request to server......");
            socket = new Socket("127.0.0.1", 8000);
            System.out.println("Connection done..");
            reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

            writer = new PrintWriter(
                    socket.getOutputStream()
            );
            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startWriting() {
        Runnable r1 = () -> {
            System.out.println("Writer started....");
            try {
                while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    writer.println(content);
                    writer.flush();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(r1).start();
    }

    private void startReading() {
        Runnable r2 = () -> {
            System.out.println("Reader started....");
            try {
                while (true && !socket.isClosed()) {
                    String msg = reader.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat.");
                        break;
                    }
                    System.out.println("Server: " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {

        System.out.println("Client Start.....");
        new Client();
    }
}
