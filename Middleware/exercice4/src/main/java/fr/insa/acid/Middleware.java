package fr.insa.acid;

import java.io.*;
import java.net.*;

public class Middleware {
    private static final int PORT_TCP = 6789;
    private static final int PORT_UDP = 6790;
    private static final InetAddress UDP_SERVER_ADDR;

    static {
        try {
            UDP_SERVER_ADDR = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT_TCP)) {
            System.out.println("Middleware démarré – écoute TCP sur port " + PORT_TCP);
            System.out.println("               – dialogue UDP avec serveur sur port " + PORT_UDP);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion client : " + clientSocket.getInetAddress());

                // On traite chaque client dans un thread séparé
                new Thread(() -> gererClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gererClient(Socket clientSocket) {
        try (
                clientSocket;                                   // auto-close
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true)
        ) {
            String ligne;
            while ((ligne = in.readLine()) != null) {
                if (ligne.trim().isEmpty()) continue;

                System.out.println("Reçu du client : " + ligne);

                // Envoi au serveur UDP
                try (DatagramSocket udpSocket = new DatagramSocket()) {
                    byte[] data = ligne.getBytes();
                    DatagramPacket packet = new DatagramPacket(
                            data, data.length, UDP_SERVER_ADDR, PORT_UDP);
                    udpSocket.send(packet);

                    // Réception réponse UDP (timeout 2s)
                    byte[] buffer = new byte[32];
                    DatagramPacket reponsePacket = new DatagramPacket(buffer, buffer.length);
                    udpSocket.setSoTimeout(2000);
                    udpSocket.receive(reponsePacket);

                    String reponse = new String(reponsePacket.getData(), 0, reponsePacket.getLength()).trim();

                    // Renvoi au client TCP
                    out.println(reponse);
                    out.flush();

                    System.out.println("Réponse renvoyée au client : " + reponse);
                } catch (SocketTimeoutException e) {
                    out.println("ERREUR: timeout serveur UDP");
                } catch (IOException e) {
                    out.println("ERREUR: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Client déconnecté : " + e.getMessage());
        }
    }
}