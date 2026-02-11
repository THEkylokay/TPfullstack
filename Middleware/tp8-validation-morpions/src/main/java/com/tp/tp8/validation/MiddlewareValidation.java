package com.tp.tp8.validation;

import java.io.*;
import java.net.*;
import java.util.*;

public class MiddlewareValidation {
    private static final int PORT_CLIENT = 5500;
    private static final String[] SERVEUR_HOSTS = {"localhost", "localhost", "localhost"};
    private static final int[] SERVEUR_PORTS = {6001, 6002, 6003};
    private static final int NB_SERVEURS = 3;

    public void demarrer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_CLIENT)) {
            System.out.println("Middleware de validation démarré sur le port " + PORT_CLIENT);
            
            while (true) {
                System.out.println("\nEn attente d'un client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté");
                
                traiterClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Erreur middleware: " + e.getMessage());
        }
    }

    private void traiterClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            
            while ((message = in.readLine()) != null) {
                System.out.println("Reçu du client: " + message);
                
                if (message.equals("exit")) {
                    out.println("Déconnexion");
                    System.out.println("Client déconnecté");
                    break;
                } else if (message.equals("print")) {
                    afficherValeurs(out);
                } else {
                    // Protocole de validation à deux phases
                    validerEtAppliquerOperation(message, out);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client: " + e.getMessage());
        }
    }

    private void afficherValeurs(PrintWriter clientOut) {
        System.out.println("\n=== Interrogation des serveurs ===");
        for (int i = 0; i < NB_SERVEURS; i++) {
            try (
                Socket socket = new Socket(SERVEUR_HOSTS[i], SERVEUR_PORTS[i]);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                out.println("print");
                String reponse = in.readLine();
                clientOut.println("Serveur " + (i + 1) + ": " + reponse);
                System.out.println("Serveur " + (i + 1) + ": " + reponse);
            } catch (IOException e) {
                clientOut.println("Serveur " + (i + 1) + ": ERREUR - " + e.getMessage());
            }
        }
    }

    private void validerEtAppliquerOperation(String operation, PrintWriter clientOut) {
        System.out.println("\n=== PHASE 1: PREPARE ===");
        
        // Phase 1: Demander à tous les serveurs s'ils sont prêts
        boolean[] preparationOK = new boolean[NB_SERVEURS];
        Socket[] sockets = new Socket[NB_SERVEURS];
        BufferedReader[] readers = new BufferedReader[NB_SERVEURS];
        PrintWriter[] writers = new PrintWriter[NB_SERVEURS];
        
        try {
            for (int i = 0; i < NB_SERVEURS; i++) {
                sockets[i] = new Socket(SERVEUR_HOSTS[i], SERVEUR_PORTS[i]);
                readers[i] = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));
                writers[i] = new PrintWriter(sockets[i].getOutputStream(), true);
                
                writers[i].println("PREPARE:" + operation);
                String reponse = readers[i].readLine();
                
                preparationOK[i] = "YES".equals(reponse);
                System.out.println("Serveur " + (i + 1) + " PREPARE: " + reponse);
                
                if (!preparationOK[i]) {
                    clientOut.println("ERREUR: Le serveur " + (i + 1) + " n'est pas disponible");
                    System.out.println("Opération annulée: serveur " + (i + 1) + " non prêt");
                    fermerConnexions(sockets, readers, writers);
                    return;
                }
            }
            
            // Tous les serveurs sont prêts
            System.out.println("Tous les serveurs sont prêts");
            System.out.println("\n=== PHASE 2: COMMIT ===");
            
            // Phase 2: Appliquer la modification
            boolean toutCommit = true;
            int serveurEchec = -1;
            
            for (int i = 0; i < NB_SERVEURS; i++) {
                writers[i].println("COMMIT:" + operation);
                String reponse = readers[i].readLine();
                
                System.out.println("Serveur " + (i + 1) + " COMMIT: " + reponse);
                
                if (!"COMMITTED".equals(reponse)) {
                    toutCommit = false;
                    serveurEchec = i + 1;
                    break;
                }
            }
            
            if (!toutCommit) {
                // Rollback sur tous les serveurs
                System.out.println("\n=== ROLLBACK ===");
                clientOut.println("ERREUR: Le serveur " + serveurEchec + " n'a pas pu valider l'opération. Rollback effectué.");
                System.out.println("Rollback sur tous les serveurs");
                
                for (int i = 0; i < NB_SERVEURS; i++) {
                    try {
                        writers[i].println("ROLLBACK");
                        readers[i].readLine();
                    } catch (IOException e) {
                        System.err.println("Erreur rollback serveur " + (i + 1));
                    }
                }
            } else {
                clientOut.println("OK: Opération validée et appliquée sur tous les serveurs");
                System.out.println("Opération validée avec succès sur tous les serveurs");
            }
            
            fermerConnexions(sockets, readers, writers);
            
        } catch (IOException e) {
            clientOut.println("ERREUR: Problème de communication - " + e.getMessage());
            System.err.println("Erreur communication: " + e.getMessage());
            fermerConnexions(sockets, readers, writers);
        }
    }

    private void fermerConnexions(Socket[] sockets, BufferedReader[] readers, PrintWriter[] writers) {
        for (int i = 0; i < NB_SERVEURS; i++) {
            try {
                if (writers[i] != null) writers[i].close();
                if (readers[i] != null) readers[i].close();
                if (sockets[i] != null) sockets[i].close();
            } catch (IOException e) {
                // Ignorer les erreurs de fermeture
            }
        }
    }

    public static void main(String[] args) {
        MiddlewareValidation middleware = new MiddlewareValidation();
        middleware.demarrer();
    }
}
