package Factory;

public class Car implements Vehicle {
    private String color;
    private int wheels;
    private String engineType;

    // Constructor using builder
    public Car(String color, int wheels, String engineType) {
        this.color = color;
        this.wheels = wheels;
        this.engineType = engineType;
    }

    @Override
    public void drive() {
        System.out.println("Driving a car!");
    }

    @Override
    public String getDescription() {
        return "Car [Color: " + color + ", Wheels: " + wheels + ", Engine: " + engineType + "]";
    }
}
