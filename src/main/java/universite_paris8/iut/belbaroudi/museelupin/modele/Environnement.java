package universite_paris8.iut.belbaroudi.museelupin.modele;

import java.util.ArrayList;

public class Environnement {

    private Terrain terrain;
    private ArrayList<Ennemi> personnages;

    public Environnement(Terrain terrain) {
        this.terrain = terrain;
        this.personnages = new ArrayList<>();
    }

    public Terrain getTerrain() { return terrain; }

    public void ajouterPersonnage(Ennemi p) {
        this.personnages.add(p);
    }

    public void unTour(double vitesse) {
        for (Ennemi p : personnages) {
            p.avancer(vitesse);
        }
    }
}