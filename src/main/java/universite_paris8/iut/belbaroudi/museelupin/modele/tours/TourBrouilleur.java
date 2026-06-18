package universite_paris8.iut.belbaroudi.museelupin.modele.tours;

import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;

import java.util.ArrayList;


public class TourBrouilleur extends Tour {

    private static final double PORTEE      = Terrain.tileSize * 2;
    private static final double DEGATS_TICK = 0.005;

    public TourBrouilleur(int caseX, int caseY) {
        super("Brouilleur", "/pictures/tower_brouilleur.png", caseX, caseY);
    }

    @Override
    public boolean agir(ArrayList<Ennemi> ennemis, Terrain terrain, Pane pane) {
        int bPxX = getCaseX() * Terrain.tileSize;
        int bPxY = getCaseY() * Terrain.tileSize;

        for (Ennemi e : ennemis) {
            if (e.estMort()) continue;

            double dx = Math.abs(e.getX() - bPxX);
            double dy = Math.abs(e.getY() - bPxY);

            if (dx <= PORTEE && dy <= PORTEE) {
                e.prendresDegats(DEGATS_TICK);
            }
        }
        return false; // reste en place
    }
}
