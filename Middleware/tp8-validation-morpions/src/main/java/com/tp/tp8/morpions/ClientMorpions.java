package com.tp.tp8.morpions;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.util.*;

public class ClientMorpions {
    private static final String HOST = "localhost";
    private static final int PORT = 7000;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JeuMorpions jeu;
    private int numeroJoueur;
    private Scanner scanner;

    public void demarrer() {
        try {
            // Connexion au serveur par socket
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);
            
            System.out.println("Connexion au serveur...");
            
            // Recevoir le numéro de joueur
            String msg = in.readLine();
            if (msg.startsWith("ERREUR:")) {
                System.err.println(msg.substring(7));
                return;
            }
            
            numeroJoueur = Integer.parseInt(msg.split(":")[1]);
            System.out.println("Vous êtes le joueur " + numeroJoueur + " (symbole: " + (numeroJoueur == 1 ? "X" : "O") + ")");
            
            // Recevoir l'URL RMI
            String rmiUrl = in.readLine().split(":")[1] + ":" + in.readLine();
            
            // Se connecter à l'objet RMI
            jeu = (JeuMorpions) Naming.lookup(rmiUrl);
            System.out.println("Connecté à l'objet RMI");
            
            // Attendre le début de la partie
            String start = in.readLine();
            if (start.equals("START")) {
                System.out.println("\n=== La partie commence! ===\n");
                jouer();
            }
            
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fermer();
        }
    }

    private void jouer() throws Exception {
        boolean partieEnCours = true;
        
        while (partieEnCours) {
            // Afficher la grille
            afficherGrille();
            
            // Vérifier le tour
            int joueurActuel = jeu.obtenirJoueurActuel();
            
            if (joueurActuel == numeroJoueur) {
                System.out.println("\n>>> C'est votre tour! <<<");
                
                // Demander le coup
                boolean coupValide = false;
                while (!coupValide) {
                    System.out.print("Entrez la ligne (0-2) ou 'q' pour quitter: ");
                    String input = scanner.nextLine().trim();
                    
                    if (input.equalsIgnoreCase("q")) {
                        out.println("QUIT");
                        return;
                    }
                    
                    try {
                        int ligne = Integer.parseInt(input);
                        
                        System.out.print("Entrez la colonne (0-2): ");
                        int colonne = Integer.parseInt(scanner.nextLine().trim());
                        
                        // Envoyer le coup au serveur
                        out.println("JOUER:" + ligne + ":" + colonne);
                        String reponse = in.readLine();
                        
                        if (reponse.equals("OK")) {
                            coupValide = true;
                            System.out.println("Coup joué!\n");
                            
                            // Vérifier si on a gagné
                            String resultat = in.readLine();
                            if (resultat != null && resultat.startsWith("VICTOIRE:")) {
                                afficherGrille();
                                int gagnant = Integer.parseInt(resultat.split(":")[1]);
                                if (gagnant == numeroJoueur) {
                                    System.out.println("\n*** VOUS AVEZ GAGNÉ! ***\n");
                                } else {
                                    System.out.println("\n*** VOUS AVEZ PERDU! ***\n");
                                }
                                partieEnCours = false;
                            } else if (resultat != null && resultat.equals("MATCH_NUL")) {
                                afficherGrille();
                                System.out.println("\n*** MATCH NUL! ***\n");
                                partieEnCours = false;
                            }
                        } else if (reponse.startsWith("ERREUR:")) {
                            System.out.println("Erreur: " + reponse.substring(7));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Veuillez entrer un nombre entre 0 et 2");
                    }
                }
            } else {
                System.out.println("\n>>> C'est le tour du joueur " + joueurActuel + " <<<");
                System.out.println("En attente...");
                
                // Attendre que l'autre joueur joue
                Thread.sleep(1000);
                
                // Vérifier si la partie est terminée
                int vainqueur = jeu.verifierVictoire();
                if (vainqueur != 0) {
                    afficherGrille();
                    if (vainqueur == numeroJoueur) {
                        System.out.println("\n*** VOUS AVEZ GAGNÉ! ***\n");
                    } else {
                        System.out.println("\n*** VOUS AVEZ PERDU! ***\n");
                    }
                    partieEnCours = false;
                } else if (jeu.verifierMatchNul()) {
                    afficherGrille();
                    System.out.println("\n*** MATCH NUL! ***\n");
                    partieEnCours = false;
                }
            }
        }
        
        System.out.print("\nVoulez-vous rejouer? (o/n): ");
        String reponse = scanner.nextLine().trim();
        if (reponse.equalsIgnoreCase("o")) {
            jeu.reinitialiser();
            jouer();
        } else {
            out.println("QUIT");
        }
    }

    private void afficherGrille() throws Exception {
        char[][] grille = jeu.obtenirGrille();
        
        System.out.println("\n    0   1   2");
        System.out.println("  +---+---+---+");
        
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + grille[i][j] + " |");
            }
            System.out.println();
            System.out.println("  +---+---+---+");
        }
    }

    private void fermer() {
        try {
            if (scanner != null) scanner.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            // Ignorer
        }
    }

    public static void main(String[] args) {
        ClientMorpions client = new ClientMorpions();
        client.demarrer();
    }
}
