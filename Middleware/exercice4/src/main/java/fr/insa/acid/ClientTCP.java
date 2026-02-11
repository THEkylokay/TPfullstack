package fr.insa.acid;

import java.io.*;
import java.net.*;

public class ClientTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 6789;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connecté au middleware. Tapez une phrase (vide pour quitter).");

            String ligne;
            while (true) {
                System.out.print("> ");
                ligne = clavier.readLine();

                if (ligne == null || ligne.trim().isEmpty()) {
                    break;
                }

                out.println(ligne);
                out.flush();

                String reponse = in.readLine();
                if (reponse != null) {
                    System.out.println("Nombre de voyelles : " + reponse);
                }
            }

            System.out.println("Au revoir.");
        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}