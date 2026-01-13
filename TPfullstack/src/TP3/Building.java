package TP3;

public class Building {
    private String name;
    private int height;
    private String address;

    public Building(String name, int height) {
        this.name = name;
        this.height = height;
    }

    public Building(String name, int height, String adress) {
        this.name = name;
        this.height = height;
        this.address = adress;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public void getInfo() {
        String infos = "Name: " + name + "\n" +
                "Height: " + height;
        if (address != null) {
            infos += "\n" + "Adress: " + address;
        }
        System.out.println(infos);
    }
}