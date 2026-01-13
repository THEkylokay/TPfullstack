package Interfaces3;

public class MobileCoin {
    private WithTranslation object;
    private double dx;
    private double dy;

    public MobileCoin(WithTranslation object, double dx, double dy) {
        this.object = object;
        this.dx = dx;
        this.dy = dy;
    }

    public void topClock() {
        object.deplacer(dx, dy);
    }

    public void display() {
        object.displayPosition();
    }
}
