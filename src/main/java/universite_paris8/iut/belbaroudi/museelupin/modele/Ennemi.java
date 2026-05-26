package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Ennemi {
    private String id;
    private IntegerProperty x;
    private IntegerProperty y;
    private static int i = 1;

    private Terrain env;


    private String derniereDirection = ""; //TODO : utiliser un type char ou int

    public Ennemi(int x, int y, String code, Terrain e){
        this.id = code + i;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.env = e;
    }

    public void setX(int n) { this.x.set(n); }
    public int getX()       { return this.x.get(); }
    public IntegerProperty xProperty() { return this.x; }

    public void setY(int n) { this.y.set(n); }
    public int getY()       { return this.y.get(); }
    public IntegerProperty yProperty() { return this.y; }

    public String getId()   { return id; }

    public void move(){
        int x = this.getX();
        int y = this.getY();
        int[][] tab = this.env.getTab();

        // On teste les 4 directions en excluant le sens inverse
        if (!derniereDirection.equals("bas") && y-1 >= 0 && tab[y-1][x] == 1){
            setY(y-1);
            derniereDirection = "haut";
            System.out.println("haut");
        }
        else if (!derniereDirection.equals("gauche") && x+1 < tab[0].length && tab[y][x+1] == 1){
            setX(x+1);
            derniereDirection = "droite";
            System.out.println("droite");
        }
        else if (!derniereDirection.equals("haut") && y+1 < tab.length && tab[y+1][x] == 1){
            setY(y+1);
            derniereDirection = "bas";
            System.out.println("bas");
        }
        else if (!derniereDirection.equals("droite") && x-1 >= 0 && tab[y][x-1] == 1){
            setX(x-1);
            derniereDirection = "gauche";
            System.out.println("gauche");
        }
        else {
            System.out.println("Fin !");
        }
    }
}