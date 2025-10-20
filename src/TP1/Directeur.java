package TP1;

public class Directeur extends Chef {
    private String societe;

    public Directeur(String nom, String prenom, int dateNaissance, int salaire, String service, String societe) {
        super(nom, prenom, dateNaissance, salaire, service);
        this.societe = societe;
    }

    public void getInfo(){
        super.getInfo();
        String infos = "société : " + this.societe;
        System.out.println(infos);
    }
}
