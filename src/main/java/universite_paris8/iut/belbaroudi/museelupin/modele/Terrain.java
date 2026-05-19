package universite_paris8.iut.belbaroudi.museelupin.modele;

import java.util.ArrayList;

public class Terrain {

    // Tableau original inchangé
    private int[][] tab = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 0, 0, 0, 3, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 0, 0, 3, 2, 2, 1, 1, 1, 1, 3, 0, 0, 0, 0, 0},
            {0, 1, 3, 0, 1, 0, 3, 2, 1, 1, 1, 0, 0, 1, 2, 3, 0, 0, 0, 0},
            {11, 1, 2, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 3, 0},
            {0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1, 2, 0},
            {0, 3, 2, 2, 3, 0, 2, 0, 3, 2, 2, 2, 3, 0, 3, 2, 0, 1, 3, 0},
            {0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 0, 1, 0, 0},
            {0, 3, 1, 0, 0, 0, 3, 0, 0, 1, 3, 1, 0, 0, 1, 3, 0, 1, 0, 0},
            {0, 0, 1, 0, 4, 0, 0, 0, 0, 1, 2, 1, 0, 4, 1, 0, 0, 1, 3, 0},
            {0, 0, 1, 3, 2, 3, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 2, 0},
            {0, 0, 1, 1, 1, 1, 1, 3, 0, 0, 3, 0, 0, 3, 1, 1, 1, 1, 3, 0},
            {0, 0, 0, 0, 0, 0, 1, 2, 0, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 3, 2, 2, 3, 0, 1, 3, 1, 1, 1, 1, 1, 1, 2, 3, 0, 0, 0, 0},
            {0, 2, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 12},
            {0, 3, 1, 3, 0, 0, 0, 0, 1, 3, 4, 3, 0, 0, 0, 0, 0, 3, 0, 0},
            {0, 0, 1, 2, 0, 3, 2, 2, 1, 1, 1, 1, 1, 2, 3, 0, 0, 2, 0, 0},
            {0, 0, 1, 1, 1, 1, 1, 1, 1, 3, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 3, 2, 2, 3, 0, 0, 0, 0, 0, 0, 3, 2, 2, 3, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    // Liste des acteurs (comme Environnement dans le TP)
    private ArrayList<Acteur> acteurs = new ArrayList<>();

    public int[][]           getTab()     { return tab; }
    public ArrayList<Acteur> getActeurs() { return acteurs; }

    public void ajouter(Acteur a) {
        acteurs.add(a);
    }

    // unTour() — copie exacte du TP
    public void unTour() {
        for (int i = 0; i < acteurs.size(); i++) {
            acteurs.get(i).agit();
        }
        for (int i = acteurs.size() - 1; i >= 0; i--) {
            if (!acteurs.get(i).estVivant()) {
                System.out.println("Suppression : " + acteurs.get(i));
                acteurs.remove(i);
            }
        }
    }

    public int[] getPositionEntree() {
        for (int r = 0; r < tab.length; r++)
            for (int c = 0; c < tab[r].length; c++)
                if (tab[r][c] == 11) return new int[]{r, c};
        return new int[]{0, 0};
    }
}