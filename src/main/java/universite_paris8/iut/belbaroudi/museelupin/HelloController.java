package universite_paris8.iut.belbaroudi.museelupin;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Acteur;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private TilePane terrain;
    @FXML private Pane     overlayPane; // calque transparent pour les sprites ennemis

    private Terrain modele;

    private static final int TILE_SIZE = 32;

    // Image du sprite ennemi
    private Image imgEnnemi;

    // Map Acteur → ImageView pour retrouver le sprite de chaque ennemi
    // (comme dans le TP où chaque acteur a son propre affichage)
    private Map<Acteur, ImageView> sprites = new HashMap<>();

    // ── initialize ────────────────────────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modele = new Terrain();

        // 1. Afficher les tuiles (ton code existant, intact)
        vueModele();

        // 2. Charger le sprite de l'ennemi
        imgEnnemi = new Image(
                getClass().getResourceAsStream(Ennemi.SPRITE),
                TILE_SIZE, TILE_SIZE, false, false
        );

        // 3. Créer un ennemi sur la case 11 et l'ajouter au terrain
        //    (comme Lancement.java du TP fait : env.ajouter(new Mouton(env)))
        int[] entree = modele.getPositionEntree();
        Ennemi e = new Ennemi(entree[0], entree[1], modele);
        e.calculerChemin();
        modele.ajouter(e);

        // Créer son ImageView et l'ajouter dans l'overlayPane
        ImageView iv = new ImageView(imgEnnemi);
        iv.setSmooth(false);
        positionner(iv, e);
        overlayPane.getChildren().add(iv);
        sprites.put(e, iv);

        // 4. GameLoop — appelle modele.unTour() à chaque tick
        //    (comme la boucle for du TP qui appelle unTour() n fois)
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                modele.unTour();
                rafraichirSprites();
            }
        }.start();
    }

    /**
     * Met à jour la position de chaque sprite selon la position de son Acteur.
     * Supprime les sprites des acteurs morts (comme unTour() enlève les morts).
     */
    private void rafraichirSprites() {
        // Supprimer les sprites des acteurs qui ne sont plus dans la liste
        sprites.entrySet().removeIf(entry -> {
            if (!modele.getActeurs().contains(entry.getKey())) {
                overlayPane.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });

        // Repositionner les sprites des acteurs encore vivants
        for (Acteur a : modele.getActeurs()) {
            ImageView iv = sprites.get(a);
            if (iv != null) {
                positionner(iv, a);
            }
        }
    }

    /** Convertit (ligne, colonne) en pixels et positionne l'ImageView */
    private void positionner(ImageView iv, Acteur a) {
        iv.setLayoutX(a.getColonne() * TILE_SIZE);
        iv.setLayoutY(a.getLigne()   * TILE_SIZE);
    }

    // ── Ton code existant, inchangé ───────────────────────────────────
    public void vueModele() {
        Image mur      = new Image(getClass().getResourceAsStream("/pictures/tile_0_mur.png"));
        Image chemin   = new Image(getClass().getResourceAsStream("/pictures/tile_1_chemin.png"));
        Image marbre   = new Image(getClass().getResourceAsStream("/pictures/tile_2_marbre.png"));
        Image slot     = new Image(getClass().getResourceAsStream("/pictures/tile_3_slot.png"));
        Image obstacle = new Image(getClass().getResourceAsStream("/pictures/tile_4_obstacle.png"));
        Image entree   = new Image(getClass().getResourceAsStream("/pictures/tile_11_entree.png"));
        Image sortie   = new Image(getClass().getResourceAsStream("/pictures/tile_12_sortie.png"));

        int[][] tab = modele.getTab();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                Image im = null;
                if      (tab[i][j] == 0)  im = mur;
                else if (tab[i][j] == 1)  im = chemin;
                else if (tab[i][j] == 2)  im = marbre;
                else if (tab[i][j] == 3)  im = slot;
                else if (tab[i][j] == 4)  im = obstacle;
                else if (tab[i][j] == 11) im = entree;
                else if (tab[i][j] == 12) im = sortie;
                terrain.getChildren().add(creerImage(im));
            }
        }
    }

    public ImageView creerImage(Image im) {
        ImageView imv = new ImageView(im);
        imv.setFitWidth(TILE_SIZE);
        imv.setFitHeight(TILE_SIZE);
        return imv;
    }
}