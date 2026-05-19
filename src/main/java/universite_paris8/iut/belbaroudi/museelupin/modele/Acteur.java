package universite_paris8.iut.belbaroudi.museelupin.modele;

import java.util.ArrayList;
import java.util.List;

public abstract class Acteur {

    // compteur statique comme dans le tp mouton
    public static int compteur = 0;

    private String id;
    private int pv;

    // position x y
    private int ligne;
    private int colonne;

    // Chemin calculé une fois : liste de positions [ligne, col]
    private List<int[]> chemin = new ArrayList<>();
    private int indexChemin = 0;

    // Vitesse : nombre de ticks avant de passer à la case suivante
    private int vitesse;
    private int compteurTick = 0;

    // référence au terrain comme tp mouton
    protected Terrain terrain;

    // constructeur
    public Acteur(int ligne, int colonne, int vitesse, int pv, Terrain terrain) {
        this.pv       = pv;
        this.ligne    = ligne;
        this.colonne  = colonne;
        this.vitesse  = vitesse;
        this.terrain  = terrain;
        this.id       = "A" + compteur;
        compteur++;
    }

    public abstract void agit();
    
    public void calculerChemin() {
        int[][] tab = terrain.getTab();
        chemin.clear();
        indexChemin = 0;

        int l = ligne, c = colonne;
        chemin.add(new int[]{l, c}); // position départ

        boolean[][] visite = new boolean[tab.length][tab[0].length]; //tabindiquantquellescasesonétaitvisités
        visite[l][c] = true;

        //droite, bas, gauche, haut
        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};

        boolean avance = true;
        while (avance) {
            avance = false;
            for (int[] d : dirs) {
                int nl = l + d[0], nc = c + d[1];
                if (nl >= 0 && nl < tab.length &&
                        nc >= 0 && nc < tab[0].length &&
                        !visite[nl][nc] &&
                        (tab[nl][nc] == 1 || tab[nl][nc] == 12)) {
                    visite[nl][nc] = true;
                    l = nl;
                    c = nc;
                    chemin.add(new int[]{l, c});
                    avance = true;
                    if (tab[nl][nc] == 12) return; // sortie atteinte
                    break;
                }
            }
        }
    }

    protected boolean seDeplace() {
        compteurTick++;
        if (compteurTick < vitesse) return false;
        compteurTick = 0;

        indexChemin++;
        if (indexChemin >= chemin.size()) {
            meurt();
            return false;
        }
        this.ligne   = chemin.get(indexChemin)[0];
        this.colonne = chemin.get(indexChemin)[1];
        return true;
    }


    public void decrementerPv(int n) { this.pv -= n; }
    public void incrementerPv(int n) { this.pv += n; }
    public boolean estVivant()       { return this.pv > 0; }
    public void meurt()              { this.pv = 0; }


    public int    getLigne()   { return ligne; }
    public int    getColonne() { return colonne; }
    public String getId()      { return id; }
    public int    getPv()      { return pv; }

    @Override
    public String toString() {
        return id + " [" + ligne + "," + colonne + "] pv=" + pv;
    }
}