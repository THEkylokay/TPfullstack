package com.tp.tp8.validation;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientValidation {
    private static final String HOST = "localhost";
    private static final int PORT = 5500;

    public void demarrer() {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connecté au middleware de validation");
            System.out.println("\nCommandes disponibles:");
            System.out.println("  +N, -N, *N, /N  : opérations arithmétiques (avec validation 2PC)");
            System.out.println("  print           : afficher les valeurs des 3 serveurs");
            System.out.println("  exit            : quitter");
            System.out.println("\nLe middleware utilise un protocole de validation à deux phases:");
            System.out.println("  - Phase 1 (PREPARE): Tous les serveurs doivent être prêts (90% de chance)");
            System.out.println("  - Phase 2 (COMMIT): L'opération est appliquée (95% de chance par serveur)");
            System.out.println();
            
            String commande;
            
            while (true) {
                System.out.print("Entrez une commande: ");
                commande = scanner.nextLine().trim();
                
                if (commande.isEmpty()) {
                    continue;
                }
                
                out.println(commande);
                
                // Lire toutes les réponses jusqu'à une ligne vide ou timeout
                String reponse;
                boolean firstLine = true;
                
                while ((reponse = in.readLine()) != null) {
                    if (firstLine) {
                        System.out.println("\nRéponse du middleware:");
                        firstLine = false;
                    }
                    System.out.println("  " + reponse);
                    
                    // Pour les commandes simples, on a qu'une ligne
                    if (commande.equals("exit") || (!commande.equals("print") && !reponse.startsWith("Serveur"))) {
                        break;
                    }
                    
                    // Pour print, on lit 3 lignes (une par serveur)
                    if (commande.equals("print") && reponse.startsWith("Serveur 3")) {
                        break;
                    }
                }
                
                System.out.println();
                
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
        ClientValidation client = new ClientValidation();
        client.demarrer();
    }
}
