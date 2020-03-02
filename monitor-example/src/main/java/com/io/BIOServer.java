package com.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BIOServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)){
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection from " + clientSocket.getRemoteSocketAddress());
                Scanner input = new Scanner(clientSocket.getInputStream());
                while (true){
                    String request = input.nextLine();
                    if ("quit".equals(request)){
                        break;
                    }
                    System.out.println(String.format("From %s : %s", clientSocket.getRemoteSocketAddress(), request));
                    String response = "Frome BIOServer Hello " + request;
                    clientSocket.getOutputStream().write(response.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
