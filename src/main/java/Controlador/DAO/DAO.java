package Controlador.DAO;
import Controlador.Interfaz.ControllerInterfaceLogin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DAO {
    public void initial_insertion(String objeto, String nombre, String apellido, String user, String password) {
        String consultaSQL = "INSERT INTO " + determinate_table_person(objeto) +
                " (Nombres"+objeto+", Apellidos"+objeto+", User"+objeto+", Password"+objeto+") VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = Conexion.getConexion().prepareStatement(consultaSQL)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, user);
            pstmt.setString(4, doHashPassword(password));

            pstmt.executeUpdate();

            Conexion.disconnect();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String read_for_register(String objeto, String nombre, String apellido)
    {
        String consultaSQL = "SELECT * FROM " + determinate_table_person(objeto) +
                " WHERE Nombres"+objeto+" = ? AND Apellidos"+objeto+" = ?";

        String user = null;
        try (PreparedStatement pstmt = Conexion.getConexion().prepareStatement(consultaSQL)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
                user = rs.getString("Nombres"+objeto)+rs.getString("Apellidos"+objeto);
            Conexion.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public String[] read_for_login(String object, String user, String password)
    {
        String consultaSQL = "SELECT * FROM " + determinate_table_person(object) +
                " WHERE User"+object+" = ? AND Password"+object+" = ?";

        String[]person = null;
        try (PreparedStatement pstmt = Conexion.getConexion().prepareStatement(consultaSQL)) {
            pstmt.setString(1, user);
            pstmt.setString(2, doHashPassword(password));

            ResultSet rs = pstmt.executeQuery();

            if(rs.next())
            {
                if(object.equals("Paciente"))
                    person = getPatient(rs,password);
                else
                    person = getDoctor(rs,password);
            }
            Conexion.disconnect();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    private String[] getPatient(ResultSet rs, String password) throws SQLException, NoSuchAlgorithmException {
        String[]attributes = null;
            attributes= new String[]{
                    rs.getString("NombresPaciente"),
                    rs.getString("ApellidosPaciente"),
                    String.valueOf(rs.getInt("EdadPaciente")),
                    rs.getString("UserPaciente"),
                    rs.getString("PasswordPaciente"),
                    rs.getString("GeneroPaciente"),
                    rs.getString("NumIdentificacionPaciente")};
        return attributes;
    }

    private String[] getDoctor(ResultSet rs, String password) throws SQLException, NoSuchAlgorithmException {
        String[]attributes = null;
        attributes= new String[]{
                    rs.getString("NombresMedico"),
                    rs.getString("ApellidosMedico"),
                    rs.getString("EspecialidadMedico"),
                    rs.getString("HorarioMedico"),
                    rs.getString("NumeroIdentificacionMedico"),
                    rs.getString("UserMedico"),
                    rs.getString("PasswordMedico")};
        return attributes;
    }
    private String determinate_table_person(String nombreObjeto) {return nombreObjeto.equals("Paciente") ? "pacientes":"medicos";}

    private static String doHashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());

        byte[] resultByteArray = messageDigest.digest();

        StringBuilder sb = new StringBuilder();

        for(byte b:resultByteArray)
            sb.append(String.format("%02x",b));

        return sb.toString();
    }
}
