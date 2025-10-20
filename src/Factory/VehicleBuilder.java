package Factory;

public class VehicleBuilder {
    private String type;
    private String color = "white";  // default
    private int wheels = 4;          // default
    private String engineType = "gasoline"; // default

    public VehicleBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public VehicleBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public VehicleBuilder setWheels(int wheels) {
        this.wheels = wheels;
        return this;
    }

    public VehicleBuilder setEngineType(String engineType) {
        this.engineType = engineType;
        return this;
    }

    public Vehicle build() {
        return VehicleFactory.createVehicle(type, color, wheels, engineType);
    }
}
