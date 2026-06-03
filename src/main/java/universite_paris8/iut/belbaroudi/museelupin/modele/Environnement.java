package universite_paris8.iut.belbaroudi.museelupin.modele;

import java.util.ArrayList;

public class Environnement {

    private Terrain terrain;
    private ArrayList<Ennemi> personnages;
    private ArrayList<Tour>   tours;

    public Environnement(Terrain terrain) {
        this.terrain      = terrain;
        this.personnages  = new ArrayList<>();
        this.tours        = new ArrayList<>();
    }

    public Terrain getTerrain() { return terrain; }

    public void ajouterPersonnage(Ennemi p) { personnages.add(p); }

    public void ajouterTour(Tour t) { tours.add(t); }

    public void unTour(double vitesse) {
        for (Ennemi p : personnages) {
            p.avancer(vitesse);
            System.out.println(p.getVie());
        }

        for (Tour t : tours) {
            if (!t.estLaser()) continue;

            int laserPxX = t.getCaseX() * Terrain.tileSize;
            int laserPxY = t.getCaseY() * Terrain.tileSize;

            for (Ennemi e : personnages) {
                if (e.estMort()) continue;

                double dx = Math.abs(e.getX() - laserPxX);
                double dy = Math.abs(e.getY() - laserPxY);

                if (dx <= Terrain.tileSize * 2 && dy <= Terrain.tileSize * 2) {
                    if (!e.isDansPorteeLaser()) {
                        e.prendresDegats(0.5);
                        e.setDansPorteeLaser(true);
                    }
                } else {
                    e.setDansPorteeLaser(false);
                }
            }
        }
    }
}