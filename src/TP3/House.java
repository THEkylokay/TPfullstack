package TP3;

public class House extends Building{
    int roomNb;

    public House(String name, int height){
        super(name, height);
    }

    public House(String name, int height, String address, int roomNb) {
        super(name, height, address);
        this.roomNb = roomNb;
    }

    public void getInfo() {
        super.getInfo();
        String infos = "";
        if (roomNb > 0) {
            infos += "Room number: " + roomNb;
            System.out.println(infos);
        }
    }
}