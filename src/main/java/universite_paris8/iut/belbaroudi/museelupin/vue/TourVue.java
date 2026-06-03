package universite_paris8.iut.belbaroudi.museelupin.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;

public class TourVue {

    private Tour tour;
    private Pane pane;

    public TourVue(Tour tour, Pane pane) {
        this.tour = tour;
        this.pane = pane;
    }

    public void afficher() {
        Image image = new Image(
                getClass().getResourceAsStream(tour.getImagePath())
        );

        ImageView sprite = new ImageView(image);
        sprite.setFitWidth(Terrain.tileSize);
        sprite.setFitHeight(Terrain.tileSize);

        sprite.setTranslateX(tour.getCaseX() * Terrain.tileSize);
        sprite.setTranslateY(tour.getCaseY() * Terrain.tileSize);

        pane.getChildren().add(sprite);
    }
}