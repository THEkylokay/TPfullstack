package TP3.src.main.java.com.repartie;

import java.io.*;
import java.net.*;

public class Server_2 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5002)) {
            System.out.println("Server_2 running on port 5002...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String input = in.readLine();
                String result = deletePunct(input);
                out.println(result);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String deletePunct(String str) {
        return str.replace(";", "");
    }
}
