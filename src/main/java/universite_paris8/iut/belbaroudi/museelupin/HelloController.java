package universite_paris8.iut.belbaroudi.museelupin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import universite_paris8.iut.belbaroudi.museelupin.modele.tours.*;
import universite_paris8.iut.belbaroudi.museelupin.vue.EnnemiVue;
import universite_paris8.iut.belbaroudi.museelupin.vue.TerrainVue;
import universite_paris8.iut.belbaroudi.museelupin.vue.TourVue;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private static final double VITESSE       = 1.0;
    private static final int    BUDGET_DEPART = 999;
    private static final int    GAIN_PAR_ENNEMI = 50;

    // Informations des tours disponibles : nom, image, prix
    private static final String[][] TOURS_DISPONIBLES = {
            { "Camera",     "/pictures/tower_camera.png",     "100" },
            { "Brouilleur", "/pictures/tower_brouilleur.png", "150" },
            { "Gardien",    "/pictures/tower_gardien.png",    "200" },
            { "Laser",      "/pictures/tower_laser.png",      "150" },
            { "Porte",      "/pictures/tower_porte.png",      "100" },
    };

    private Terrain       terrain;
    private Environnement environnement;
    private Timeline      gameLoop;

    private String tourSelectionnee     = null;
    private int    prixTourSelectionnee = 0;

    private IntegerProperty budget = new SimpleIntegerProperty(BUDGET_DEPART);

    @FXML private Pane     pane;
    @FXML private TilePane paneTerrain;
    @FXML private VBox     panneauTours;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        this.terrain       = new Terrain();
        this.environnement = new Environnement(terrain, pane);

        TerrainVue terrainVue = new TerrainVue(terrain, paneTerrain);
        terrainVue.chargerMap();

        // Premier ennemi
        Ennemi p1 = new Ennemi(0, 4, "E", terrain);
        EnnemiVue ennemiVue = new EnnemiVue(p1, pane, "/pictures/enemy_faussaire.png");
        ennemiVue.setOnMort(() -> {
            budget.set(budget.get() + GAIN_PAR_ENNEMI);
            faireApparaitreEnnemi(2);
        });
        ennemiVue.creerSprite();
        this.environnement.ajouterPersonnage(p1);

        remplirPanneauTours();
        gererClicSurTerrain();
        initAnimation();
        gameLoop.play();
    }

    // -----------------------------------------------------------------------
    // Construction du panneau latéral des tours
    // -----------------------------------------------------------------------

    private void remplirPanneauTours() {
        Label labelBudget = new Label();
        labelBudget.setStyle(
                "-fx-text-fill: #f1c40f; -fx-font-size: 14px; -fx-font-weight: bold;" +
                        "-fx-padding: 8 6 8 6;"
        );
        labelBudget.textProperty().bind(budget.asString("💰 Budget : %d $"));
        panneauTours.getChildren().add(labelBudget);

        for (String[] tourInfo : TOURS_DISPONIBLES) {
            String nom       = tourInfo[0];
            String imagePath = tourInfo[1];
            int    prix      = Integer.parseInt(tourInfo[2]);

            VBox bouton = creerBoutonTour(nom, imagePath, prix);
            panneauTours.getChildren().add(bouton);
        }
    }

    private VBox creerBoutonTour(String nom, String imagePath, int prix) {
        VBox bouton = new VBox(4);
        bouton.setStyle(
                "-fx-background-color: #3c3f41; -fx-padding: 6; " +
                        "-fx-border-color: #555; -fx-border-width: 1; -fx-cursor: hand;"
        );
        bouton.setPrefWidth(120);

        Image     img = new Image(getClass().getResourceAsStream(imagePath));
        ImageView iv  = new ImageView(img);
        iv.setFitWidth(48);
        iv.setFitHeight(48);
        iv.setPreserveRatio(true);

        Label labelNom  = new Label(nom);
        labelNom.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        Label labelPrix = new Label(prix + " $");
        labelPrix.setStyle("-fx-text-fill: #f1c40f; -fx-font-size: 11;");

        bouton.getChildren().addAll(iv, labelNom, labelPrix);

        bouton.setOnMouseClicked(e -> {
            tourSelectionnee     = imagePath;
            prixTourSelectionnee = prix;
            mettreEnSurbrilanceBouton(bouton);
        });

        return bouton;
    }


    private void mettreEnSurbrilanceBouton(VBox boutonChoisi) {
        for (javafx.scene.Node node : panneauTours.getChildren()) {
            if (node instanceof VBox) {
                node.setStyle(
                        "-fx-background-color: #3c3f41; -fx-padding: 6; " +
                                "-fx-border-color: #555; -fx-border-width: 1; -fx-cursor: hand;"
                );
            }
        }
        boutonChoisi.setStyle(
                "-fx-background-color: #4a6fa5; -fx-padding: 6; " +
                        "-fx-border-color: #aad4f5; -fx-border-width: 2; -fx-cursor: hand;"
        );
    }



    private void gererClicSurTerrain() {
        pane.setOnMouseClicked(e -> {
            if (tourSelectionnee == null) return;
            if (budget.get() < prixTourSelectionnee) {
                afficherMessageBudget();
                return;
            }

            int caseX   = (int) e.getX() / Terrain.tileSize;
            int caseY   = (int) e.getY() / Terrain.tileSize;
            int[][] tab = terrain.getTab();

            if (caseY < 0 || caseY >= tab.length || caseX < 0 || caseX >= tab[0].length) return;

            boolean estPorte  = tourSelectionnee.contains("porte");
            boolean caseDispo = (tab[caseY][caseX] == 3)
                    || (estPorte && tab[caseY][caseX] == 1);

            if (caseDispo) {
                budget.set(budget.get() - prixTourSelectionnee);

                Tour tour = creerTour(tourSelectionnee, caseX, caseY);
                environnement.ajouterTour(tour);

                TourVue tourVue = new TourVue(tour, pane);
                tourVue.afficher();


                if (!estPorte) {
                    tab[caseY][caseX] = 0;
                }
            }
        });
    }


    private Tour creerTour(String imagePath, int caseX, int caseY) {
        if (imagePath.contains("laser"))      return new TourLaser(caseX, caseY);
        if (imagePath.contains("porte"))      return new TourPorte(caseX, caseY);
        if (imagePath.contains("camera"))     return new TourCamera(caseX, caseY);
        if (imagePath.contains("brouilleur")) return new TourBrouilleur(caseX, caseY);
        if (imagePath.contains("gardien"))    return new TourGardien(caseX, caseY);
        throw new IllegalArgumentException("Type de tour inconnu : " + imagePath);
    }



    private void faireApparaitreEnnemi(int numero) {
        Ennemi    ennemi;
        EnnemiVue ennemiVue;

        switch (numero) {
            case 2:
                ennemi    = new Ennemi(0, 4, "E", terrain);
                ennemiVue = new EnnemiVue(ennemi, pane, "/pictures/enemy_vandal.png");
                ennemiVue.setOnMort(() -> {
                    budget.set(budget.get() + GAIN_PAR_ENNEMI);
                    faireApparaitreEnnemi(3);
                });
                break;
            case 3:
                ennemi    = new Ennemi(0, 4, "E", terrain);
                ennemiVue = new EnnemiVue(ennemi, pane, "/pictures/enemy_gang.png");
                ennemiVue.setOnMort(() -> {
                    budget.set(budget.get() + GAIN_PAR_ENNEMI);
                    faireApparaitreEnnemi(4);
                });
                break;
            case 4:
                ennemi    = new Ennemi(0, 4, "E", terrain);
                ennemiVue = new EnnemiVue(ennemi, pane, "/pictures/enemy_pickpocket.png");
                ennemiVue.setOnMort(() -> budget.set(budget.get() + GAIN_PAR_ENNEMI));
                break;
            default:
                return;
        }

        ennemiVue.creerSprite();
        this.environnement.ajouterPersonnage(ennemi);
    }


    private void afficherMessageBudget() {
        Label msg = new Label("❌ Budget insuffisant !");
        msg.setStyle(
                "-fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold;" +
                        "-fx-background-color: rgba(180,0,0,0.85);" +
                        "-fx-padding: 6 12 6 12; -fx-background-radius: 6;"
        );
        msg.setTranslateX(10);
        msg.setTranslateY(10);
        pane.getChildren().add(msg);

        new Timeline(new KeyFrame(Duration.millis(1500),
                ev -> pane.getChildren().remove(msg))
        ).play();
    }



    private void initAnimation() {
        this.gameLoop = new Timeline();
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);
        this.gameLoop.getKeyFrames().add(
                new KeyFrame(Duration.millis(16), ev -> environnement.unTour(VITESSE))
        );
    }
}
