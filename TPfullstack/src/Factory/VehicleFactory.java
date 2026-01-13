package Factory;

public class VehicleFactory {
    public static Vehicle createVehicle(String type, String color, int wheels, String engineType) {
        switch(type.toLowerCase()) {
            case "car":
                return new Car(color, wheels, engineType);
            case "motorcycle":
                return new Motorcycle(color, wheels, engineType);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}
