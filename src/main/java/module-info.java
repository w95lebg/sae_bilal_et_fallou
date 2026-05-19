module universite_paris8.iut.belbaroudi.museelupin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens universite_paris8.iut.belbaroudi.museelupin to javafx.fxml;
    exports universite_paris8.iut.belbaroudi.museelupin;
    exports universite_paris8.iut.belbaroudi.museelupin.modele;
    opens universite_paris8.iut.belbaroudi.museelupin.modele to javafx.fxml;
}