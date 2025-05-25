package com.mycompany.paraulogicweb.daos;

import com.mycompany.paraulogicweb.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDAO {

    public void guardarScore(int partidaId, int puntuacion, int palabrasEncontradas, int totalPalabras, String letras) throws SQLException {
        String sql = "INSERT INTO scores (score, words_found, total_words, letter_set) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, puntuacion);
            ps.setInt(2, palabrasEncontradas);
            ps.setInt(3, totalPalabras);
            ps.setString(4, letras);
            ps.executeUpdate();
        }
    }
}
