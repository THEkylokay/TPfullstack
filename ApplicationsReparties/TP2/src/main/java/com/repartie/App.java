package TP2.src.main.java.com.repartie;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage : java App server|client");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "server":
                ServerBar.main(args);
                break;
            case "client":
                ClientBar.main(args);
                break;
            default:
                System.out.println("Argument invalide : utilisez 'server' ou 'client'");
        }
    }
}
