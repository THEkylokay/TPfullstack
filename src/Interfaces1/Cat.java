package Interfaces1;

public class Cat extends Animal {
    boolean hasFurr;

    public Cat(boolean hasFurr, String name) {
        super(name);
        this.hasFurr = hasFurr;
    }

    @Override
    protected void setAnimalType() {
        this.animalType = "cat";
    }

    @Override
    public void cry() {
        System.out.println("Cat cries");
    }
}
