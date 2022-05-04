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

    public static void main(String[] args) {
        System.out.println("Server started!");

/*
        if(argc == 2) {
            portNumber = Integer.parseInt(args[1]);
        } else {
            portNumber = Prefs.ReadPortFromJSON();
        }
*/

        ServerSocket serverSocket = openToServer();
        if (serverSocket == null) {
            return;
        }

        Socket clientSocket = openClientSocket(serverSocket);

        System.out.println("Server accepted");

        BufferedReader in = allocateReader(clientSocket);
        PrintWriter out = allocateWriter(clientSocket);

        String userInput;
        try {
            while((userInput = in.readLine()) != null)
            {
                System.out.println("Client: " + userInput);
                out.println(userInput.toUpperCase() + " haaaaaa.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ServerSocket openToServer() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return serverSocket;
    }

    private static Socket openClientSocket(ServerSocket serverSocket) {
        Socket clientSocket;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Accept failed" + e);
            return null;
        } return clientSocket;
    }


    private static BufferedReader allocateReader(Socket clientSocket) {
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed" + e);
            return null;
        } return in;
    }

    private static PrintWriter allocateWriter(Socket clientSocket) {
        PrintWriter out;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return out;
    }

    private void proccess() {

    }
}



