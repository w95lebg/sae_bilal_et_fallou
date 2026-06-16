package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.scene.image.ImageView;

public class Tour {

    private String nom;
    private String imagePath;
    private int caseX;
    private int caseY;
    private ImageView spriteView;

    public Tour(String nom, String imagePath, int caseX, int caseY) {
        this.nom       = nom;
        this.imagePath = imagePath;
        this.caseX     = caseX;
        this.caseY     = caseY;
    }

    public String getNom()       { return nom; }
    public String getImagePath() { return imagePath; }
    public int    getCaseX()     { return caseX; }
    public int    getCaseY()     { return caseY; }

    public boolean estLaser() {
        return imagePath != null && imagePath.contains("laser");
    }

    public boolean estPorte() {
        return imagePath != null && imagePath.contains("porte");
    }

    private boolean activee = false;
    public boolean isActivee()          { return activee; }
    public void    setActivee(boolean v){ activee = v; }

    public ImageView getSpriteView()            { return spriteView; }
    public void      setSpriteView(ImageView v) { spriteView = v; }
}