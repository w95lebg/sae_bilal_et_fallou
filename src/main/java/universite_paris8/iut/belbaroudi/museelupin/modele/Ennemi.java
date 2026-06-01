package universite_paris8.iut.belbaroudi.museelupin.modele;

public class Ennemi extends Acteur {

    private char derniereDirection = ' ';

    public Ennemi(int x, int y, String code, Terrain e) {
        super(x, y, code, e);
    }

    @Override
    public void move() {
        int tileSize = Terrain.tileSize;
        int x        = (int) this.getX();
        int y        = (int) this.getY();
        int caseX    = x / tileSize;
        int caseY    = y / tileSize;
        int[][] tab  = this.getEnv().getTab();

        if (derniereDirection != 'B' && caseY - 1 >= 0 && tab[caseY - 1][caseX] == 1) {
            setCibleY(caseY - 1);
            derniereDirection = 'H';
        } else if (derniereDirection != 'G' && caseX + 1 < tab[0].length && tab[caseY][caseX + 1] == 1) {
            setCibleX(caseX + 1);
            derniereDirection = 'D';
        } else if (derniereDirection != 'H' && caseY + 1 < tab.length && tab[caseY + 1][caseX] == 1) {
            setCibleY(caseY + 1);
            derniereDirection = 'B';
        } else if (derniereDirection != 'D' && caseX - 1 >= 0 && tab[caseY][caseX - 1] == 1) {
            setCibleX(caseX - 1);
            derniereDirection = 'G';
        } else {
            System.out.println("Fin de parcours !");
        }
    }
}