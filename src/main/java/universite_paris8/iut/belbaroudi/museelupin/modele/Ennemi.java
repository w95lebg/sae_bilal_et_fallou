package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Ennemi extends Acteur {

    private char derniereDirection = ' ';

    private DoubleProperty vie = new SimpleDoubleProperty(1.0);

    // État : effets des tours
    private boolean dansPorteeLaser = false;
    private boolean bloque          = false;
    private boolean ralenti         = false;
    private double  facteurVitesse  = 1.0;

    // Référence à la tour caméra qui ralentit cet ennemi (évite les conflits)
    private Tour sourceDuRalentissement = null;

    public Ennemi(int x, int y, String code, Terrain e) {
        super(x, y, code, e);
    }

    // --- Vie ---
    public double          getVie()             { return vie.get(); }
    public DoubleProperty  vieProperty()        { return vie; }
    public boolean         estMort()            { return vie.get() <= 0.0; }
    public void prendresDegats(double pct)      { vie.set(Math.max(0.0, vie.get() - pct)); }

    // --- Laser ---
    public boolean isDansPorteeLaser()           { return dansPorteeLaser; }
    public void    setDansPorteeLaser(boolean v) { dansPorteeLaser = v; }

    // --- Porte ---
    public boolean isBloque()           { return bloque; }
    public void    setBloque(boolean v) { bloque = v; }

    // --- Caméra ---
    public boolean isRalenti()             { return ralenti; }
    public void    setRalenti(boolean v)   { ralenti = v; }
    public double  getFacteurVitesse()     { return facteurVitesse; }
    public void    setFacteurVitesse(double v) { facteurVitesse = v; }

    /** Permet à la caméra de savoir si c'est bien elle qui ralentit cet ennemi. */
    public boolean isRalentiParCamera(Tour source) {
        return ralenti && sourceDuRalentissement == source;
    }
    public void setSourceDuRalentissement(Tour t) { sourceDuRalentissement = t; }

    @Override
    public void avancer(double vitesse) {
        if (bloque) return;
        super.avancer(vitesse * facteurVitesse);
    }

    @Override
    public void move() {
        int tileSize = Terrain.tileSize;
        int x        = (int) this.getX();
        int y        = (int) this.getY();
        int caseX    = x / tileSize;
        int caseY    = y / tileSize;
        int[][] tab  = this.getEnv().getTab();

        if (derniereDirection != 'B' && caseY - 1 >= 0 && tab[caseY - 1][caseX] == 1) {
            setCibleY(caseY - 1);
            derniereDirection = 'H';
        } else if (derniereDirection != 'G' && caseX + 1 < tab[0].length && tab[caseY][caseX + 1] == 1) {
            setCibleX(caseX + 1);
            derniereDirection = 'D';
        } else if (derniereDirection != 'H' && caseY + 1 < tab.length && tab[caseY + 1][caseX] == 1) {
            setCibleY(caseY + 1);
            derniereDirection = 'B';
        } else if (derniereDirection != 'D' && caseX - 1 >= 0 && tab[caseY][caseX - 1] == 1) {
            setCibleX(caseX - 1);
            derniereDirection = 'G';
        } else {
            System.out.println("Fin de parcours !");
        }
    }
}
