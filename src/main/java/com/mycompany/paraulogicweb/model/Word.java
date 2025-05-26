package com.mycompany.paraulogicweb.model;

public class Word {
    private String texto;

    public Word(String text) {
        this.texto = text;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}