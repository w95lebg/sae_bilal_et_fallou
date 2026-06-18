package universite_paris8.iut.belbaroudi.museelupin.modele.tours;

import javafx.scene.layout.Pane;
import universite_paris8.iut.belbaroudi.museelupin.modele.Ennemi;
import universite_paris8.iut.belbaroudi.museelupin.modele.Terrain;
import universite_paris8.iut.belbaroudi.museelupin.modele.Tour;

import java.util.ArrayList;


public class TourGardien extends Tour {

    private static final double PORTEE       = Terrain.tileSize * 2;
    private static final double DEGATS       = 0.10;
    private static final int    DELAI_TICKS  = 60; // ~1 seconde à 60fps

    private int ticksDepuisDerniereAttaque = 0;

    public TourGardien(int caseX, int caseY) {
        super("Gardien", "/pictures/tower_gardien.png", caseX, caseY);
    }

    @Override
    public boolean agir(ArrayList<Ennemi> ennemis, Terrain terrain, Pane pane) {
        int gPxX = getCaseX() * Terrain.tileSize;
        int gPxY = getCaseY() * Terrain.tileSize;

        ticksDepuisDerniereAttaque++;

        if (ticksDepuisDerniereAttaque < DELAI_TICKS) return false;

        for (Ennemi e : ennemis) {
            if (e.estMort()) continue;

            double dx = Math.abs(e.getX() - gPxX);
            double dy = Math.abs(e.getY() - gPxY);

            if (dx <= PORTEE && dy <= PORTEE) {
                e.prendresDegats(DEGATS);
                ticksDepuisDerniereAttaque = 0;
                break; // attaque un seul ennemi par cycle
            }
        }
        return false; // reste en place
    }
}
