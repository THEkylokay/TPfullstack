package TP3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building("acsit", 100));
        buildings.add(new Building("acsit", 5, "edmont michelet"));

        for  (Building building : buildings) {
            building.getInfo();
            System.out.println();
        }

        List<House> houses = new ArrayList<>();
        houses.add(new House("bahuet", 100));
        houses.add(new House("bahuet", 5, "edmont michelet", 3));

        for  (House house : houses) {
            house.getInfo();
            System.out.println();
        }
    }
}