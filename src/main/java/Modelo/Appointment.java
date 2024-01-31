package Modelo;



public class Appointment {

    private String date;
    private String hour;
    private Patient patient;
    private Doctor doctor;

    private boolean estado = true;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHout(String hour) {
        this.hour = hour;
    }

    public Patient getPaciente() {
        return patient;
    }

    public void setPaciente(Patient patient) {
        this.patient = patient;
    }

    public Doctor getMedico() {
        return doctor;
    }

    public void setMedico(Doctor doctor) {
        this.doctor = doctor;
    }

}
