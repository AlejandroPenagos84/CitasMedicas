package Controlador;

import Controlador.DAO.DAO;
import Modelo.Patient;
import Modelo.Doctor;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private DAO conntrolDao;

    private Patient patient;
    private Doctor doctor;
    public Controller()
    {
        conntrolDao = new DAO(this);
    }

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

    private String[] tipoDePersonas = {"Paciente","Medico"};

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
    public void handleRegisterOption(ActionEvent event) throws IOException {
        TranslateTransition translateInformation = new TranslateTransition();
        translateInformation.setNode(mainBorderPane.getRight());
        translateInformation.setDuration(Duration.millis(500));
        translateInformation.setByX(305);
        translateInformation.play();


        translateInformation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainBorderPane.setRight(registerPane);
            }
        });

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

    public void Register(ActionEvent event)
    {
        if(VerifyBlancks()) {
            String name = nameRegister.getText();
            String lastName = lastNameRegister.getText();
            if(conntrolDao.Consultar(myChoiceBoxRegister.getValue(),name,lastName) == null)
                conntrolDao.initial_insertion(myChoiceBoxRegister.getValue(), name, lastName, GenerateUser(name, lastName), passwordRegister.getText());
            else
                errorLabel.setVisible(true);
            Whiten();
        }else
            textFieldErrorLabel.setVisible(true);
    }

    public void Login(ActionEvent event)
    {
        if(VerifyBlancksLogin()) {

            if(myChoiceBoxLogin.getValue().equals("Paciente"))
            {
                conntrolDao.read_for_login(myChoiceBoxLogin.getValue(),userLogin.getText(),passwordLogin.getText());
                if(patient != null)
                    System.out.println("YUPI");
                else
                    errorLabelLogin.setVisible(true);
            }else {
                if(doctor != null)
                    System.out.println("YUPI");
                else
                    errorLabelLogin.setVisible(true);
            }
            Whiten();
        }else
            textFieldErrorLabel.setVisible(true);
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

    private void Whiten()
    {
        nameRegister.setText("");
        lastNameRegister.setText("");
        passwordRegister.setText("");
        myChoiceBoxRegister.setValue("");
    }

    public Patient createPatient(String name, String lastName, int age, String gender, String ID_number, String user, String password){
        patient = new Patient();
        patient.setName(name);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setUser(user);
        patient.setPassword(password);
        patient.setGender(gender);
        patient.setID_number(ID_number);;
        return patient;
    }

    public Doctor createDoctor(String name, String lastName, String specialty, String word_schedule, String ID_number, String user, String password){
        doctor = new Doctor();
        doctor.setName(name);
        doctor.setLastName(lastName);
        doctor.setSpecialty(specialty);
        doctor.setWork_Schedule(word_schedule);
        doctor.setID_number(ID_number);
        doctor.setUser(user);
        doctor.setPassword(password);
        return doctor;
    }
}
