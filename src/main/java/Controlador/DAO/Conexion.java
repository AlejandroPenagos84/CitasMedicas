package Controlador.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static String db_url = "jdbc:mysql://localhost/bd_hospital";
    private static String db_user = "root";
    private static String db_password = "";
    private static Connection conexion;

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(db_url, db_user, db_password);
            System.out.println(conexion);
        }
        return conexion;
    }

    public static void disconnect() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
