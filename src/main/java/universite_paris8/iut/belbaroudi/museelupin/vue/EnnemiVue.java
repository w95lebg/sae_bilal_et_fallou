package universite_paris8.iut.belbaroudi.museelupin.vue;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;

public class EnnemiVue {

    private Ennemi ennemi;
    private Pane   pane;

    public EnnemiVue(Ennemi ennemi, Pane pane) {
        this.ennemi = ennemi;
        this.pane   = pane;
    }

    public void creerSprite() {
        int tileSize = Terrain.tileSize;

        // Sprite
        Image imageEnnemi = new Image(
                getClass().getResourceAsStream("/pictures/enemy_faussaire.png")
        );
        ImageView sprite = new ImageView(imageEnnemi);
        sprite.setFitWidth(tileSize);
        sprite.setFitHeight(tileSize);
        sprite.setId(ennemi.getId());

        sprite.translateXProperty().bind(ennemi.xProperty());
        sprite.translateYProperty().bind(ennemi.yProperty());

        // Barre de vie
        int barHeight = 4;

        ProgressBar barreVie = new ProgressBar(ennemi.getVie());
        barreVie.setPrefWidth(tileSize);
        barreVie.setPrefHeight(barHeight);

        //
        barreVie.setStyle(
            "-fx-accent: #2ecc71;" +
            "-fx-control-inner-background: #e74c3c;" +
            "-fx-background-insets: 0;" +
            "-fx-padding: 0;"
        );


        barreVie.translateXProperty().bind(ennemi.xProperty());
        barreVie.translateYProperty().bind(ennemi.yProperty().subtract(barHeight + 1));


        ennemi.vieProperty().addListener((obs, oldVal, newVal) -> {
            barreVie.setProgress(newVal.doubleValue());
        });

        pane.getChildren().addAll(sprite, barreVie);
    }
}
