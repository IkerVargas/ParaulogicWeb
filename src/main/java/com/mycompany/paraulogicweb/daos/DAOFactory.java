package com.mycompany.paraulogicweb.daos;

import com.mycompany.paraulogicweb.utils.DatabaseConnection;

import java.sql.Connection;

public class DAOFactory {
    private static Connection conn;

    static {
        try {
            conn = DatabaseConnection.conectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WordDAO getWordDAO() {
        return new WordDAO();
    }

    public static GameDAO getGameDAO() {
        return new GameDAO();
    }
}
