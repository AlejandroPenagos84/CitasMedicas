package Controlador.Interfaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SampleControllerInterface implements Initializable {
    @FXML
    private StackPane contentArea;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try
        {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch(Exception e)
        {
            Logger.getLogger(SampleControllerInterface.class.getName()).log(Level.SEVERE, null,e);
        }
    }



}
