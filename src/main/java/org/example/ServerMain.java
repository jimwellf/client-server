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
    static BufferedReader in = null;
    static PrintWriter out = null;
    static Socket clientSocket = null;
    static ServerSocket serverSocket = null;

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
            System.out.println("Server accepted");

            manageClient();

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageClient() {
        in = allocateReader();
        out = allocateWriter();
        handleInput();
    }

    private static void handleInput() {
        String userInput;
        try {
            while ((userInput = in.readLine()) != null) {
                System.out.println("Client: " + (userInput));
                out.println(process(userInput));
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    private static String process(String input) {
        if (input.contains("+")) {
            String[] operators = input.split("\\+");
            int result = 0;
            for (int i = 0; i < operators.length; i++) {
                result += Integer.parseInt(operators[i]);
            }
            return Integer.toString(result);

        } else if (input.contains("-")) {
            String[] operators = input.split("-");
            int result = Integer.parseInt(operators[0]);
            for (int i = 1; i < operators.length; i++) {
                result -= Integer.parseInt(operators[i]);
            }
            return Integer.toString(result);

        } else if (input.contains("*")) {
            String[] operators = input.split("\\*");

            int result = 1;
            for (int i = 0; i < operators.length; i++) {
                result *= Integer.parseInt(operators[i]);
            }
            return Integer.toString(result);

        } else if (input.contains("/")) {
            String[] operators = input.split("/");
            int result = Integer.parseInt(operators[0]);
            for (int i = 1; i < operators.length; i++)
            {
                result = result / Integer.parseInt(operators[i]);
            }
            return Integer.toString(result);

        } else {
            input = input.toUpperCase();
        } return input;
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
        } catch (IOException e) {
            System.out.println("Accept failed" + e);
            return null;
        } return clientSocket;
    }

    private static BufferedReader allocateReader() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed" + e);
            return null;
        } return in;
    }

    private static PrintWriter allocateWriter() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return out;
    }
}



