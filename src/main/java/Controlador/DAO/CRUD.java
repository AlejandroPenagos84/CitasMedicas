package Controlador.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class CRUD {
    public abstract void insert(String... args);

    public abstract void delete(String... args);

    public abstract void update(String... args);
}

