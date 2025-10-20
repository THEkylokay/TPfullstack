package Interfaces2;

public class Compte implements Convertible {
    int solde;
    String titulaire;
    int numero;

    public Compte(String n, int num) {
        titulaire = n;
        numero = num;
        solde = 0;
    }

    void afficher() {
        System.out.println("Titulaire : " + titulaire + " | Numéro : " + numero + " | Solde : " + solde);
    }

    void deposer(int montant) {
        solde += montant;
    }

    void retirer(int montant) {
        solde -= montant;
    }

    @Override
    public int toInt() {
        return numero * 1000 + solde;
    }
}
