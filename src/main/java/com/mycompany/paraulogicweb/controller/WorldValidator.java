package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.daos.WordDAO;
import com.mycompany.paraulogicweb.model.*;

import java.util.HashMap;
import java.util.Map;

public class WorldValidator {

    public static boolean validarPalabra(Word word, Letter letraCentral, String letrasValidas) {
        boolean palabraValida = true;

        //convertimos todo a mayúsculas para comparar bien
        String texto = word.getTexto().toUpperCase();
        letrasValidas = letrasValidas.toUpperCase();

        //comprobamos que tenga al menos 3 letras y la letra central
        if (texto.length() < 3 || !texto.contains(String.valueOf(letraCentral.getCaracter()))) {
            System.out.println("La palabra es muy corta o no tiene la letra central");
            palabraValida = false;

            //comprobamos si la palabra existe en la base de datos
        } else if (!WordDAO.isPalabraValida(texto.toLowerCase())) {
            System.out.println("La palabra no existe en la base de datos");
            palabraValida = false;

            //comprobamos si la palabra esta formada solo con las letras generadas
        } else {
            //guardamos cuántas veces aparece cada letra en las letras válidas
            Map<Character, Integer> letrasDisponibles = new HashMap<>();
            for (char c : letrasValidas.toCharArray()) {
                letrasDisponibles.put(c, letrasDisponibles.getOrDefault(c, 0) + 1);
            }

            //guardamos cuántas veces aparece cada letra en la palabra que escribió el usuario
            Map<Character, Integer> letrasUsadas = new HashMap<>();
            for (char c : texto.toCharArray()) {
                letrasUsadas.put(c, letrasUsadas.getOrDefault(c, 0) + 1);
            }

            //miramos si se usan letras que no están en las validas o se repiten más veces de lo que hay
            for (char c : letrasUsadas.keySet()) {
                if (!letrasDisponibles.containsKey(c) || letrasUsadas.get(c) > letrasDisponibles.get(c)) {
                    System.out.println("La palabra usa letras que no están entre las generadas o se repite alguna de más");
                    palabraValida = false;
                    break;
                }
            }
        }

        return palabraValida;
    }
}