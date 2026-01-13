package TP2022;

public class TestFactory {
    public static void main(String[] args) {
        Computer pc = ComputerFactory.getComputer(new PCFactory("16GB", "1TB", "Intel i7"));
        Computer server = ComputerFactory.getComputer(new ServerFactory("64GB", "4TB", "AMD EPYC"));

        System.out.println("PC Config: " + pc);
        System.out.println("Server Config: " + server);
    }
}
