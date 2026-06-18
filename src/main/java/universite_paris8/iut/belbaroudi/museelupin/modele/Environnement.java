package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.scene.layout.Pane;

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
        // Déplacement des ennemis
        for (Ennemi p : personnages) {
            p.avancer(vitesse);
        }

        ArrayList<Tour> toursASupprimer = new ArrayList<>();
        for (Tour t : tours) {
            boolean doitEtreSupprimer = t.agir(personnages, terrain, pane);
            if (doitEtreSupprimer) {
                toursASupprimer.add(t);
            }
        }

        // Suppression des tours qui ont fini leur rôle
        for (Tour t : toursASupprimer) {
            tours.remove(t);
            if (t.getSpriteView() != null) {
                pane.getChildren().remove(t.getSpriteView());
            }
        }
    }
}
