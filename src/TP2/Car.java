package TP2;

public class Car extends Vehicle {
    public Car(int modelYear, String model, float price) {
        super(modelYear, model, price);
    }
    @Override
    public void start() {
        System.out.println("Car starts🚗🚗🚗🚗");
    }

    @Override
    public void accelerate() {
        System.out.println("Car accelerates" + "\n");
    }
}
