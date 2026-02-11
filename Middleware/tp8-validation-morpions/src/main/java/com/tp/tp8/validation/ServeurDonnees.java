package com.tp.tp8.validation;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurDonnees {
    private final int id;
    private final int port;
    private int nb;
    private final String journalFile;
    private final Random random;

    public ServeurDonnees(int id) {
        this.id = id;
        this.port = 6000 + id;
        this.nb = 1;
        this.journalFile = "journal_serveur_" + id + ".txt";
        this.random = new Random();
        chargerJournal();
    }

    private void chargerJournal() {
        File file = new File(journalFile);
        if (!file.exists()) {
            System.out.println("[Serveur " + id + "] Aucun journal trouvé. nb initialisé à 1");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String ligne;
            nb = 1;
            System.out.println("[Serveur " + id + "] Chargement du journal...");
            
            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();
                if (!ligne.isEmpty() && !ligne.startsWith("PREPARE") && !ligne.startsWith("COMMIT") && !ligne.startsWith("ABORT")) {
                    appliquerOperation(ligne);
                }
            }
            
            System.out.println("[Serveur " + id + "] Journal chargé. nb = " + nb);
        } catch (IOException e) {
            System.err.println("[Serveur " + id + "] Erreur chargement journal: " + e.getMessage());
        }
    }

    private void appliquerOperation(String operation) {
        try {
            char op = operation.charAt(0);
            int valeur = Integer.parseInt(operation.substring(1));
            
            switch (op) {
                case '+': nb += valeur; break;
                case '-': nb -= valeur; break;
                case '*': nb *= valeur; break;
                case '/': if (valeur != 0) nb /= valeur; break;
            }
        } catch (Exception e) {
            System.err.println("[Serveur " + id + "] Erreur opération: " + e.getMessage());
        }
    }

    private void ecrireJournal(String message) {
        try (FileWriter writer = new FileWriter(journalFile, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("[Serveur " + id + "] Erreur écriture journal: " + e.getMessage());
        }
    }

    public void demarrer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[Serveur " + id + "] Démarré sur le port " + port);
            System.out.println("[Serveur " + id + "] Valeur de nb: " + nb);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                traiterClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("[Serveur " + id + "] Erreur: " + e.getMessage());
        }
    }

    private void traiterClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message = in.readLine();
            System.out.println("[Serveur " + id + "] Reçu: " + message);
            
            if (message.startsWith("PREPARE:")) {
                // Phase 1: Prêt à modifier?
                boolean pret = random.nextDouble() < 0.9; // 90% de succès
                ecrireJournal("PREPARE:" + message.substring(8));
                
                if (pret) {
                    out.println("YES");
                    System.out.println("[Serveur " + id + "] Répondu: YES");
                } else {
                    out.println("NO");
                    System.out.println("[Serveur " + id + "] Répondu: NO (non disponible)");
                }
            } else if (message.startsWith("COMMIT:")) {
                // Phase 2: Appliquer la modification
                String operation = message.substring(7);
                boolean accepte = random.nextDouble() < 0.95; // 95% de succès
                
                if (accepte) {
                    appliquerOperation(operation);
                    ecrireJournal("COMMIT:" + operation);
                    ecrireJournal(operation);
                    out.println("COMMITTED");
                    System.out.println("[Serveur " + id + "] Modification appliquée. nb = " + nb);
                } else {
                    ecrireJournal("ABORT:" + operation);
                    out.println("ABORT");
                    System.out.println("[Serveur " + id + "] Modification refusée (erreur d'écriture)");
                }
            } else if (message.equals("ROLLBACK")) {
                ecrireJournal("ABORT");
                out.println("ROLLED_BACK");
                System.out.println("[Serveur " + id + "] Rollback effectué");
            } else if (message.equals("print")) {
                out.println("nb=" + nb);
                System.out.println("[Serveur " + id + "] Valeur envoyée: " + nb);
            }
        } catch (IOException e) {
            System.err.println("[Serveur " + id + "] Erreur client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ServeurDonnees <id>");
            System.exit(1);
        }
        
        int id = Integer.parseInt(args[0]);
        ServeurDonnees serveur = new ServeurDonnees(id);
        serveur.demarrer();
    }
}
