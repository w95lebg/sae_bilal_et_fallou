package universite_paris8.iut.belbaroudi.museelupin.modele;

/**
 * Ennemi — sous-classe d'Acteur (comme Mouton étend Acteur dans le TP)
 *
 * Sprint 1 : agit() = se déplace d'une case sur les 1.
 * Utilise enemy_faussaire.png comme sprite.
 */
public class Ennemi extends Acteur {

    // Sprite associé (nom du fichier dans /pictures/)
    public static final String SPRITE = "/pictures/enemy_faussaire.png";

    // ── Constructeur ──────────────────────────────────────────────────
    // vitesse = 20 ticks entre chaque case (comme le TP fixe la vitesse)
    public Ennemi(int ligne, int colonne, Terrain terrain) {
        super(ligne, colonne, 20, 100, terrain);
    }

    // ── agit() — abstraite dans Acteur, implémentée ici ──────────────
    // Comme Mouton.agit() dans le TP : action simple à chaque tour
    @Override
    public void agit() {
        seDeplace();
    }

    @Override
    public String toString() {
        return "Ennemi " + super.toString();
    }
}