package com.tp.tp6;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public void demarrer() {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connecté au serveur");
            System.out.println("Commandes disponibles:");
            System.out.println("  +N, -N, *N, /N  : opérations arithmétiques");
            System.out.println("  print           : afficher la valeur");
            System.out.println("  exit            : quitter");
            System.out.println();
            
            String commande;
            
            while (true) {
                System.out.print("Entrez une commande: ");
                commande = scanner.nextLine().trim();
                
                if (commande.isEmpty()) {
                    continue;
                }
                
                out.println(commande);
                
                String reponse = in.readLine();
                System.out.println("Serveur: " + reponse);
                
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
