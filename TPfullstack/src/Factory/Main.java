package Factory;

public class Main {
    public static void main(String[] args) {
        // Using builder to create a car
        Vehicle car = new VehicleBuilder()
                .setType("car")
                .setColor("red")
                .setWheels(4)
                .setEngineType("electric")
                .build();

        System.out.println(car.getDescription());
        car.drive();

        // Using builder to create a motorcycle
        Vehicle bike = new VehicleBuilder()
                .setType("motorcycle")
                .setColor("black")
                .setWheels(2)
                .setEngineType("gasoline")
                .build();

        System.out.println(bike.getDescription());
        bike.drive();
    }
}
