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
        boolean agregada = true;

        //verifica si ya se ha encomtrado
        for (Word word : palabrasEncontradas) {
            if (word.getTexto().equals(palabra.toLowerCase())) {
                agregada = false;
            }
        }

        palabrasEncontradas.add(new Word(palabra.toLowerCase()));
        int puntos = calcularPuntos(palabra.toLowerCase());
        puntuacion.agregarPuntos(puntos);

        return agregada;
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
        boolean valida = true;
        //obtiene todas las letras validas del juego
        String letrasValidas = getTodasLetras().toLowerCase();

        //recorre cada letra de la palabra para ver si esta entre las que son validas
        for (char c : palabra.toLowerCase().toCharArray()) {
            if (letrasValidas.indexOf(c) == -1) {
                //la letra no esta entre las que son validas
                valida = false;
            }
        }

        return valida;
    }

    public String getTodasLetras() {
        //convierte las letras a un string
        StringBuilder letrasStr = new StringBuilder();
        //recorre cada letra y las concatena
        for (Letter letra : this.letras) {
            letrasStr.append(letra.getCaracter());
        }
        //devuelve el string con todas las letras
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
                if (longitud > 7) {
                    puntos = 10;
                } else {
                    puntos = 0;
                }
                break;
        }
        return puntos;
    }

}