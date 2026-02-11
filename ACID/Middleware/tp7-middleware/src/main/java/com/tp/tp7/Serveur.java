package com.tp.tp7;

import java.io.*;
import java.net.*;

public class Serveur {
    private static final int PORT = 5002;
    private StringBuilder chaine;

    public Serveur() {
        this.chaine = new StringBuilder();
    }

    public void demarrer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré sur le port " + PORT);
            
            while (true) {
                System.out.println("\nEn attente du middleware...");
                Socket middlewareSocket = serverSocket.accept();
                System.out.println("Middleware connecté");
                
                traiterMiddleware(middlewareSocket);
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur: " + e.getMessage());
        }
    }

    private void traiterMiddleware(Socket middlewareSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(middlewareSocket.getInputStream()));
            PrintWriter out = new PrintWriter(middlewareSocket.getOutputStream(), true)
        ) {
            String message;
            
            while ((message = in.readLine()) != null) {
                System.out.println("Reçu: " + message);
                
                if (message.equals("exit")) {
                    out.println("Déconnexion du serveur");
                    System.out.println("Middleware déconnecté");
                    break;
                } else if (message.equals("print")) {
                    String resultat = chaine.toString();
                    if (resultat.isEmpty()) {
                        resultat = "(vide)";
                    }
                    out.println("PHRASE: " + resultat);
                    System.out.println("Phrase envoyée: " + resultat);
                    chaine.setLength(0); // Réinitialiser
                    System.out.println("Chaîne réinitialisée");
                } else {
                    // Ajouter le mot en minuscules
                    if (chaine.length() > 0) {
                        chaine.append(" ");
                    }
                    chaine.append(message.toLowerCase());
                    out.println("OK: Mot ajouté");
                    System.out.println("Chaîne actuelle: " + chaine.toString());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le middleware: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Serveur serveur = new Serveur();
        serveur.demarrer();
    }
}
