package Interfaces1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Animal> cryAnimals = new ArrayList<>();
        cryAnimals.add(new Cat(true, "Félix"));
        cryAnimals.add(new Dog(true, "Médor"));

        List<Animal> nonCryAnimals = new ArrayList<>();
        nonCryAnimals.add(new Rabbit("panpan", true));
        nonCryAnimals.add(new Rabbit("pimpampoum", true));

        for  (Animal animal : cryAnimals) {
            animal.cry();
        }

        for  (Animal animal : nonCryAnimals) {
            animal.cry();
        }

    }
}
