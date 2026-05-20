package universite_paris8.iut.belbaroudi.museelupin.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Acteur {
    private String id;
    private IntegerProperty x;
    private IntegerProperty y;
    private static int i = 1;

    private Terrain env;

    public Acteur(int x, int y, String code, Terrain e){
        this.id = code + i;
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.env = e;
    }

    public void setX(int n) {
        this.x.set(n);
    }

    public int getX() {
        return this.x.get();
    }

    public IntegerProperty xProperty(){
        return this.x;
    }

    public void setY(int n) {
        this.y.set(n);
    }

    public int getY() {
        return this.y.get();
    }

    public IntegerProperty yProperty(){
        return this.y;
    }

    public String getId() {
        return id;
    }

    public void move(){
        int x = this.getX();
        int y = this.getY();

        if (this.env.getTab()[y][x+1]==1){
            setX(x+1);
            System.out.println("droite");

        }
        else if (this.env.getTab()[y+1][x]==1) {
            setY(y+1);
            System.out.println("bas");
        }
        else if (this.env.getTab()[y-1][x]==1){
            setY(y-1);
            System.out.println("haut");

        }  else if (this.env.getTab()[y][x-1]==1) {
            setX(x-1);
            System.out.println("gauche");

        }

    }

}