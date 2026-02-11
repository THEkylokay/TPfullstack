package fr.insa.acid;

public class DemarrerSysteme {
    public static void main(String[] args) {
        // 1. Lancer les 4 serveurs de calcul
        new Thread(new OperationServer(6001, "+")).start();
        new Thread(new OperationServer(6002, "-")).start();
        new Thread(new OperationServer(6003, "*")).start();
        new Thread(new OperationServer(6004, "/")).start();

        // 2. Lancer le Middleware
        System.out.println("Lancement du Middleware...");
        try {
            Middleware.main(null); // Appelle la méthode main du Middleware
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}