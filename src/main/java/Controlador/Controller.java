package Controlador;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane logoPane;

    @FXML
    private AnchorPane registerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void handleRegisterOption(ActionEvent event) throws IOException {
        TranslateTransition translateInformation = new TranslateTransition();
        translateInformation.setNode(mainBorderPane.getRight());
        translateInformation.setDuration(Duration.millis(1000));
        translateInformation.setByX(305);
        translateInformation.play();


        TranslateTransition translateLogo = new TranslateTransition();
        translateLogo.setNode(mainBorderPane.getLeft());
        translateLogo.setDuration(Duration.millis(1000));
        translateLogo.setByX(-295);
        translateLogo.play();
        mainBorderPane.setRight(registerPane);
    }

    @FXML
    public void handleLoginOption(ActionEvent event) throws IOException {

        TranslateTransition translateLogo = new TranslateTransition();
        translateLogo.setNode(mainBorderPane.getLeft());
        translateLogo.setDuration(Duration.millis(1000));
        translateLogo.setByX(295);
        translateLogo.play();

        mainBorderPane.setRight(loginPane);

        TranslateTransition translateInformation = new TranslateTransition();
        translateInformation.setNode(mainBorderPane.getRight());
        translateInformation.setDuration(Duration.millis(1000));
        translateInformation.setByX(-305);
        translateInformation.play();
    }
}
