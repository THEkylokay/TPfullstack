package fr.insa.acid;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Entrez calcul (ex: 3+2) ou 'exit': ");
            String input = sc.nextLine();

            try (Socket socket = new Socket("localhost", 5000);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(input);
                if ("exit".equalsIgnoreCase(input)) break;

                System.out.println("Résultat : " + in.readLine());
            } catch (IOException e) {
                System.err.println("Erreur de connexion au middleware.");
                break;
            }
        }
        sc.close();
    }
}