module Vista{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Vista to javafx.fxml;
    exports Vista;

    exports Controlador.Interfaz;
    opens Controlador.Interfaz to javafx.fxml;
    exports Controlador.Logica;
    opens Controlador.Logica to javafx.fxml;
}