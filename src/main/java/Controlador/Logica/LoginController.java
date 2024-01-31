package Controlador.Logica;

import Controlador.DAO.DAO;
import Modelo.Doctor;
import Modelo.Patient;
import Modelo.Person;

public class LoginController {

    private DAO controlDao;

    public LoginController() {
        controlDao = new DAO();
    }

    public Person createPatient(String name, String lastName, int age, String gender, String ID_number, String user, String password) {
        Person patient = new Patient();

        Patient patientCasted = (Patient) patient;
        patientCasted.setName(name);
        patientCasted.setLastName(lastName);
        patientCasted.setAge(age);
        patientCasted.setUser(user);
        patientCasted.setPassword(password);
        patientCasted.setGender(gender);
        patientCasted.setID_number(ID_number);
        return patientCasted;
    }

    public Person createDoctor(String name, String lastName, String specialty, String word_schedule, String ID_number, String user, String password){
        Person doctor = new Doctor();

        Doctor doctorCasted = (Doctor) doctor;
        doctorCasted.setName(name);
        doctorCasted.setLastName(lastName);
        doctorCasted.setSpecialty(specialty);
        doctorCasted.setWork_Schedule(word_schedule);
        doctorCasted.setID_number(ID_number);
        doctorCasted.setUser(user);
        doctorCasted.setPassword(password);
        return doctorCasted;
    }

    public void register_user(String[] atributes)
    {
        controlDao.initial_insertion(atributes[0],atributes[1],atributes[2],atributes[3],atributes[4]);
    }

    public boolean verify_user_existence(String[] atributes)
    {
        return controlDao.read_for_register(atributes[0],atributes[1],atributes[2]) == null;
    }

    public String[] login_user(String[] atributes)
    {
        return controlDao.read_for_login(atributes[0],atributes[1],atributes[2]);
    }
}
