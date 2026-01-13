package Interfaces2;

public class OutilComparaison {

    public static boolean plusGrand(Convertible a, Convertible b) {
        return a.toInt() > b.toInt();
    }

    public static boolean plusPetit(Convertible a, Convertible b) {
        return a.toInt() < b.toInt();
    }

    public static boolean egal(Convertible a, Convertible b) {
        return a.toInt() == b.toInt();
    }

    public static void trier(Convertible[] tab) {
        // bubble sorting
        for (int i = 0; i < tab.length - 1; i++) {
            for (int j = 0; j < tab.length - i - 1; j++) {
                if (tab[j].toInt() > tab[j + 1].toInt()) {
                    Convertible temp = tab[j];
                    tab[j] = tab[j + 1];
                    tab[j + 1] = temp;
                }
            }
        }
    }

    // sort accounts & dates
    public static void trierComptes(Compte[] comptes) {
        Convertible[] temp = comptes;
        trier(temp);
        for (int i = 0; i < comptes.length; i++) {
            comptes[i] = (Compte) temp[i];
        }
    }

    public static void trierDates(Date[] dates) {
        Convertible[] temp = dates;
        trier(temp);
        for (int i = 0; i < dates.length; i++) {
            dates[i] = (Date) temp[i];
        }
    }
}
