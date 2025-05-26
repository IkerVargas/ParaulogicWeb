package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.model.*;
import com.mycompany.paraulogicweb.utils.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/GameController")
public class GameController extends HttpServlet {
    private Game game;
    private Letter letraCentral;
    private final Logger _log = Logger.getLogger(GameController.class.getName());

    @Override
    public void init() {
        List<Letter> letras = GameUtils.genararLetras();
        this.game = new Game(letras);
        Collections.shuffle(letras);

        for (Letter letra : letras) {
            if (letra.isEsCentral()) {
                this.letraCentral = letra;
            }
        }
        if (this.letraCentral == null) {
            System.out.println("Letra central NO asignada correctamente.");
        } else {
            System.out.println("Letra central asignada: " + letraCentral.getCaracter());
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("letras", game.getLetras());
        _log.info("Letras generadas: " + game.getLetras().stream().map(l -> String.valueOf(l.getCaracter())).toList());
        request.setAttribute("puntos", game.getPuntuacion());
        request.setAttribute("palabras", game.getPalabrasEncontradas());

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Game page not found");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strPalabra = request.getParameter("palabra").toUpperCase();
        Word palabra = new Word(strPalabra);
        String mensaje = "";

        // Usamos el método getLetrasComoString para obtener las letras válidas
        String letrasValidas = game.getTodasLetras();

        if (!WorldValidator.validarPalabra(palabra, letraCentral, letrasValidas)) {
            mensaje = "La palabra no es válida";
        } else if (game.getPalabrasEncontradas().contains(palabra)) {
            mensaje = "La palabra ya ha sido encontrada";
        } else {
            game.getPalabrasEncontradas().add(palabra);
            game.getPuntuacion().agregarPuntos(palabra.calcularPuntos());
            mensaje = "Palabra encontrada: " + strPalabra;
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("letras", game.getLetras());
        request.setAttribute("puntos", game.getPuntuacion());
        request.setAttribute("palabras", game.getPalabrasEncontradas());

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

}