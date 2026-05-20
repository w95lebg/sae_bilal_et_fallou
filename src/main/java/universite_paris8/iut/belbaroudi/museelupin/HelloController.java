package universite_paris8.iut.belbaroudi.museelupin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private Terrain env;

    private Timeline gameLoop;

    private int temps;

    @FXML
    private Pane pane;

    @FXML
    private TilePane terrain;

    private final int tileSize = 32;

    public void initialize(URL url, ResourceBundle resourcebundle) {
        gameLoop = new Timeline();
        this.env = new Terrain();
        // Entrée = case 11, ligne 4, colonne 0 dans ton tableau
        Ennemi p1 = new Ennemi(0, 4, "E", env);
        this.creerSprite(p1);
        this.env.ajouterPersonnage(p1);
        chargerMap();
        initAnimation();
        gameLoop.play();
    }

    private void initAnimation() {
        this.gameLoop = new Timeline();
        this.temps = 0;
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                (ev -> {
                    if (this.temps == 10000) {
                        this.gameLoop.stop();
                    } else if (this.temps % 50 == 0) {
                        env.unTour();
                    }
                    this.temps++;
                })
        );
        this.gameLoop.getKeyFrames().add(kf);
    }

    public void chargerMap() {
        Image mur = new Image(String.valueOf(getClass().getResource("/pictures/tile_0_mur.png")));
        Image chemin = new Image(String.valueOf(getClass().getResource("/pictures/tile_1_chemin.png")));
        Image marbre = new Image(String.valueOf(getClass().getResource("/pictures/tile_2_marbre.png")));
        Image slot = new Image(String.valueOf(getClass().getResource("/pictures/tile_3_slot.png")));
        Image obstacle = new Image(String.valueOf(getClass().getResource("/pictures/tile_4_obstacle.png")));
        Image entree = new Image(String.valueOf(getClass().getResource("/pictures/tile_11_entree.png")));
        Image sortie = new Image(String.valueOf(getClass().getResource("/pictures/tile_12_sortie.png")));

        int[][] tab = env.getTab();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                ImageView imv = null;
                if (tab[i][j] == 0) imv = setUpImage(mur);
                else if (tab[i][j] == 1) imv = setUpImage(chemin);
                else if (tab[i][j] == 2) imv = setUpImage(marbre);
                else if (tab[i][j] == 3) imv = setUpImage(slot);
                else if (tab[i][j] == 4) imv = setUpImage(obstacle);
                else if (tab[i][j] == 11) imv = setUpImage(entree);
                else if (tab[i][j] == 12) imv = setUpImage(sortie);
                System.out.println("ligne : " + i + " ; colonne : " + j);
                terrain.getChildren().add(imv);
            }
        }
    }

    public ImageView setUpImage(Image img) {
        ImageView imv = new ImageView();
        imv.setImage(img);
        imv.setFitHeight(tileSize);
        imv.setFitWidth(tileSize);
        return imv;
    }

    public void creerSprite(Ennemi p) {

        // Chargement de l'image
        Image imageEnnemi = new Image(
                getClass().getResourceAsStream("/pictures/enemy_faussaire.png")
        );

        ImageView sprite = new ImageView(imageEnnemi);

        sprite.setFitWidth(tileSize);
        sprite.setFitHeight(tileSize);
        sprite.setId(p.getId());
        sprite.translateXProperty().bind(
                p.xProperty().multiply(tileSize)
        );
        sprite.translateYProperty().bind(
                p.yProperty().multiply(tileSize)
        );
        pane.getChildren().add(sprite);
    }
}