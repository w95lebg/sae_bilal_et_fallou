package universite_paris8.iut.belbaroudi.museelupin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.vue.TerrainVue;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private Terrain terrain;

    private Timeline gameLoop;

    private int temps;

    @FXML
    private Pane pane;

    @FXML
    private TilePane paneTerrain;



    public void initialize(URL url, ResourceBundle resourcebundle) {
        gameLoop = new Timeline();
        this.terrain = new Terrain();
        TerrainVue terrainVue = new TerrainVue(terrain, paneTerrain);
        terrainVue.chargerMap();
        // Entrée = case 11, ligne 4, colonne
        Ennemi p1 = new Ennemi(0, 4, "E", terrain);
        this.creerSprite(p1);
        this.terrain.ajouterPersonnage(p1);

        initAnimation();
        gameLoop.play();
    }

    private void initAnimation() {
        this.gameLoop = new Timeline();
        this.temps = 0;
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.009),
                (ev -> {
                    if (this.temps == 10000) {
                        this.gameLoop.stop();
                    } else if (this.temps % 50 == 0) {
                        terrain.unTour();
                    }
                    this.temps++;
                })
        );
        this.gameLoop.getKeyFrames().add(kf);

    }





    public void creerSprite(Ennemi p) { //TODO déplacer dans une classe EnnemiVue

        Image imageEnnemi = new Image(
                getClass().getResourceAsStream("/pictures/enemy_faussaire.png")
        );

        ImageView sprite = new ImageView(imageEnnemi);

        sprite.setFitWidth(Terrain.tileSize);
        sprite.setFitHeight(Terrain.tileSize);
        sprite.setId(p.getId());
        sprite.translateXProperty().bind(
                p.xProperty().multiply(Terrain.tileSize)
        );
        sprite.translateYProperty().bind(
                p.yProperty().multiply(Terrain.tileSize)
        );
        pane.getChildren().add(sprite);
    }
}