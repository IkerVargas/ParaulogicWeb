package com.mycompany.paraulogicweb.daos;

import com.mycompany.paraulogicweb.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

    //obtiene todas las palabras de la base de datos
    public List<String> obtenerTodasLasPalabras() {
        List<String> palabras = new ArrayList<>();
        String query = "SELECT word FROM words";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                palabras.add(rs.getString("word").toLowerCase());
            }
            System.out.println("Palabras cargadas desde la base de datos: " + palabras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return palabras;
    }

    //verifica si la palabra esta en la base de datos
    public static boolean isPalabraValida(String palabra) {
        boolean esValida = false;
        String sql = "SELECT word FROM words WHERE word = ?";
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
