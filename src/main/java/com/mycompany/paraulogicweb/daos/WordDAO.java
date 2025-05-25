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

    //comprueba que una palabra existe en la base de datos
    public boolean existe(String word) {
        String query = "SELECT COUNT(*) FROM words WHERE word = ?";
        boolean existe = false;
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, word.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            existe = rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
}
