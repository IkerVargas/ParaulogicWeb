package com.mycompany.paraulogicweb.model;

public class Score {
    private int puntosTotales;

    public Score() {
        this.puntosTotales = 0;
    }

    //añade puntos a la puntuacion total
    public void agregarPuntos(int points) {
        this.puntosTotales += points;
    }

    //obtiene la puntuacion total
    public int getPuntosTotales() {
        return puntosTotales;
    }
}