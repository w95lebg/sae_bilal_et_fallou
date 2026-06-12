package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Environnement {

    private Terrain terrain;
    private ArrayList<Ennemi> personnages;
    private ArrayList<Tour>   tours;
    private Pane pane;

    public Environnement(Terrain terrain, Pane pane) {
        this.terrain      = terrain;
        this.personnages  = new ArrayList<>();
        this.tours        = new ArrayList<>();
        this.pane         = pane;
    }

    public Terrain getTerrain() { return terrain; }

    public void ajouterPersonnage(Ennemi p) { personnages.add(p); }

    public void ajouterTour(Tour t) { tours.add(t); }

    public void unTour(double vitesse) {
        for (Ennemi p : personnages) {
            p.avancer(vitesse);
        }

        ArrayList<Tour> toursASupprimer = new ArrayList<>();

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

                        if (!toursASupprimer.contains(t)) {
                            toursASupprimer.add(t);
                            terrain.getTab()[t.getCaseY()][t.getCaseX()] = 3;
                        }
                    }
                } else {
                    e.setDansPorteeLaser(false);
                }
            }
        }

        for (Tour t : toursASupprimer) {
            tours.remove(t);
            System.out.println("Suppression tour laser : spriteView=" + t.getSpriteView());
            if (t.getSpriteView() != null) {
                boolean removed = pane.getChildren().remove(t.getSpriteView());
                System.out.println("  -> remove() retourne : " + removed);
            }
        }
    }
}