package Modelo;

import java.util.ArrayList;

public class Doctor extends Person{
    private String specialty;
    private String work_Schedule;

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getWork_Schedule() {
        return work_Schedule;
    }

    public void setWork_Schedule(String work_Schedule) {
        this.work_Schedule = work_Schedule;
    }
}
