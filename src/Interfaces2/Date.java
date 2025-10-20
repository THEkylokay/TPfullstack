package Interfaces2;

public class Date implements Convertible {
    int jour;
    int mois;
    int annee;

    public Date(int j, int m, int a) {
        jour = j;
        mois = m;
        annee = a;
    }

    public void afficherDate() {
        System.out.println(jour + "/" + mois + "/" + annee);
    }

    @Override
    public int toInt() {
        // int conversion
        return annee * 10000 + mois * 100 + jour;
    }
}
