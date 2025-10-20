package TP1;

public class Chef extends Employe {
    private String service;

    public Chef(String nom, String prenom, int dateNaissance, int salaire, String service) {
        super(nom, prenom, dateNaissance, salaire);
        this.service = service;
    }

    public void getInfo(){
        super.getInfo();
        String infos = "service : " + this.service;
        System.out.println(infos);
    }
}
