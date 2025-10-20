package TP1;

public class Personne {
    private String nom;
    private String prenom;
    private int dateNaissance;

    public Personne(String nom, String prenom, int dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public void getInfo() {
        String infos = "\n" + "nom : " + nom + "\n" + "prénom : " + prenom + "\n" + "date naissance : " + dateNaissance;
        System.out.println(infos);
    }
}
