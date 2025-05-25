package com.mycompany.paraulogicweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_BBDD = "jdbc:mysql://localhost:3306/paraulogic?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // Cambia esto por tu contraseña de MySQL si es necesario

    public static Connection conectar() throws SQLException {
        try {
            // No es necesario cargar el driver explícitamente con MySQL Connector/J 8.x
            return DriverManager.getConnection(URL_BBDD, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
    }
}