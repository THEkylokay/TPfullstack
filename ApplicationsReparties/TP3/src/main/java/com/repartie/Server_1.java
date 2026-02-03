package TP3.src.main.java.com.repartie;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server_1 {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Server_1 running on port 5001...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String input = in.readLine();
                int n = Integer.parseInt(input);
                String result = generateRandomString(n);
                out.println(result);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomString(int n) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(rand.nextInt(10)); // chiffre entre 0 et 9
            sb.append(";");
        }
        return sb.toString();
    }
}
