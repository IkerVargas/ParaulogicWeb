package com.mycompany.paraulogicweb.model;

public class Word {
    private String texto;

    public Word(String text) {
        this.texto = text;
    }

    public String getTexto() {
        return texto;
    }

    //calcula los puntos depende del numero de letras de la palabra
    public int calcularPuntos() {
        int puntos;
        switch (texto.length()) {
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