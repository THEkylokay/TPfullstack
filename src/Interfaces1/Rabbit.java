package Interfaces1;

public class Rabbit extends Animal {
    boolean isMammal;

    public Rabbit(String name, boolean isMammal) {
        super(name);
        this.isMammal = isMammal;
    }

    @Override
    protected void setAnimalType() {
        this.animalType = "rabbit";
    }

    @Override
    public void cry() {
        throw new RuntimeException("Rabbits don't cry"); //test error trigger
    }
}
