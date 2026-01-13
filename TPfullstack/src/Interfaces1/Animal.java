package Interfaces1;

public class Animal implements Cry{
    String name;
    protected String animalType;

    public Animal(String name) {
        this.name = name;
        setAnimalType();
    }

    protected void setAnimalType() {
        this.animalType = "";
    }

    public void getInfo() {
        String info = "Animal name:" + name + "\n" + "animalType:" + animalType + "\n";
        System.out.println(info);
    }

    @Override //from Cry interface
    public void cry() {
        System.out.println("Cry");
    }
}
