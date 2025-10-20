package TP2;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        List<Car> Cars = new ArrayList<>();
        Cars.add(new Car(2000, "toyota", 1000.00f));
        Cars.add(new Car(2001, "peugeot", 1000000.10f));

        for (Car car : Cars) {
            car.getInfo();
            car.start();
            car.accelerate();
        }

        List<Truck> Trucks = new ArrayList<>();
        Trucks.add(new Truck(2000, "mercedes", 1000.00f));
        Trucks.add(new Truck(2001, "chevrolet", 1000000.10f));

        for (Truck truck : Trucks) {
            truck.getInfo();
            truck.start();
            truck.accelerate();
        }
    }
}
