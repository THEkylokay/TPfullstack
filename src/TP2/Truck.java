package TP2;

public class Truck extends Vehicle {
    public Truck(int modelYear, String model, float price) {
        super(modelYear, model, price);
    }
    @Override
    public void start() {
        System.out.println("Truck starts🚗🚗🚗🚗");
    }

    @Override
    public void accelerate() {
        System.out.println("Truck accelerates" + "\n");
    }
}
