package Interfaces1;

public class Dog extends Animal {
    boolean hasFurr;

    public Dog(boolean hasFurr, String name) {
        super(name);
        this.hasFurr = hasFurr;
    }

    @Override
    protected void setAnimalType() {
        this.animalType = "dog";
    }

    @Override
    public void cry() {
        System.out.println("Dog cries");
    }
}
