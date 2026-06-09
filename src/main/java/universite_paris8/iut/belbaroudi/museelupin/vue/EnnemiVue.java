package universite_paris8.iut.belbaroudi.museelupin.vue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;

public class EnnemiVue {

    private Ennemi ennemi;
    private Pane   pane;
    private ProgressBar barreVie;

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
        int barHeight = 8;

        barreVie = new ProgressBar(ennemi.getVie());
        barreVie.setPrefWidth(tileSize);
        barreVie.setPrefHeight(barHeight);

        barreVie.setStyle(
                "-fx-accent: #2ecc71;" +
                        "-fx-control-inner-background: #e74c3c;" +
                        "-fx-background-insets: 0;" +
                        "-fx-padding: 0;"
        );

        barreVie.translateXProperty().bind(ennemi.xProperty());
        barreVie.translateYProperty().bind(ennemi.yProperty().subtract(barHeight + 1));

        ennemi.vieProperty().addListener((obs, oldVal, newVal) -> {
            double vie = newVal.doubleValue();
            barreVie.setProgress(vie);

            if (vie <= 0.0) {
                // del sprite et bV
                pane.getChildren().remove(sprite);
                pane.getChildren().remove(barreVie);

                // Afficher le message de mort
                afficherMessageMort();
            }
        });

        pane.getChildren().addAll(sprite, barreVie);
    }

    private void afficherMessageMort() {
        Label msg = new Label("Éliminé !");
        msg.setStyle(
                "-fx-text-fill: red;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-color: rgba(0,0,0,0.6);" +
                        "-fx-padding: 4 8 4 8;" +
                        "-fx-background-radius: 6;"
        );

        // message au mm endroit que le sprite/ennemie enft
        msg.setTranslateX(ennemi.getX() - 20);
        msg.setTranslateY(ennemi.getY() - 30);

        pane.getChildren().add(msg);

        // après 1.5 secondes
        Timeline disparition = new Timeline(
                new KeyFrame(Duration.millis(1500), e -> pane.getChildren().remove(msg))
        );
        disparition.play();
    }
}