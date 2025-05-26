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

    //genera letras asegurando 2 vocales y 4 consonantes, permitiendo repeticiones
    public static List<Letter> genararLetras() {
        List<String> todasLasPalabras = wordDAO.obtenerTodasLasPalabras();

        //selecciona una palabra base aleatoria con longitud >= 3
        String palabraBase;
        do {
            palabraBase = todasLasPalabras.get(random.nextInt(todasLasPalabras.size()));
        } while (palabraBase.length() < 3);
        System.out.println("Palabra base seleccionada: " + palabraBase);

        // Letras de la palabra base sin repetir
        List<Character> letrasBase = new ArrayList<>();
        for (char c : palabraBase.toCharArray()) {
            if (!letrasBase.contains(c)) {
                letrasBase.add(c);
            }
        }

        // Selecciona la letra central aleatoriamente dentro de letrasBase
        char letraCentral = letrasBase.get(random.nextInt(letrasBase.size()));

        List<Letter> letras = new ArrayList<>();

        // Añade la letra central primero, marcada como central
        letras.add(new Letter(letraCentral, true));

        // Añade las demás letras de la palabra base excepto la central y sin superar 7 letras en total
        for (char c : letrasBase) {
            if (c != letraCentral && letras.size() < 7) {
                letras.add(new Letter(c, false));
            }
        }

        // Completa con letras de palabras que contienen la letra central, sin repetir letras ya añadidas
        List<Character> letrasNoCentral = new ArrayList<>();
        for (String palabra : todasLasPalabras) {
            if (palabra.contains(String.valueOf(letraCentral)) && palabra.length() >= 3) {
                for (char c : palabra.toCharArray()) {
                    if (!letrasNoCentral.contains(c) && !contieneCaracter(letras, c)) {
                        letrasNoCentral.add(c);
                    }
                }
            }
        }

        Collections.shuffle(letrasNoCentral);

        // Añade letras extras hasta completar 7 letras
        for (char c : letrasNoCentral) {
            if (letras.size() >= 7) break;
            letras.add(new Letter(c, false));
        }

        // Asegura al menos 2 vocales y 4 consonantes (incluyendo la central)
        int contarVocales = 0;
        for (Letter l : letras) {
            if (esVocal(l.getCaracter())) {
                contarVocales++;
            }
        }

        int contarConsonantes = letras.size() - contarVocales;

        // Añade vocales si hacen falta
        while (contarVocales < 2 && letras.size() < 7) {
            char vocal = VOCALES[random.nextInt(VOCALES.length)];
            if (!contieneCaracter(letras, vocal)) {
                letras.add(new Letter(vocal, false));
                contarVocales++;
            }
        }

        // Añade consonantes si hacen falta
        while (contarConsonantes < 4 && letras.size() < 7) {
            char consonante = CONSONANTES[random.nextInt(CONSONANTES.length)];
            if (!contieneCaracter(letras, consonante)) {
                letras.add(new Letter(consonante, false));
                contarConsonantes++;
            }
        }

        // Completa con letras aleatorias si no llegamos a 7
        while (letras.size() < 7) {
            char c = random.nextBoolean() ? VOCALES[random.nextInt(VOCALES.length)] : CONSONANTES[random.nextInt(CONSONANTES.length)];
            if (!contieneCaracter(letras, c)) {
                letras.add(new Letter(c, false));
            }
        }

        // Finalmente mezcla todas las letras para que la central no siempre esté al principio
        Collections.shuffle(letras);

        // Asegúrate de que la letra central esté marcada correctamente:
        for (int i = 0; i < letras.size(); i++) {
            Letter l = letras.get(i);
            if (l.getCaracter() == letraCentral) {
                letras.set(i, new Letter(letraCentral, true));
            } else {
                letras.set(i, new Letter(l.getCaracter(), false));
            }
        }

        System.out.println("Letras generadas: " + letras.stream().map(l -> String.valueOf(l.getCaracter())).toList());

        return letras;
    }


    private static boolean contieneCaracter(List<Letter> lista, char c) {
        for (Letter letra : lista) {
            if (letra.getCaracter() == c) {
                return true;
            }
        }
        return false;
    }

    private static boolean esVocal(char c) {
        c = Character.toLowerCase(c);
        for (char vocal : VOCALES) {
            if (c == vocal) return true;
        }
        return false;
    }


}