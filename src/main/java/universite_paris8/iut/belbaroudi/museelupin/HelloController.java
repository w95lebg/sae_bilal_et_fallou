package universite_paris8.iut.belbaroudi.museelupin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import universite_paris8.iut.belbaroudi.museelupin.modele.Environnement;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.vue.EnnemiVue;
import universite_paris8.iut.belbaroudi.museelupin.vue.TerrainVue;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    // (1 frame = 16ms ~60fps)
    // tileSize=32px vitesse=2 l'ennemi parcourt une case en 16 frames ≈ 0.27s
    private static final double VITESSE = 2.0;

    private Terrain terrain;
    private Environnement environnement;
    private Timeline gameLoop;

    @FXML private Pane pane;
    @FXML private TilePane paneTerrain;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        this.terrain = new Terrain();
        this.environnement = new Environnement(terrain);

        TerrainVue terrainVue = new TerrainVue(terrain, paneTerrain);
        terrainVue.chargerMap();

        // Entrée = case 11, ligne 4, colonne 0
        Ennemi p1 = new Ennemi(0, 4, "E", terrain);

        p1.move();

        EnnemiVue ennemiVue = new EnnemiVue(p1, pane);
        ennemiVue.creerSprite();
        this.environnement.ajouterPersonnage(p1);

        initAnimation();
        gameLoop.play();
    }

    private void initAnimation() {
        this.gameLoop = new Timeline();
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);


        KeyFrame kf = new KeyFrame(
                Duration.millis(16),
                ev -> environnement.unTour(VITESSE)
        );
        this.gameLoop.getKeyFrames().add(kf);
    }
}