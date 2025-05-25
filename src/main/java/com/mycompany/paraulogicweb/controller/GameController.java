package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.model.*;
import com.mycompany.paraulogicweb.utils.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/GameController")
public class GameController extends HttpServlet {
    private Game game;
    private Letter letraCentral;

    @Override
    public void init() {
        List<Letter> letras = GameUtils.genararLetras();
        this.game = new Game(letras);

        for (Letter letra : letras) {
            if (letra.isEsCentral()) {
                this.letraCentral = letra;
            }
        }
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("letras", game.getLetras());
        request.setAttribute("puntos", game.getPuntuacion());
        request.setAttribute("palabrasEncontradas", game.getPalabrasEnecontradas());

        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
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

        if(!WorldValidator.validarPalabra(palabra, letraCentral, game.getTodasPalabras())) {
            mensaje = "La palabra no es valida";
        } else if (game.getPalabrasEnecontradas().contains(palabra)) {
            mensaje = "La palabra ya ha sido encontrada";
        } else {
            game.getPalabrasEnecontradas().add(palabra);
            game.getPuntuacion().agregarPuntos(palabra.calcularPuntos());
            mensaje = "Palabra encontrada: " + strPalabra;
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("letras", game.getLetras());
        request.setAttribute("puntos", game.getPuntuacion());
        request.setAttribute("palabrasEncontradas", game.getPalabrasEnecontradas());

        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        dispatcher.forward(request, response);

    }

}
