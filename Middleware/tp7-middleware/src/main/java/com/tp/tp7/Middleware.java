package com.tp.tp7;

import java.io.*;
import java.net.*;

public class Middleware {
    private static final int PORT_CLIENT = 5001;
    private static final String SERVEUR_HOST = "localhost";
    private static final int SERVEUR_PORT = 5002;

    public void demarrer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_CLIENT)) {
            System.out.println("Middleware démarré sur le port " + PORT_CLIENT);
            
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
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            Socket serveurSocket = new Socket(SERVEUR_HOST, SERVEUR_PORT);
            BufferedReader serveurIn = new BufferedReader(new InputStreamReader(serveurSocket.getInputStream()));
            PrintWriter serveurOut = new PrintWriter(serveurSocket.getOutputStream(), true)
        ) {
            System.out.println("Connecté au serveur");
            String message;
            
            while ((message = clientIn.readLine()) != null) {
                System.out.println("Reçu du client: " + message);
                
                if (message.equals("exit")) {
                    serveurOut.println("exit");
                    serveurIn.readLine(); // Lire la réponse du serveur
                    clientOut.println("Déconnexion");
                    System.out.println("Client déconnecté");
                    break;
                } else if (message.equals("print")) {
                    // Transmettre directement
                    serveurOut.println("print");
                    String reponse = serveurIn.readLine();
                    clientOut.println(reponse);
                    System.out.println("Transmis: " + reponse);
                } else {
                    // Vérifier la conformité du mot
                    if (estMotValide(message)) {
                        serveurOut.println(message);
                        String reponse = serveurIn.readLine();
                        clientOut.println(reponse);
                        System.out.println("Mot valide transmis");
                    } else {
                        clientOut.println("ERREUR: Le mot contient des caractères non autorisés (seules les lettres sont acceptées)");
                        System.out.println("Mot rejeté: " + message);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client: " + e.getMessage());
        }
    }

    private boolean estMotValide(String mot) {
        if (mot == null || mot.isEmpty()) {
            return false;
        }
        
        // Vérifier que le mot ne contient que des lettres (majuscules ou minuscules)
        for (char c : mot.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        Middleware middleware = new Middleware();
        middleware.demarrer();
    }
}
