module com.mycompany.gestionbiblioteque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    opens com.mycompany.gestionbiblioteque to javafx.fxml;
    exports com.mycompany.gestionbiblioteque;
}
