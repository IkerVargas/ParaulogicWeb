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

    public boolean agregarPalabra(String palabra) {
        // Normalizar palabra (por ejemplo, convertir a minúsculas)
        String palabraNormalizada = palabra.toLowerCase();

        // Verificar si ya fue encontrada
        for (Word w : palabrasEncontradas) {
            if (w.getTexto().equals(palabraNormalizada)) {
                return false;
            }
        }

        // Calcular y agregar puntos
        int puntos = calcularPuntos(palabraNormalizada);
        puntuacion.agregarPuntos(puntos);

        // Agregar la palabra a la lista
        palabrasEncontradas.add(new Word(palabraNormalizada));

        return true;
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

    private boolean sePuedeFormar(String palabra) {
        // obtenemos todas las letras válidas como un String para fácil búsqueda
        String letrasValidas = getTodasLetras().toUpperCase();

        // recorremos cada letra de la palabra y comprobamos si está en las letras válidas
        for (char c : palabra.toUpperCase().toCharArray()) {
            if (letrasValidas.indexOf(c) == -1) {
                // letra no está entre las válidas, palabra inválida
                return false;
            }
        }

        // todas las letras están dentro de las válidas, palabra válida
        return true;
    }


    public String getTodasLetras() {
        StringBuilder letrasStr = new StringBuilder();
        for (Letter letra : this.letras) {
            letrasStr.append(letra.getCaracter());
        }
        return letrasStr.toString();
    }

    //calcula los puntos depende del numero de letras de la palabra
    public int calcularPuntos(String palabra) {
        int puntos = 0;
        int longitud = palabra.length();

        switch (longitud) {
            case 3:
                puntos = 1;
                break;
            case 4:
                puntos = 2;
                break;
            case 5:
                puntos = 3;
                break;
            case 6:
                puntos = 5;
                break;
            case 7:
                puntos = 10;
                break;
            default:
                puntos = 0;
                break;
        }
        return puntos;
    }


}