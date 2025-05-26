package com.mycompany.paraulogicweb.utils;

import javax.swing.*;
import java.sql.*;

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

    public static boolean isPalabraValida(String palabra) {
        boolean esValida = false;
        String sql = "SELECT 1 FROM words WHERE word = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, palabra.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                esValida = rs.next(); //devuelve true si hay una fila, osea que existe
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return esValida;
    }
}