package TP2.src.main.java.com.repartie;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientBar {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        String message = "Demande de boisson";
        byte[] sendBuffer = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 9876);
        socket.send(sendPacket); // envoie la requête

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivePacket); // reçoit la boisson

        String drink = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Le serveur vous sert : " + drink);

        socket.close();
    }
}
