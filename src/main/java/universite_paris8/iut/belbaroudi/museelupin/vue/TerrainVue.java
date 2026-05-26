package universite_paris8.iut.belbaroudi.museelupin.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;


public class TerrainVue {

    private Terrain terrain;
    private TilePane terrainPane;

    public TerrainVue(Terrain terrain, TilePane terrainPane) {
        this.terrain = terrain;
        this.terrainPane = terrainPane;
    }

    public void chargerMap() {
        Image mur = new Image(String.valueOf(getClass().getResource("/pictures/tile_0_mur.png")));
        Image chemin = new Image(String.valueOf(getClass().getResource("/pictures/tile_1_chemin.png")));
        Image marbre = new Image(String.valueOf(getClass().getResource("/pictures/tile_2_marbre.png")));
        Image slot = new Image(String.valueOf(getClass().getResource("/pictures/tile_3_slot.png")));
        Image obstacle = new Image(String.valueOf(getClass().getResource("/pictures/tile_4_obstacle.png")));
        Image entree = new Image(String.valueOf(getClass().getResource("/pictures/tile_11_entree.png")));
        Image sortie = new Image(String.valueOf(getClass().getResource("/pictures/tile_12_sortie.png")));

        int[][] tab = terrain.getTab();
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
                terrainPane.getChildren().add(imv);
            }
        }
    }

    private ImageView setUpImage(Image img) {
        ImageView imv = new ImageView();
        imv.setImage(img);
        imv.setFitHeight(Terrain.tileSize);
        imv.setFitWidth(Terrain.tileSize);
        return imv;
    }

}