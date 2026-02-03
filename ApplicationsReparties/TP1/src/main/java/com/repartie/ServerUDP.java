package src.main.java.com.repartie;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerUDP {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9876); // port d'écoute
        System.out.println("Serveur UDP en attente...");

        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        
        socket.receive(receivePacket); // attente d'un message du client
        String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Message reçu : " + clientMessage);

        String response = "Hello World";
        byte[] sendBuffer = response.getBytes();
        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();

        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
        socket.send(sendPacket); // envoi de la réponse
        System.out.println("Réponse envoyée.");

        socket.close();
    }
}
