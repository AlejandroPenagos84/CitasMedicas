package Controlador.Interfaz;

import Controlador.Logica.LoginController;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInterfaceLogin implements Initializable {
    private LoginController loginController;

    public ControllerInterfaceLogin() { loginController = new LoginController();}

    @FXML
    private ChoiceBox<String> myChoiceBoxRegister;

    @FXML
    private ChoiceBox<String> myChoiceBoxLogin;

    @FXML
    private Label textFieldErrorLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorTexFieldLabelLogin;

    @FXML
    private Label errorLabelLogin;

    private final String[] tipoDePersonas = {"Paciente","Medico"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBoxRegister.getItems().addAll(tipoDePersonas);
        myChoiceBoxLogin.getItems().addAll(tipoDePersonas);

        errorLabel.setVisible(false);
        textFieldErrorLabel.setVisible(false);

        errorLabelLogin.setVisible(false);
        errorTexFieldLabelLogin.setVisible(false);
    }

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane registerPane;

    @FXML
    public void handleRegisterOption(ActionEvent event) {
        TranslateTransition translateInformation = new TranslateTransition();
        translateInformation.setNode(mainBorderPane.getRight());
        translateInformation.setDuration(Duration.millis(500));
        translateInformation.setByX(305);
        translateInformation.play();


        translateInformation.setOnFinished(actionEvent -> mainBorderPane.setRight(registerPane));

        Translate(mainBorderPane.getLeft(),500,-295);
    }

    @FXML
    public void handleLoginOption(ActionEvent event) throws IOException {
        Translate(mainBorderPane.getLeft(),500,295);
        mainBorderPane.setRight(loginPane);
        Translate(mainBorderPane.getRight(),500,-305);
    }

    private void Translate(Node node, double time, double coordinateX)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(time));
        translate.setByX(coordinateX);
        translate.play();
    }

    @FXML
    private TextField nameRegister;

    @FXML
    private TextField lastNameRegister;

    @FXML
    private TextField passwordRegister;
    public boolean VerifyBlancks()
    {
        return !(nameRegister.getText().isEmpty() || lastNameRegister.getText().isEmpty()
                || passwordRegister.getText().isEmpty() || myChoiceBoxRegister.getValue() == null);
    }

    @FXML
    private TextField userLogin;

    @FXML
    private TextField passwordLogin;

    public boolean VerifyBlancksLogin()
    {
        return !(userLogin.getText().isEmpty() || passwordLogin.getText().isEmpty()
                || myChoiceBoxLogin.getValue() == null);
    }

    public void Register(ActionEvent event) throws IOException {
        String[]attributes;
        if(VerifyBlancks()) {
            String name = nameRegister.getText();
            String lastName = lastNameRegister.getText();

            attributes = new String[]{
                    myChoiceBoxRegister.getValue(),name,lastName, GenerateUser(name, lastName),
                    passwordRegister.getText()
            };

            if(loginController.verify_user_existence(attributes))
            {
                loginController.register_user(attributes);
                switch_scene(event);
            }
            else
                errorLabel.setVisible(true);
            whiten_register();
        }else
            textFieldErrorLabel.setVisible(true);
    }

    public void Login(ActionEvent event) throws IOException {
        if(VerifyBlancksLogin())
        {
            String[]attributes;
            attributes = new String[] {
                    myChoiceBoxLogin.getValue(),
                    userLogin.getText(),passwordLogin.getText()
            };
            if(loginController.login_user(attributes) != null)
                switch_scene(event);
            else
                errorLabelLogin.setVisible(true);
            whiten_login();
        }else
            errorTexFieldLabelLogin.setVisible(true);
    }

    private void switch_scene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Sample.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private String GenerateUser(String name, String lastName)
    {
        String user = null;
        String[]names = name.split(" ");
        String[]lastNames = lastName .split(" ");

        if(names.length == 1 && lastNames.length == 1)
            user = names[0].charAt(0)+lastNames[0];
        else if(names.length == 1 && lastNames.length == 2)
            user = names[0].charAt(0)+lastNames[0]+lastNames[1].charAt(0);
        else if(names.length == 2 && lastNames.length == 1)
            user = names[0].charAt(0)+ "" +names[1].charAt(0)+lastNames[0];
        else if(names.length == 2 && lastNames.length == 2)
            user = names[0].charAt(0)+ "" +names[1].charAt(0)+lastNames[0]+lastNames[1].charAt(0);

        return user;
    }

    private void whiten_register()
    {
        nameRegister.setText("");
        lastNameRegister.setText("");
        passwordRegister.setText("");
        myChoiceBoxRegister.setValue("");
    }

    private void whiten_login()
    {
        userLogin.setText("");
        passwordLogin.setText("");
        myChoiceBoxLogin.setValue("");
    }
}
