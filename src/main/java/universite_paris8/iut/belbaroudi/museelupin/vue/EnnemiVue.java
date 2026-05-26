package universite_paris8.iut.belbaroudi.museelupin.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;

public class EnnemiVue {

    private Ennemi ennemi;
    private Pane pane;

    public EnnemiVue(Ennemi ennemi, Pane pane) {
        this.ennemi = ennemi;
        this.pane = pane;
    }

    public void creerSprite() {
        Image imageEnnemi = new Image(
                getClass().getResourceAsStream("/pictures/enemy_faussaire.png")
        );

        ImageView sprite = new ImageView(imageEnnemi);
        sprite.setFitWidth(Terrain.tileSize);
        sprite.setFitHeight(Terrain.tileSize);
        sprite.setId(ennemi.getId());

        sprite.translateXProperty().bind(ennemi.xProperty());
        sprite.translateYProperty().bind(ennemi.yProperty());

        pane.getChildren().add(sprite);
    }
}