package com.mycompany.paraulogicweb.model;



import com.mycompany.paraulogicweb.daos.WordDAO;

import java.util.*;

public class Game {
    private List<Letter> letras;
    private List<Word> palabrasEncontradas;
    private Score puntuacion;
    private List<String> palabrasValidas;
    private WordDAO wordDAO;

    public Game(List<Letter> letras) {
        this.wordDAO = new WordDAO();
        this.letras = letras;
        this.palabrasEncontradas = new ArrayList<>();
        this.puntuacion = new Score();
        this.palabrasValidas = filtrarPalabrasValidas();
        System.out.println("Letras generadas: " + letras.stream().map(l -> String.valueOf(l.getCaracter())).toList());
        System.out.println("Palabras validas: " + palabrasValidas);
    }

    public List<Letter> getLetras() {
        return letras;
    }

    public List<Word> getPalabrasEncontradas() {
        return palabrasEncontradas;
    }

    public Score getPuntuacion() {
        return puntuacion;
    }

    //filtra las palabras validas a partir de las letras disponibles y la letra central
    private List<String> filtrarPalabrasValidas() {
        List<String> todasLasLetras = wordDAO.obtenerTodasLasPalabras();
        List<String> filtradas = new ArrayList<>();
        char letraCentral = letras.get(0).getCaracter();

        for (String palabra : todasLasLetras) {
            if (palabra.contains(String.valueOf(letraCentral)) && sePuedeFormar(palabra)) {
                filtradas.add(palabra);
            }
        }
        return filtradas;
    }

    // verifica si la palabra puede ser formada con las letras disponibles, teniendo en cuenta repeticiones
    private boolean sePuedeFormar(String palabra) {
        boolean palabraValida = true;
        Map<Character, Integer> cantidadDeLetras = new HashMap<>();
        for (Letter letra : letras) {
            char c = letra.getCaracter();
            cantidadDeLetras.put(c, cantidadDeLetras.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> cantidadUsadas = new HashMap<>();
        for (char c : palabra.toCharArray()) {
            cantidadUsadas.put(c, cantidadUsadas.getOrDefault(c, 0) + 1);
            if (!cantidadDeLetras.containsKey(c) || cantidadUsadas.get(c) > cantidadDeLetras.get(c)) {
                palabraValida = false;
            }
        }
        return palabraValida;
    }

    public String getTodasLetras() {
        StringBuilder letrasStr = new StringBuilder();
        for (Letter letra : this.letras) {
            letrasStr.append(letra.getCaracter());
        }
        return letrasStr.toString();
    }

}