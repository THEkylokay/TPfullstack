package fr.insa.acid;

import java.io.*;
import java.net.*;

public class OperationServer implements Runnable {
    private int port;
    private String op;

    public OperationServer(int port, String op) {
        this.port = port;
        this.op = op;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur [" + op + "] actif sur le port " + port);
            while (!Thread.currentThread().isInterrupted()) {
                try (Socket client = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

                    String input = in.readLine();
                    if ("exit".equalsIgnoreCase(input)) break;

                    String[] parts = input.split(";");
                    int a = Integer.parseInt(parts[0]);
                    int b = Integer.parseInt(parts[1]);
                    
                    int res = switch (op) {
                        case "+" -> a + b;
                        case "-" -> a - b;
                        case "*" -> a * b;
                        case "/" -> (b != 0) ? a / b : 0;
                        default -> 0;
                    };
                    out.println(res);
                }
            }
        } catch (IOException e) {
            System.out.println("Fermeture du serveur " + op);
        }
    }
}