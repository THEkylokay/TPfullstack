package src.main.java.com.repartie;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTCP {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 6789);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();
        System.out.println("Réponse du serveur : " + response);

        socket.close();
    }
}
