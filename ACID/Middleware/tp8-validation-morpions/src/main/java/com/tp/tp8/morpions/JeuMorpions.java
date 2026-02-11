package com.tp.tp8.morpions;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JeuMorpions extends Remote {
    
    /**
     * Obtenir la grille de jeu
     * @return Représentation de la grille (tableau 3x3)
     */
    char[][] obtenirGrille() throws RemoteException;
    
    /**
     * Jouer un coup
     * @param joueur Le joueur (1 ou 2)
     * @param ligne Ligne (0-2)
     * @param colonne Colonne (0-2)
     * @return true si le coup est valide, false sinon
     */
    boolean jouerCoup(int joueur, int ligne, int colonne) throws RemoteException;
    
    /**
     * Vérifier si un joueur a gagné
     * @return 0 si personne n'a gagné, 1 si joueur 1 gagne, 2 si joueur 2 gagne
     */
    int verifierVictoire() throws RemoteException;
    
    /**
     * Vérifier si c'est un match nul
     * @return true si match nul, false sinon
     */
    boolean verifierMatchNul() throws RemoteException;
    
    /**
     * Réinitialiser la partie
     */
    void reinitialiser() throws RemoteException;
    
    /**
     * Obtenir le joueur dont c'est le tour
     * @return 1 ou 2
     */
    int obtenirJoueurActuel() throws RemoteException;
}
