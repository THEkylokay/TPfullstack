package TP1;

public class Employe extends Personne {
    private int salaire;

    public Employe(String nom, String prenom, int dateNaissance, int salaire) {
        super(nom, prenom, dateNaissance);
        this.salaire = salaire;
    }

    @Override
    public void getInfo(){
        super.getInfo();
        String infos = "salaire : " + this.salaire;
        System.out.println(infos);
    }
}
