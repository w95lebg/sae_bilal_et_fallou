package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Environnement {

    private Terrain           terrain;
    private ArrayList<Ennemi> personnages;
    private ArrayList<Tour>   tours;
    private Pane              pane;

    public Environnement(Terrain terrain, Pane pane) {
        this.terrain     = terrain;
        this.personnages = new ArrayList<>();
        this.tours       = new ArrayList<>();
        this.pane        = pane;
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

            // L A S E R
            if (t.estLaser()) {
                int laserPxX = t.getCaseX() * Terrain.tileSize;
                int laserPxY = t.getCaseY() * Terrain.tileSize;

                for (Ennemi e : personnages) {
                    if (e.estMort()) continue;

                    double dx = Math.abs(e.getX() - laserPxX);
                    double dy = Math.abs(e.getY() - laserPxY);

                    if (dx <= Terrain.tileSize * 2 && dy <= Terrain.tileSize * 2) {
                        if (!e.isDansPorteeLaser()) {
                            e.prendresDegats(0.25);
                            e.setDansPorteeLaser(true);

                            if (!toursASupprimer.contains(t)) {
                                toursASupprimer.add(t);
                                terrain.getTab()[t.getCaseY()][t.getCaseX()] = 3; // libère la case tour
                            }
                        }
                    } else {
                        e.setDansPorteeLaser(false);
                    }
                }
            }

            // P O R T E
            if (t.estPorte() && !t.isActivee()) {
                for (Ennemi e : personnages) {
                    if (e.estMort() || e.isBloque()) continue;

                    int ennemiCaseX = (int) e.getX() / Terrain.tileSize;
                    int ennemiCaseY = (int) e.getY() / Terrain.tileSize;

                    System.out.println("Ennemi case=(" + ennemiCaseX + "," + ennemiCaseY + ") Porte case=(" + t.getCaseX() + "," + t.getCaseY() + ")");

                    if (ennemiCaseX == t.getCaseX() && ennemiCaseY == t.getCaseY()) {
                        System.out.println(">>> BLOCAGE déclenché !");
                        t.setActivee(true);
                        e.setBloque(true);

                        Tour porteRef = t;
                        Timeline deblocage = new Timeline(
                                new KeyFrame(Duration.seconds(5), ev -> {
                                    System.out.println(">>> DEBLOCAGE après 5s");
                                    e.setBloque(false);
                                    tours.remove(porteRef);
                                    if (porteRef.getSpriteView() != null) {
                                        pane.getChildren().remove(porteRef.getSpriteView());
                                    }
                                })
                        );
                        deblocage.play();
                        break;
                    }
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