package TP3.src.main.java.com.repartie;

import java.io.*;
import java.net.*;

public class App {
    public static void main(String[] args) {
        try {
            // 1️⃣ Appel Server_1
            Socket s1 = new Socket("localhost", 5001);
            PrintWriter out1 = new PrintWriter(s1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));

            out1.println("10"); // demande 10 chiffres
            String randomString = in1.readLine();
            System.out.println("Server_1 generated: " + randomString);
            s1.close();

            // 2️⃣ Appel Server_2
            Socket s2 = new Socket("localhost", 5002);
            PrintWriter out2 = new PrintWriter(s2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));

            out2.println(randomString);
            String cleanedString = in2.readLine();
            System.out.println("Server_2 cleaned: " + cleanedString);
            s2.close();

            // 3️⃣ Appel Server_3
            Socket s3 = new Socket("localhost", 5003);
            PrintWriter out3 = new PrintWriter(s3.getOutputStream(), true);
            BufferedReader in3 = new BufferedReader(new InputStreamReader(s3.getInputStream()));

            out3.println(cleanedString);
            String mean = in3.readLine();
            System.out.println("Server_3 mean: " + mean);
            s3.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
