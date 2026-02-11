package com.tp.tp8.morpions;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class JeuMorpionsImpl extends UnicastRemoteObject implements JeuMorpions {
    
    private char[][] grille;
    private int joueurActuel;
    
    public JeuMorpionsImpl() throws RemoteException {
        super();
        reinitialiser();
    }
    
    @Override
    public synchronized char[][] obtenirGrille() throws RemoteException {
        // Retourner une copie pour éviter les modifications externes
        char[][] copie = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(grille[i], 0, copie[i], 0, 3);
        }
        return copie;
    }
    
    @Override
    public synchronized boolean jouerCoup(int joueur, int ligne, int colonne) throws RemoteException {
        // Vérifications
        if (joueur != joueurActuel) {
            return false; // Pas le tour de ce joueur
        }
        
        if (ligne < 0 || ligne > 2 || colonne < 0 || colonne > 2) {
            return false; // Coordonnées invalides
        }
        
        if (grille[ligne][colonne] != ' ') {
            return false; // Case déjà occupée
        }
        
        if (verifierVictoire() != 0 || verifierMatchNul()) {
            return false; // Partie terminée
        }
        
        // Placer le symbole
        char symbole = (joueur == 1) ? 'X' : 'O';
        grille[ligne][colonne] = symbole;
        
        // Changer de joueur
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
        
        return true;
    }
    
    @Override
    public synchronized int verifierVictoire() throws RemoteException {
        // Vérifier les lignes
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] != ' ' && 
                grille[i][0] == grille[i][1] && 
                grille[i][1] == grille[i][2]) {
                return (grille[i][0] == 'X') ? 1 : 2;
            }
        }
        
        // Vérifier les colonnes
        for (int i = 0; i < 3; i++) {
            if (grille[0][i] != ' ' && 
                grille[0][i] == grille[1][i] && 
                grille[1][i] == grille[2][i]) {
                return (grille[0][i] == 'X') ? 1 : 2;
            }
        }
        
        // Vérifier la diagonale principale
        if (grille[0][0] != ' ' && 
            grille[0][0] == grille[1][1] && 
            grille[1][1] == grille[2][2]) {
            return (grille[0][0] == 'X') ? 1 : 2;
        }
        
        // Vérifier la diagonale inverse
        if (grille[0][2] != ' ' && 
            grille[0][2] == grille[1][1] && 
            grille[1][1] == grille[2][0]) {
            return (grille[0][2] == 'X') ? 1 : 2;
        }
        
        return 0; // Personne n'a gagné
    }
    
    @Override
    public synchronized boolean verifierMatchNul() throws RemoteException {
        if (verifierVictoire() != 0) {
            return false; // Il y a un gagnant
        }
        
        // Vérifier si toutes les cases sont remplies
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' ') {
                    return false; // Il reste des cases vides
                }
            }
        }
        
        return true; // Match nul
    }
    
    @Override
    public synchronized void reinitialiser() throws RemoteException {
        grille = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = ' ';
            }
        }
        joueurActuel = 1;
    }
    
    @Override
    public synchronized int obtenirJoueurActuel() throws RemoteException {
        return joueurActuel;
    }
}
