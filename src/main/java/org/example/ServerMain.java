package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain
{
    static int portNumber = 1234;
    static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;

    public static void main(String[] args) {
        System.out.println("Server started!");

/*
        if(argc == 2) {
            portNumber = Integer.parseInt(args[1]);
        } else {
            portNumber = Prefs.ReadPortFromJSON();
        }
*/
        serverSocket = openServer();
        if (serverSocket == null) {
            return;
        }

        while (true) {
            clientSocket = openClientSocket();

            Thread th = new Thread(() -> {
                        ClientHandler clientHandler = new ClientHandler();
                        clientHandler.handleReaderPrinter(clientSocket);
                        closeClientSocket();
                    });
            th.start();
        }
    }

    private static ServerSocket openServer() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return serverSocket;
    }

    private static Socket openClientSocket() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Server accepted");
        } catch (IOException e) {
            System.out.println("Accept failed" + e);
            return null;
        } return clientSocket;
    }

    private static void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



