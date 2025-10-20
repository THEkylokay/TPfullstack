package Interfaces3;

class Circle implements WithTranslation {
    private double x, y, radius;

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void deplacer(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void displayPosition() {
        System.out.println("Circle's center(" + x + ", " + y + "), radius=" + radius);
    }
}
