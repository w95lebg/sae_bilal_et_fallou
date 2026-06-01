package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Acteur {
    private String id;
    private static int compteur = 1;

    private DoubleProperty x;
    private DoubleProperty y;

    private int cibleX;
    private int cibleY;

    private Terrain env;

    public Acteur(int caseX, int caseY, String code, Terrain e) {
        this.id     = code + compteur++;
        this.env    = e;
        this.cibleX = caseX;
        this.cibleY = caseY;
        this.x      = new SimpleDoubleProperty(caseX * Terrain.tileSize);
        this.y      = new SimpleDoubleProperty(caseY * Terrain.tileSize);
    }

    public double getX()              { return x.get(); }
    public void   setX(double v)      { x.set(v); }
    public DoubleProperty xProperty() { return x; }

    public double getY()              { return y.get(); }
    public void   setY(double v)      { y.set(v); }
    public DoubleProperty yProperty() { return y; }

    public int  getCibleX()           { return cibleX; }
    public int  getCibleY()           { return cibleY; }
    public void setCibleX(int v)      { cibleX = v; }
    public void setCibleY(int v)      { cibleY = v; }

    public String  getId()            { return id; }
    public Terrain getEnv()           { return env; }

    public abstract void move();

    public void avancer(double vitesse) {
        double ciblePxX = cibleX * Terrain.tileSize;
        double ciblePxY = cibleY * Terrain.tileSize;

        double dx   = ciblePxX - getX();
        double dy   = ciblePxY - getY();
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= vitesse) {
            setX(ciblePxX);
            setY(ciblePxY);
            move();
        } else {
            setX(getX() + (dx / dist) * vitesse);
            setY(getY() + (dy / dist) * vitesse);
        }
    }
}