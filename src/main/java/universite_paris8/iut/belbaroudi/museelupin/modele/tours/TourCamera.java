package universite_paris8.iut.belbaroudi.museelupin.modele.tours;

import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;

import java.util.ArrayList;


public class TourCamera extends Tour {

    private static final double PORTEE              = Terrain.tileSize * 3;
    private static final double FACTEUR_RALENTISSEMENT = 0.5;

    public TourCamera(int caseX, int caseY) {
        super("Camera", "/pictures/tower_camera.png", caseX, caseY);
    }

    @Override
    public boolean agir(ArrayList<Ennemi> ennemis, Terrain terrain, Pane pane) {
        int camPxX = getCaseX() * Terrain.tileSize;
        int camPxY = getCaseY() * Terrain.tileSize;

        for (Ennemi e : ennemis) {
            if (e.estMort()) continue;

            double dx    = Math.abs(e.getX() - camPxX);
            double dy    = Math.abs(e.getY() - camPxY);
            boolean dans = dx <= PORTEE && dy <= PORTEE;

            if (dans && !e.isRalenti()) {
                // L'ennemi entre dans la zone : on le ralentit
                e.setRalenti(true);
                e.setSourceDuRalentissement(this);
                e.setFacteurVitesse(FACTEUR_RALENTISSEMENT);
            } else if (!dans && e.isRalentiParCamera(this)) {
                // L'ennemi sort de la zone de cette caméra : on restaure sa vitesse
                e.setRalenti(false);
                e.setSourceDuRalentissement(null);
                e.setFacteurVitesse(1.0);
            }
        }
        return false; // la caméra reste en place
    }
}
