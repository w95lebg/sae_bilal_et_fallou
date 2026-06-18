package universite_paris8.iut.belbaroudi.museelupin.modele.tours;

import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;

import java.util.ArrayList;


public class TourLaser extends Tour {

    private static final double PORTEE  = Terrain.tileSize * 2;
    private static final double DEGATS  = 0.25;

    public TourLaser(int caseX, int caseY) {
        super("Laser", "/pictures/tower_laser.png", caseX, caseY);
    }

    @Override
    public boolean agir(ArrayList<Ennemi> ennemis, Terrain terrain, Pane pane) {
        int laserPxX = getCaseX() * Terrain.tileSize;
        int laserPxY = getCaseY() * Terrain.tileSize;

        for (Ennemi e : ennemis) {
            if (e.estMort()) continue;

            double dx = Math.abs(e.getX() - laserPxX);
            double dy = Math.abs(e.getY() - laserPxY);

            if (dx <= PORTEE && dy <= PORTEE) {
                if (!e.isDansPorteeLaser()) {
                    e.prendresDegats(DEGATS);
                    e.setDansPorteeLaser(true);
                    terrain.getTab()[getCaseY()][getCaseX()] = 3; // libère la case
                    return true; // se supprime
                }
            } else {
                e.setDansPorteeLaser(false);
            }
        }
        return false;
    }
}
