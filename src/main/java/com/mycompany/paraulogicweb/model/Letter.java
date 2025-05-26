package com.mycompany.paraulogicweb.model;

public class Letter {
    private char caracter;
    private boolean esCentral;

    public Letter(char character, boolean isCentral) {
        this.caracter = character;
        this.esCentral = isCentral;
    }

    public char getCaracter() {
        return caracter;
    }

    public boolean isEsCentral() {
        return esCentral;
    }

    @Override
    public String toString() {
        return String.valueOf(caracter);
    }
}