package com.tp.tp7;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5001; // Port du middleware

    public void demarrer() {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connecté au middleware");
            System.out.println("Commandes disponibles:");
            System.out.println("  <mot>  : ajouter un mot (lettres uniquement)");
            System.out.println("  print  : afficher la phrase et la réinitialiser");
            System.out.println("  exit   : quitter");
            System.out.println();
            
            String commande;
            
            while (true) {
                System.out.print("Entrez un mot ou une commande: ");
                commande = scanner.nextLine().trim();
                
                if (commande.isEmpty()) {
                    continue;
                }
                
                out.println(commande);
                
                String reponse = in.readLine();
                System.out.println("Réponse: " + reponse);
                
                if (commande.equals("exit")) {
                    break;
                }
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.demarrer();
    }
}
