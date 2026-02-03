package src.main.java.com.repartie;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Serveur TCP en attente...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connecté : " + clientSocket.getInetAddress());

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("Hello World");

        clientSocket.close();
        serverSocket.close();
    }
}
