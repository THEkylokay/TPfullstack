package Interfaces2;

public class Main {
    public static void main(String[] args) {
        // Dates
        Date d1 = new Date(5, 10, 2025);
        Date d2 = new Date(1, 1, 2024);
        Date[] dates = {d1, d2};

        // Comptes
        Compte c1 = new Compte("Alice", 101);
        c1.deposer(500);
        Compte c2 = new Compte("Bob", 102);
        c2.deposer(200);
        Compte[] comptes = {c1, c2};

        // compare
        System.out.println("d1 > d2 ? " + OutilComparaison.plusGrand(d1, d2));
        System.out.println("c1 < c2 ? " + OutilComparaison.plusPetit(c1, c2));
        System.out.println("c1 = c2 ? " + OutilComparaison.egal(c1, c2));

        // separated sort
        OutilComparaison.trierDates(dates);
        OutilComparaison.trierComptes(comptes);

        System.out.println("\nafter sorting accounts :");
        for (Compte c : comptes) {
            c.afficher();
        }

        System.out.println("\nafter sorting dates :");
        for (Date d : dates) {
            d.afficherDate();
        }
    }
}
