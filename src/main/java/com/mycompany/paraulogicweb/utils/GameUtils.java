package com.mycompany.paraulogicweb.utils;

import com.mycompany.paraulogicweb.daos.WordDAO;
import com.mycompany.paraulogicweb.model.Letter;
import java.util.*;

public class GameUtils {
    private static final Random random = new Random();
    private static final WordDAO wordDAO = new WordDAO();
    private static final char[] VOCALES = {'a', 'e', 'i', 'o', 'u'};
    private static final char[] CONSONANTES = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};

    public static List<Letter> genararLetras() {
        List<String> palabras = wordDAO.obtenerTodasLasPalabras();
        List<Letter> letras = new ArrayList<>();

        //elige una palabra random de la lista de palabras
        String palabraBase = palabras.get(random.nextInt(palabras.size()));
        System.out.println("Palabra base: " + palabraBase);

        //mete las letras de la palabra base en una lista sin repetir
        List<Character> letrasBase = new ArrayList<>();
        for (char c : palabraBase.toCharArray()) {
            if (!letrasBase.contains(c)){
                letrasBase.add(c);
            }
        }

        //elige una letra random de la palabra base como la letra central
        char letraCentral = letrasBase.get(random.nextInt(letrasBase.size()));
        letras.add(new Letter(letraCentral, true));

        //crea una lista de mas letras que tienen palabras con la letra central
        List<Character> masLetras = new ArrayList<>();
        for (String palabra : palabras) {
            //solo coge palabras que tengan la letra central y minimo 3 letras
            if (palabra.indexOf(letraCentral) != -1 && palabra.length() >= 3) {
                //mete sus letras si no estan ya en la lista
                for (char c : palabra.toCharArray()) {
                    if (!contiene(letras, c) && !masLetras.contains(c)) {
                        masLetras.add(c);
                    }
                }
            }
        }

        //añade letras hasta tener 7 como maximo
        for (char c : masLetras) {
            if (letras.size() < 7 && !contiene(letras, c)) {
                letras.add(new Letter(c, false));
            }
        }

        //se asegura de tener minimo 2 vocales
        int vocales = contarVocales(letras);
        for (int i = 0; i < VOCALES.length && vocales < 2 && letras.size() < 7; i++) {
            char v = VOCALES[i];
            if (!contiene(letras, v)) {
                letras.add(new Letter(v, false));
                vocales++;
            }
        }

        //se asegura de tener minimo 4 consonantes
        int consonantes = letras.size() - vocales;
        for (int i = 0; i < CONSONANTES.length && consonantes < 4 && letras.size() < 7; i++) {
            char c = CONSONANTES[i];
            if (!contiene(letras, c)) {
                letras.add(new Letter(c, false));
                consonantes++;
            }
        }

        //mezcla el orden de las letras
        Collections.shuffle(letras);

        //vuelve a marcar cual es la letra central
        List<Letter> resultado = new ArrayList<>();
        for (Letter l : letras) {
            resultado.add(new Letter(l.getCaracter(), l.getCaracter() == letraCentral));
        }

        System.out.println("Letras generadas: " + resultado.stream().map(l -> String.valueOf(l.getCaracter())).toList());
        return resultado;
    }

    //mira si la lista de letras ya tiene ese caracter
    private static boolean contiene(List<Letter> lista, char c) {
        boolean contiene = false;
        for (Letter letra : lista)
            if (letra.getCaracter() == c) {
                contiene = true;
            }
        return contiene;
    }

    //cuenta cuantas vocales hay en la lista de letras
    private static int contarVocales(List<Letter> letras) {
        int vocales = 0;
        for (Letter letra : letras)
            for (char vocal : VOCALES)
                if (Character.toLowerCase(letra.getCaracter()) == vocal)
                    vocales++;
        return vocales;
    }
}