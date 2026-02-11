package com.tp.tp8.morpions;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServeurMorpions {
    private static final int PORT_SOCKET = 7000;
    private static final String RMI_URL = "rmi://localhost/JeuMorpions";
    private static JeuMorpions jeu;
    private static int nbJoueursConnectes = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        try {
            // Démarrer le registre RMI
            try {
                LocateRegistry.createRegistry(1099);
                System.out.println("Registre RMI créé sur le port 1099");
            } catch (Exception e) {
                System.out.println("Registre RMI déjà existant");
            }
            
            // Créer et enregistrer l'objet distant
            jeu = new JeuMorpionsImpl();
            Naming.rebind(RMI_URL, jeu);
            System.out.println("Objet RMI enregistré: " + RMI_URL);
            
            // Démarrer le serveur de sockets
            ServerSocket serverSocket = new ServerSocket(PORT_SOCKET);
            System.out.println("Serveur de morpions démarré sur le port " + PORT_SOCKET);
            System.out.println("En attente de 2 joueurs...\n");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                synchronized (lock) {
                    if (nbJoueursConnectes < 2) {
                        nbJoueursConnectes++;
                        int numeroJoueur = nbJoueursConnectes;
                        
                        System.out.println("Joueur " + numeroJoueur + " connecté");
                        
                        Thread thread = new Thread(new GestionnaireJoueur(clientSocket, numeroJoueur, jeu));
                        thread.start();
                        
                        if (nbJoueursConnectes == 2) {
                            System.out.println("\nLes 2 joueurs sont connectés. La partie commence!\n");
                        }
                    } else {
                        // Refuser la connexion
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("ERREUR:La partie est complète (2 joueurs maximum)");
                        clientSocket.close();
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erreur serveur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static class GestionnaireJoueur implements Runnable {
        private Socket socket;
        private int numeroJoueur;
        private JeuMorpions jeu;
        
        public GestionnaireJoueur(Socket socket, int numeroJoueur, JeuMorpions jeu) {
            this.socket = socket;
            this.numeroJoueur = numeroJoueur;
            this.jeu = jeu;
        }
        
        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                // Envoyer le numéro du joueur
                out.println("JOUEUR:" + numeroJoueur);
                out.println("RMI_URL:" + RMI_URL);
                
                // Attendre que les 2 joueurs soient connectés
                while (nbJoueursConnectes < 2) {
                    Thread.sleep(100);
                }
                
                out.println("START");
                
                String commande;
                while ((commande = in.readLine()) != null) {
                    System.out.println("Joueur " + numeroJoueur + " > " + commande);
                    
                    if (commande.equals("QUIT")) {
                        out.println("Au revoir!");
                        break;
                    } else if (commande.equals("GRILLE")) {
                        envoyerGrille(out);
                    } else if (commande.startsWith("JOUER:")) {
                        String[] parts = commande.split(":");
                        if (parts.length == 3) {
                            try {
                                int ligne = Integer.parseInt(parts[1]);
                                int colonne = Integer.parseInt(parts[2]);
                                
                                boolean succes = jeu.jouerCoup(numeroJoueur, ligne, colonne);
                                
                                if (succes) {
                                    out.println("OK");
                                    System.out.println("Joueur " + numeroJoueur + " a joué en (" + ligne + "," + colonne + ")");
                                    
                                    // Vérifier victoire ou match nul
                                    int gagnant = jeu.verifierVictoire();
                                    if (gagnant != 0) {
                                        out.println("VICTOIRE:" + gagnant);
                                        System.out.println("Victoire du joueur " + gagnant + "!");
                                    } else if (jeu.verifierMatchNul()) {
                                        out.println("MATCH_NUL");
                                        System.out.println("Match nul!");
                                    }
                                } else {
                                    out.println("ERREUR:Coup invalide");
                                }
                            } catch (NumberFormatException e) {
                                out.println("ERREUR:Format invalide");
                            }
                        }
                    } else if (commande.equals("TOUR")) {
                        out.println("TOUR:" + jeu.obtenirJoueurActuel());
                    }
                }
                
                synchronized (lock) {
                    nbJoueursConnectes--;
                    System.out.println("Joueur " + numeroJoueur + " déconnecté");
                    
                    if (nbJoueursConnectes == 0) {
                        // Réinitialiser la partie
                        jeu.reinitialiser();
                        System.out.println("Partie réinitialisée\n");
                    }
                }
                
            } catch (Exception e) {
                System.err.println("Erreur joueur " + numeroJoueur + ": " + e.getMessage());
            }
        }
        
        private void envoyerGrille(PrintWriter out) throws Exception {
            char[][] grille = jeu.obtenirGrille();
            StringBuilder sb = new StringBuilder("GRILLE:");
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(grille[i][j]);
                }
            }
            
            out.println(sb.toString());
        }
    }
}
