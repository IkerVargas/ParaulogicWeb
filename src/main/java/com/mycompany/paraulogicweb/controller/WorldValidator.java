package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.model.*;

public class WorldValidator {

    public static boolean validarPalabra(Word word, Letter letraCentral, String letrasValidas ) {
        boolean valido = true;
        String texto = word.getTexto().toUpperCase();
        letrasValidas = letrasValidas.toUpperCase();

        //comprovar si la palabra contiene la letra central y que tenga una longitud de al menos 3 caracteres
        if (texto.length() < 3 || !texto.contains(String.valueOf(letraCentral.getCaracter()))) {
            System.out.println("La palabra no tiene la letra central o no tiene minimo 3 letras");
            valido = false;
        }

        //comprovar las letras validas
        for (char c : texto.toCharArray()) {
            if (letrasValidas.indexOf(c) == -1) {
                System.out.println("La palabra contiene letras no validas: " + c);
                valido = false;
            }
        }

        return valido;
    }
}
