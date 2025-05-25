package com.mycompany.paraulogicweb.utils;



import com.mycompany.paraulogicweb.daos.*;
import com.mycompany.paraulogicweb.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class GameUtils {
    private static final Random random = new Random();
    private static final WordDAO wordDAO = new WordDAO();
    private static final char[]  VOCALES= {'a', 'e', 'i', 'o', 'u'};
    private static final char[] CONSONANTES = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};

    //genera letras para los hexagonos, asegurando 2 vocales y 4 consonantes, permitiendo repeticiones
    public static List<Letter> genararLetras() {
        List<String> todasLasPalabras = wordDAO.obtenerTodasLasPalabras();

        //selecciona una palabra base aleatoria de la base de datos
        String palabraBase;
        do {
            palabraBase = todasLasPalabras.get(random.nextInt(todasLasPalabras.size()));
        } while (palabraBase.length() < 3);
        System.out.println("Palabra base seleccionada: " + palabraBase);

        //obtiene letras de la palabra base
        List<Character> letrasBase = new ArrayList<>();
        for (char c : palabraBase.toCharArray()) {
            letrasBase.add(c);
        }

        //selecciona la letra central
        char letraCentral = letrasBase.get(random.nextInt(letrasBase.size()));
        List<Letter> letrasHexagonales = new ArrayList<>();
        letrasHexagonales.add(new Letter(letraCentral, true));

        //agregar las letras de la palabra base
        for (char c : letrasBase) {
            if (letrasHexagonales.size() < 7) {
                letrasHexagonales.add(new Letter(c, false));
            }
        }

        //completar con letras de palabras que contengan la letra central
        List<Character> palabras = new ArrayList<>();
        for (String palabra : todasLasPalabras) {
            if (palabra.contains(String.valueOf(letraCentral)) && palabra.length() >= 3) {
                for (char c : palabra.toCharArray()) {
                    if (!palabras.contains(c)) { //evitar duplicar palabras
                        palabras.add(c);
                    }
                }
            }
        }

        Collections.shuffle(palabras);
        while (letrasHexagonales.size() < 7 && !palabras.isEmpty()) {
            letrasHexagonales.add(new Letter(palabras.get(random.nextInt(palabras.size())), false));
        }

        //aseguramos 2 vocales i 4 consonantes
        int contarVocales = (int) letrasHexagonales.stream().map(l -> l.getCaracter()).filter(GameUtils::esVocal).count();
        int contarConsonantes = letrasHexagonales.size() - contarVocales - 1; //-1 por la letra central

        while (contarVocales < 2 && letrasHexagonales.size() < 7) {
            char vocal = VOCALES[random.nextInt(VOCALES.length)];
            letrasHexagonales.add(new Letter(vocal, false));
            contarVocales++;
        }

        while (contarConsonantes < 4 && letrasHexagonales.size() < 7) {
            char consonant = CONSONANTES[random.nextInt(CONSONANTES.length)];
            letrasHexagonales.add(new Letter(consonant, false));
            contarConsonantes++;
        }

        //completar si hace falta con letras aleatorias
        while (letrasHexagonales.size() < 7) {
            char c = random.nextBoolean() ? VOCALES[random.nextInt(VOCALES.length)] : CONSONANTES[random.nextInt(CONSONANTES.length)];
            letrasHexagonales.add(new Letter(c, false));
        }

        System.out.println("Letras generadas con posibles repeticiones: " + letrasHexagonales.stream().map(l -> String.valueOf(l.getCaracter())).toList());
        return letrasHexagonales;
    }

    //comprueba si es una vocal
    private static boolean esVocal(char c) {
        boolean esVocal = false;
        for (char vocal : VOCALES) {
            if (c == vocal) {
                esVocal = true;
            }
        }
        return esVocal;
    }

}