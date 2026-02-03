package src.main.java.com.repartie;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        // --- UDP ---
        Thread udpServerThread = new Thread(() -> {
            try {
                ServerUDP.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        udpServerThread.start();

        // On attend un peu pour que le serveur démarre
        Thread.sleep(500);

        System.out.println("=== Test UDP ===");
        ClientUDP.main(null);

        // On attend que le serveur termine
        udpServerThread.join();

        // --- TCP ---
        Thread tcpServerThread = new Thread(() -> {
            try {
                ServerTCP.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tcpServerThread.start();

        Thread.sleep(500);

        System.out.println("=== Test TCP ===");
        ClientTCP.main(null);

        tcpServerThread.join();
    }
}
