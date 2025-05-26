package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.model.*;
import com.mycompany.paraulogicweb.utils.DatabaseConnection;

public class WorldValidator {

    public static boolean validarPalabra(Word word, Letter letraCentral, String letrasValidas) {
        boolean valido = true;
        String texto = word.getTexto().toUpperCase();
        letrasValidas = letrasValidas.toUpperCase();

        if (texto.length() < 3 || !texto.contains(String.valueOf(Character.toUpperCase(letraCentral.getCaracter())))) {
            System.out.println("La palabra no tiene la letra central o no tiene minimo 3 letras");
            valido = false;
        }

        //verifica si la palabra contiene las letras validas
        for (char c : texto.toCharArray()) {
            if (letrasValidas.indexOf(c) == -1) {
                System.out.println("La palabra contiene letras no validas: " + c);
                valido = false;
            }
        }
        //verifica si la palabra esta en la base de datos
        if (!DatabaseConnection.isPalabraValida(texto.toLowerCase())) {
            System.out.println("La palabra no esta en la base de datos");
            valido = false;
        }

        return valido;
    }
}
