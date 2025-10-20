package TP2;

public abstract class Vehicle {
    static int count=0;

    private int serialNb;
    private int modelYear;
    private String model;
    private float price;

    public Vehicle(int modelYear, String model, float price) {
        count++;
        this.serialNb= count;
        this.modelYear = modelYear;
        this.model = model;
        this.price = price;
    }

    public int getSerialNb() {
        return serialNb;
    }

    public int getModelYear() {
        return modelYear;
    }

    public float getPrice() {
        return price;
    }

    public String getmodel() {
        return model;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public void setmodel(String model) {
        this.model = model;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void getInfo() {
        String infos = "Serial number : " + serialNb + "\n" + "Model : " + model + "\n" + "Model year: " + modelYear + "\n" + "Price: " + price;
        System.out.println(infos);
    } // to string

    public abstract void start();
    public abstract void accelerate();
}
