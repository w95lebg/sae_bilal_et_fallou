package universite_paris8.iut.belbaroudi.museelupin.modele;

public class Ennemi extends Acteur {

    // ' ' = aucune direction, 'H'=haut, 'D'=droite, 'B'=bas, 'G'=gauche
    private char derniereDirection = ' ';

    public Ennemi(int x, int y, String code, Terrain e) {
        super(x, y, code, e);
    }


    @Override
    public void move() {
        int x   = this.getCibleX();
        int y   = this.getCibleY();
        int[][] tab = this.getEnv().getTab();

        if (derniereDirection != 'B' && y - 1 >= 0 && tab[y - 1][x] == 1) {
            setCibleY(y - 1);
            derniereDirection = 'H';
        } else if (derniereDirection != 'G' && x + 1 < tab[0].length && tab[y][x + 1] == 1) {
            setCibleX(x + 1);
            derniereDirection = 'D';
        } else if (derniereDirection != 'H' && y + 1 < tab.length && tab[y + 1][x] == 1) {
            setCibleY(y + 1);
            derniereDirection = 'B';
        } else if (derniereDirection != 'D' && x - 1 >= 0 && tab[y][x - 1] == 1) {
            setCibleX(x - 1);
            derniereDirection = 'G';
        } else {
            System.out.println("Fin de parcours !");
        }
    }
}