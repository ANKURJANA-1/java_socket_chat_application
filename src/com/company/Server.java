package com.company;

import java.io.*;
import java.net.*;
import java.util.Locale;


public class Server {

    private ServerSocket server;
    private Socket socket;

    private BufferedReader reader;
    private PrintWriter writer;

    public Server() {
        try {
            server = new ServerSocket(8000);
            System.out.println("Server is ready to accept connection.");
            System.out.println("Waiting..............");
            socket = server.accept();

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
                    String msg = reader.readLine().toLowerCase(Locale.ROOT);
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat.");
                        break;
                    }
                    System.out.println("Client: " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Server going to start...............");
        new Server();
    }
}
