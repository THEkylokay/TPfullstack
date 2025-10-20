package TP1;

public class Main {

    public static void main(String[] args) {
        Personne personne = new Personne("dupond", "alice", 1995);
        personne.getInfo();

        Employe emp = new Employe("dupond", "alice", 1900, 1000);
        emp.getInfo();

        Chef chef = new Chef("dupond", "alice", 1900, 1000, "gestion");
        chef.getInfo();

        Directeur dir = new Directeur("dupond", "alice", 1900, 1000, "gestion", "acsit");
        dir.getInfo();

    }

}
