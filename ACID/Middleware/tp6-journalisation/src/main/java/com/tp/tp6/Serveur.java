package com.tp.tp6;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Serveur {
    private static final int PORT = 5000;
    private static final String JOURNAL_FILE = "journal.txt";
    private int nb;

    public Serveur() {
        this.nb = 1;
        chargerJournal();
    }

    private void chargerJournal() {
        File file = new File(JOURNAL_FILE);
        if (!file.exists()) {
            System.out.println("Aucun journal trouvé. nb initialisé à 1");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            nb = 1; // Réinitialiser
            System.out.println("Chargement du journal...");
            
            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();
                if (!ligne.isEmpty() && !ligne.equals("exit") && !ligne.equals("print")) {
                    appliquerOperation(ligne, false);
                }
            }
            
            System.out.println("Journal chargé. Valeur de nb: " + nb);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du journal: " + e.getMessage());
        }
    }

    private void appliquerOperation(String operation, boolean afficher) {
        try {
            char op = operation.charAt(0);
            int valeur = Integer.parseInt(operation.substring(1));
            
            switch (op) {
                case '+':
                    nb += valeur;
                    if (afficher) System.out.println("Addition: nb = " + nb);
                    break;
                case '-':
                    nb -= valeur;
                    if (afficher) System.out.println("Soustraction: nb = " + nb);
                    break;
                case '*':
                    nb *= valeur;
                    if (afficher) System.out.println("Multiplication: nb = " + nb);
                    break;
                case '/':
                    if (valeur != 0) {
                        nb /= valeur;
                        if (afficher) System.out.println("Division: nb = " + nb);
                    } else {
                        if (afficher) System.out.println("Erreur: Division par zéro");
                    }
                    break;
                default:
                    if (afficher) System.out.println("Opération inconnue");
            }
        } catch (Exception e) {
            if (afficher) System.out.println("Erreur dans l'opération: " + e.getMessage());
        }
    }

    private void ecrireJournal(String message) {
        try (FileWriter writer = new FileWriter(JOURNAL_FILE, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("Erreur d'écriture dans le journal: " + e.getMessage());
        }
    }

    public void demarrer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré sur le port " + PORT);
            System.out.println("Valeur initiale de nb: " + nb);
            
            while (true) {
                System.out.println("\nEn attente d'un client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté: " + clientSocket.getInetAddress());
                
                traiterClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur: " + e.getMessage());
        }
    }

    private void traiterClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            
            while ((message = in.readLine()) != null) {
                System.out.println("Reçu: " + message);
                ecrireJournal(message);
                
                if (message.equals("exit")) {
                    out.println("Déconnexion");
                    System.out.println("Client déconnecté");
                    break;
                } else if (message.equals("print")) {
                    out.println("Valeur de nb: " + nb);
                    System.out.println("Envoi de la valeur: " + nb);
                } else {
                    appliquerOperation(message, true);
                    out.println("Opération effectuée. nb = " + nb);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Serveur serveur = new Serveur();
        serveur.demarrer();
    }
}
