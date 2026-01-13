package Interfaces3;

class Point implements WithTranslation {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void deplacer(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void displayPosition() {
        System.out.println("Point(" + x + ", " + y + ")");
    }
}