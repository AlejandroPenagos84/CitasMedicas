module Vista{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Vista to javafx.fxml;
    exports Vista;

    opens Controlador to javafx.fxml;
    exports Controlador;
}