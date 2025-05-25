package com.mycompany.paraulogicweb.utils;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_BBDD = "jdbc:mysql://localhost:3306/paraulogic";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el driver: " + e.getMessage());
            throw new SQLException("Driver JDBC no encontrado", e);
        }
        return DriverManager.getConnection(URL_BBDD, USUARIO, PASSWORD);
    }
}