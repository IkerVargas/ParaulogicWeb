package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.daos.WordDAO;
import com.mycompany.paraulogicweb.model.*;

import java.util.*;

public class WorldValidator {

    public static boolean validarPalabra(Word word, Letter letraCentral, String letrasValidas) {
        boolean palabraValida = true;
        String texto = word.getTexto().toUpperCase();
        letrasValidas = letrasValidas.toUpperCase();

        //verifica si la palabra tiene la letra central y minimo 3 letras
        if (texto.length() < 3 || !texto.contains(String.valueOf(Character.toUpperCase(letraCentral.getCaracter())))) {
            System.out.println("La palabra no tiene la letra central o no tiene minimo 3 letras");
            palabraValida = false;

            //verifica si la palabra esta en la base de datos
        } else if (!WordDAO.isPalabraValida(texto.toLowerCase())) {
            System.out.println("La palabra no esta en la base de datos");
            palabraValida = false;

        } else {
            //crea un mapa con cuantas veces aparece cada letra valida
            Map<Character, Integer> mapaLetrasValidas = new HashMap<>();
            for (char c : letrasValidas.toCharArray()) {
                mapaLetrasValidas.put(c, mapaLetrasValidas.getOrDefault(c, 0) + 1);
            }

            //crea un mapa con cuantas veces aparece cada letra en la palabra del usuario
            Map<Character, Integer> mapaLetrasUsadas = new HashMap<>();
            for (char c : texto.toCharArray()) {
                mapaLetrasUsadas.put(c, mapaLetrasUsadas.getOrDefault(c, 0) + 1);
            }

            //verificamos que todas las letras de la palabra esten en las letras validas
            for (char letra : mapaLetrasUsadas.keySet()) {
                int usadas = mapaLetrasUsadas.get(letra);

                if (usadas > 2) {
                    System.out.println("La letra '" + letra + "' se repite mas de 2 veces");
                    palabraValida = false;
                }
            }
        }

        return palabraValida;
    }
}