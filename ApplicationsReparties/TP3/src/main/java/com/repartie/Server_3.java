package TP3.src.main.java.com.repartie;

import java.io.*;
import java.net.*;

public class Server_3 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5003)) {
            System.out.println("Server_3 running on port 5003...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String input = in.readLine();
                int result = meanFromString(input);
                out.println(result);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int meanFromString(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += c - '0';
        }
        return sum / str.length();
    }
}
