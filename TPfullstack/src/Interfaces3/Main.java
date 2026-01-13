package Interfaces3;

public class Main {
    public static void main(String[] args) {
        // Création de 4 objets mobiles
        MobileCoin[] pieces = new MobileCoin[4];
        pieces[0] = new MobileCoin(new Point(0, 0), 1, 2);
        pieces[1] = new MobileCoin(new Circle(5, 5, 3), -1, 0);
        pieces[2] = new MobileCoin(new Point(10, 10), 0, -2);
        pieces[3] = new MobileCoin(new Circle(2, 8, 1), 0.5, 0.5);

        // display and translate after 3 tops
        for (int top = 1; top <= 3; top++) {
            System.out.println("clock's top " + top + ":");
            for (MobileCoin pm : pieces) {
                pm.topClock();      // move
                pm.display();        // display new pos
            }
            System.out.println();
        }
    }
}
