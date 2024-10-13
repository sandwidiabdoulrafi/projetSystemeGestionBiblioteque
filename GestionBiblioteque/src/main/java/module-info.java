module com.mycompany.gestionbiblioteque {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.gestionbiblioteque to javafx.fxml;
    exports com.mycompany.gestionbiblioteque;
}
