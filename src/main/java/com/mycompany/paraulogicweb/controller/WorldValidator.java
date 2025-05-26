package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.daos.WordDAO;
import com.mycompany.paraulogicweb.model.*;
import com.mycompany.paraulogicweb.utils.DatabaseConnection;

public class WorldValidator {

    public static boolean validarPalabra(Word word, Letter letraCentral, String letrasValidas) {
        boolean palabraValida = true;
        String texto = word.getTexto().toUpperCase();
        letrasValidas = letrasValidas.toUpperCase();

        //verifica si la palabra tiene la letra central
        if (texto.length() < 3 || !texto.contains(String.valueOf(Character.toUpperCase(letraCentral.getCaracter())))) {
            System.out.println("La palabra no tiene la letra central o no tiene minimo 3 letras");
            palabraValida = false;
        //verifica si la palabra esta en la base de datos
        } else if (!WordDAO.isPalabraValida(texto.toLowerCase())) {
            System.out.println("La palabra no esta en la base de datos");
            palabraValida = false;

        } else {
            //verifica si la palabra contiene las letras validas
            for (char c : texto.toCharArray()) {
                for (char letraValida : letrasValidas.toCharArray()) {
                    if (c == letraValida) {
                        palabraValida = true;
                    }
                }
                if (!palabraValida) {
                    palabraValida = false;
                }
            }
        }

        return palabraValida;
    }
}
