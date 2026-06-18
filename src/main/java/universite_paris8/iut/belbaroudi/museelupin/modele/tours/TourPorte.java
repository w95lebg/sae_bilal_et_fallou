package universite_paris8.iut.belbaroudi.museelupin.modele.tours;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;

import java.util.ArrayList;


public class TourPorte extends Tour {

    private static final int DUREE_BLOCAGE_SECONDES = 5;

    public TourPorte(int caseX, int caseY) {
        super("Porte", "/pictures/tower_porte.png", caseX, caseY);
    }

    @Override
    public boolean agir(ArrayList<Ennemi> ennemis, Terrain terrain, Pane pane) {
        if (isActivee()) return false;

        for (Ennemi e : ennemis) {
            if (e.estMort() || e.isBloque()) continue;

            int ennemiCaseX = (int) e.getX() / Terrain.tileSize;
            int ennemiCaseY = (int) e.getY() / Terrain.tileSize;

            if (ennemiCaseX == getCaseX() && ennemiCaseY == getCaseY()) {
                setActivee(true);
                e.setBloque(true);

                Tour porteRef = this;
                Timeline deblocage = new Timeline(
                    new KeyFrame(Duration.seconds(DUREE_BLOCAGE_SECONDES), ev -> {
                        e.setBloque(false);
                        if (porteRef.getSpriteView() != null) {
                            pane.getChildren().remove(porteRef.getSpriteView());
                        }
                    })
                );
                deblocage.play();
                break;
            }
        }
        return false; // la suppression est gérée par la Timeline interne
    }
}
