package universite_paris8.iut.belbaroudi.museelupin.modele;

public class Tour {

    private String nom;
    private String imagePath;
    private int caseX;
    private int caseY;

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
}