package fr.insa.acid;

import java.io.*;
import java.net.*;

public class Middleware {
    public static void main(String[] args) throws IOException {
        ServerSocket middlewareSocket = new ServerSocket(5000);
        System.out.println("Middleware en attente sur le port 5000...");

        while (true) {
            try (Socket clientSocket = middlewareSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String request = in.readLine();
                if ("exit".equalsIgnoreCase(request)) {
                    // Signal de fermeture aux serveurs (optionnel selon implémentation)
                    System.out.println("Arrêt du système.");
                    break;
                }

                // Analyse de l'opération
                String op = "";
                if (request.contains("+")) op = "+";
                else if (request.contains("-")) op = "-";
                else if (request.contains("*")) op = "*";
                else if (request.contains("/")) op = "/";

                String[] nums = request.split("\\" + op);
                int port = getPortForOp(op);
                
                // Communication avec le serveur spécifique
                String result = callServer(port, nums[0].trim() + ";" + nums[1].trim());
                out.println(result);
            }
        }
        middlewareSocket.close();
    }

    private static int getPortForOp(String op) {
        switch (op) {
            case "+": return 6001;
            case "-": return 6002;
            case "*": return 6003;
            case "/": return 6004;
            default: return -1;
        }
    }

    private static String callServer(int port, String data) {
        try (Socket s = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            out.println(data);
            return in.readLine();
        } catch (IOException e) { return "Erreur serveur"; }
    }
}