package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain
{
    public static void main(String[] args) {
        String hostName = "127.0.0.1";
        int portNumber = 1234;

        Socket echoSocket = null;
        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("Cannot reach server " + e);
        }
        System.out.println("Write something");

        PrintWriter out = null; //allocate to write answer to client.
        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Writer failed" + e);
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Reader failed " + e);
        }

        BufferedReader stdIn;
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput;

        while (true) {
            try {
                if ((userInput = stdIn.readLine()) != null)
                out.println(userInput);
                System.out.println("Server echo: " + in.readLine());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
