package Factory;

public class Motorcycle implements Vehicle {
    private String color;
    private int wheels;
    private String engineType;

    // Constructor using builder
    public Motorcycle(String color, int wheels, String engineType) {
        this.color = color;
        this.wheels = wheels;
        this.engineType = engineType;
    }

    @Override
    public void drive() {
        System.out.println("Riding a motorcycle!");
    }

    @Override
    public String getDescription() {
        return "Motorcycle [Color: " + color + ", Wheels: " + wheels + ", Engine: " + engineType + "]";
    }
}
