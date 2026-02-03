package TP2.src.main.java.com.repartie;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class ServerBar {
    private static final String[] DRINKS = {"sirop", "vodka", "tonic", "cidre", "bière", "punch", "porto"};

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9876);
        System.out.println("Serveur du bar en attente de clients...");

        byte[] receiveBuffer = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket); // reçoit la requête du client

            String drink = generateRandomDrink();
            byte[] sendBuffer = drink.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer, 
                    sendBuffer.length, 
                    receivePacket.getAddress(), 
                    receivePacket.getPort()
            );

            socket.send(sendPacket); // renvoie la boisson au client
        }
    }

    private static String generateRandomDrink() {
        Random rand = new Random();
        return DRINKS[rand.nextInt(DRINKS.length)];
    }
}
