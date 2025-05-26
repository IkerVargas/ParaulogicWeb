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
        //generar las letras
        List<Letter> letras = GameUtils.genararLetras();
        this.game = new Game(letras);
        Collections.shuffle(letras);

        //buscar una letra central entre las letras generadas
        for (Letter letra : letras) {
            if (letra.isEsCentral()) {
                this.letraCentral = letra; //asigna la letra centrañ
                System.out.println("Letra central: " + letra.getCaracter());
            }
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("letras", game.getLetras());
        _log.info("Letras generadas: " + game.getLetras().stream().map(l -> String.valueOf(l.getCaracter())).toList());
        request.setAttribute("puntos", game.getPuntuacion().getPuntosTotales());
        request.setAttribute("palabras", game.getPalabrasEncontradas());

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "pagina no encontrada");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //obtiene la palabra escrita
        String strPalabra = request.getParameter("palabra");
        Word palabra = new Word(strPalabra);
        String mensaje = "";

        String letrasValidas = game.getTodasLetras();

        //validar que las palabras sean validas
        if (!WorldValidator.validarPalabra(palabra, letraCentral, game.getTodasLetras())) {
            mensaje = "La palabra no es válida";
        } else {
            boolean palabraAgregada = game.agregarPalabra(strPalabra);
            if (!palabraAgregada) {
                mensaje = "La palabra ya ha sido encontrada";
            } else {
                mensaje = "Palabra encontrada: " + strPalabra;
            }
        }


        //asigna valores para mostrarlos en el index
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("letras", game.getLetras());
        request.setAttribute("puntos", game.getPuntuacion().getPuntosTotales());
        request.setAttribute("palabras", game.getPalabrasEncontradas());

        //reeenvia los valores para actualizar la vista con los nuevos datos
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

}