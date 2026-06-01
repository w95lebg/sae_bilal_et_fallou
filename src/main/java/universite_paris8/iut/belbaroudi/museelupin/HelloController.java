package universite_paris8.iut.belbaroudi.museelupin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Environnement;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;
import universite_paris8.iut.belbaroudi.museelupin.vue.EnnemiVue;
import universite_paris8.iut.belbaroudi.museelupin.vue.TerrainVue;
import universite_paris8.iut.belbaroudi.museelupin.vue.TourVue;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private static final double VITESSE = 2.0;

    // Noms et chemins des 5 tours disponibles
    private static final String[][] TOURS_DISPONIBLES = {
            { "Camera",     "/pictures/tower_camera.png"     },
            { "Brouilleur", "/pictures/tower_brouilleur.png" },
            { "Gardien",    "/pictures/tower_gardien.png"    },
            { "Laser",      "/pictures/tower_laser.png"      },
            { "Porte",      "/pictures/tower_porte.png"      }
    };

    private Terrain terrain;
    private Environnement environnement;
    private Timeline gameLoop;

    // La tour sélectionnée dans le panneau (null = aucune)
    private String tourSelectionnee = null;

    @FXML private Pane pane;
    @FXML private TilePane paneTerrain;
    @FXML private VBox panneauTours;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        this.terrain      = new Terrain();
        this.environnement = new Environnement(terrain);

        TerrainVue terrainVue = new TerrainVue(terrain, paneTerrain);
        terrainVue.chargerMap();

        Ennemi p1 = new Ennemi(0, 4, "E", terrain);
        EnnemiVue ennemiVue = new EnnemiVue(p1, pane);
        ennemiVue.creerSprite();
        this.environnement.ajouterPersonnage(p1);

        remplirPanneauTours();
        gererClicSurTerrain();

        initAnimation();
        gameLoop.play();
    }

    // Remplit le panneau de droite avec les boutons de tours
    private void remplirPanneauTours() {
        for (String[] tourInfo : TOURS_DISPONIBLES) {
            String nom       = tourInfo[0];
            String imagePath = tourInfo[1];

            VBox bouton = new VBox(4);
            bouton.setStyle(
                    "-fx-background-color: #3c3f41; -fx-padding: 6; " +
                            "-fx-border-color: #555; -fx-border-width: 1; -fx-cursor: hand;"
            );
            bouton.setPrefWidth(120);

            Image img = new Image(getClass().getResourceAsStream(imagePath));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(48);
            iv.setFitHeight(48);
            iv.setPreserveRatio(true);

            Label label = new Label(nom);
            label.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

            bouton.getChildren().addAll(iv, label);

            // Clic sur le bouton → sélectionner cette tour
            bouton.setOnMouseClicked(e -> {
                tourSelectionnee = imagePath;
                // Mettre en surbrillance le bouton sélectionné
                for (javafx.scene.Node node : panneauTours.getChildren()) {
                    node.setStyle(
                            "-fx-background-color: #3c3f41; -fx-padding: 6; " +
                                    "-fx-border-color: #555; -fx-border-width: 1; -fx-cursor: hand;"
                    );
                }
                bouton.setStyle(
                        "-fx-background-color: #4a6fa5; -fx-padding: 6; " +
                                "-fx-border-color: #aad4f5; -fx-border-width: 2; -fx-cursor: hand;"
                );
            });

            panneauTours.getChildren().add(bouton);
        }
    }

    // Gère le clic sur le terrain pour poser une tour sur une case "3"
    private void gererClicSurTerrain() {
        pane.setOnMouseClicked(e -> {
            if (tourSelectionnee == null) return;

            int caseX = (int) e.getX() / Terrain.tileSize;
            int caseY = (int) e.getY() / Terrain.tileSize;
            int[][] tab = terrain.getTab();

            // Vérifier que la case est bien un slot (valeur 3)
            if (caseY >= 0 && caseY < tab.length &&
                    caseX >= 0 && caseX < tab[0].length &&
                    tab[caseY][caseX] == 3) {

                Tour tour = new Tour(tourSelectionnee, tourSelectionnee, caseX, caseY);
                TourVue tourVue = new TourVue(tour, pane);
                tourVue.afficher();

                // Marquer la case comme occupée (on change le 3 en 0)
                tab[caseY][caseX] = 0;
            }
        });
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