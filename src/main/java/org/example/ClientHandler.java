package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {

    private static BufferedReader in = null;
    private static PrintWriter out = null;

    public void handleReaderPrinter(Socket clientSocket) {
        in = allocateReader(clientSocket);
        out = allocateWriter(clientSocket);
        handleInput();
    }

    public void handleInput() {
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
                try {
                    result = result / Integer.parseInt(operators[i]);
                }
                catch (ArithmeticException e) {
                    String error = "Divided by zero operation cannot possible";
                    return error;
                }

            }
            return Integer.toString(result);

        }  else if (input.contains(":")) {
            String[] operators = input.split(":");
            int result = Integer.parseInt(operators[0]);
            for (int i = 1; i < operators.length; i++)
            {
                try {
                    result = result / Integer.parseInt(operators[i]);
                }
                catch (ArithmeticException e) {
                    String error = "Divided by zero operation cannot possible";
                    return error;
                }

            }
            return Integer.toString(result);

        } else {
            input = input.toUpperCase();
        } return input;
    }

    private BufferedReader allocateReader(Socket clientSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed" + e);
            return null;
        } return in;
    }

    private PrintWriter allocateWriter(Socket clientSocket) {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return out;
    }
}
