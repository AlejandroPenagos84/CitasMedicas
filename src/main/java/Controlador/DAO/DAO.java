package Controlador.DAO;
import Controlador.Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DAO {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Controller logicControl;

    public DAO(Controller logicControl)
    {
        con = null;
        st = null;
        rs = null;
        this.logicControl = logicControl;
    }

    public void initial_insertion(String objeto, String nombre, String apellido, String user, String password) {
        String consultaSQL = "INSERT INTO " + determinarTablaPersona(objeto) +
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

    public String Consultar(String objeto, String nombre, String apellido)
    {
        String consultaSQL = "SELECT * FROM " + determinarTablaPersona(objeto) +
                " WHERE Nombres"+objeto+" = ? AND Apellidos"+objeto+" = ?";

        System.out.println(consultaSQL);

        String user = null;
        try (PreparedStatement pstmt = Conexion.getConexion().prepareStatement(consultaSQL)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);

            rs = pstmt.executeQuery();

            if(rs.next())
                user = rs.getString("Nombres"+objeto)+rs.getString("Apellidos"+objeto);
            Conexion.disconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void read_for_login(String object, String user, String password)
    {
        String consultaSQL = "SELECT * FROM " + determinarTablaPersona(object) +
                " WHERE User"+object+" = ? AND Password"+object+" = ?";

        try (PreparedStatement pstmt = Conexion.getConexion().prepareStatement(consultaSQL)) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if(rs.next())
            {
                if(object.equals("Paciente"))
                    getPatient(rs,password);
                else
                    getDoctor(rs,password);
            }
            Conexion.disconnect();
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPatient(ResultSet rs, String password) throws SQLException, NoSuchAlgorithmException {
        if(VerifyPassword(rs.getString("PasswordPaciente"),password))
        {
            logicControl.createPatient(
                    rs.getString("NombresPaciente"),
                    rs.getString("ApellidosPaciente"),
                    rs.getInt("EdadPaciente"),
                    rs.getString("UserPaciente"),
                    rs.getString("PasswordPaciente"),
                    rs.getString("GeneroPaciente"),
                    rs.getString("NumIdentificacionPaciente")
            );
        }else{
            logicControl.createPatient(
                    null,
                    null,
                    0,
                    null,
                    null,
                    null,
                    null
            );
        }
    }

    private void getDoctor(ResultSet rs, String password) throws SQLException, NoSuchAlgorithmException {
        if(VerifyPassword(rs.getString("PasswordMedico"),password))
        {
            logicControl.createDoctor(
                    rs.getString("NombresMedico"),
                    rs.getString("ApellidosMedico"),
                    rs.getString("EspecialidadMedico"),
                    rs.getString("HorarioMedico"),
                    rs.getString("NumeroIdentificacionMedico"),
                    rs.getString("UserMedico"),
                    rs.getString("PasswordMedico")
            );
        }else{
                logicControl.createDoctor(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
    }
    private String determinarTablaPersona(String nombreObjeto)
    {
        return nombreObjeto.equals("Paciente") ? "pacientes":"medicos";
    }

    private boolean VerifyPassword(String password_db,  String password) throws NoSuchAlgorithmException {
        return doHashPassword(password).equals(password_db);
    }
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
