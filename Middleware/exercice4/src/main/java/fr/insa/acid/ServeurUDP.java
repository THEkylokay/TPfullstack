package fr.insa.acid;

import java.net.*;
import java.io.*;

public class ServeurUDP {
    private static final int PORT = 6790;
    private static final String VOYELLES = "aeiouyAEIOUY";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Serveur UDP vowels-count démarré sur port " + PORT);

            byte[] buffer = new byte[256];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String phrase = new String(packet.getData(), 0, packet.getLength());
                int count = compterVoyelles(phrase);

                String reponse = String.valueOf(count);
                byte[] reponseBytes = reponse.getBytes();

                DatagramPacket reponsePacket = new DatagramPacket(
                        reponseBytes, reponseBytes.length,
                        packet.getAddress(), packet.getPort()
                );

                socket.send(reponsePacket);

                System.out.printf("Reçu: \"%s\" → %d voyelles → envoyé à %s:%d%n",
                        phrase.trim(), count, packet.getAddress(), packet.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int compterVoyelles(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (VOYELLES.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
}