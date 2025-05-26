package com.mycompany.paraulogicweb.controller;

import com.mycompany.paraulogicweb.model.*;
import com.mycompany.paraulogicweb.utils.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/GameController")
public class GameController extends HttpServlet {
    private Game game;
    private Letter letraCentral;
    private final Logger _log = Logger.getLogger(GameController.class.getName());

    @Override
    public void init() {
        //generar las letras del juego
        List<Letter> letras = GameUtils.genararLetras();
        this.game = new Game(letras);

        //buscar la letra central dentro de la lista de letras
        for (Letter letra : letras) {
            if (letra.isEsCentral()) {
                this.letraCentral = letra; //guarda la letra central
                System.out.println("Letra central: " + letra.getCaracter());
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //manda las letras al jsp
        request.setAttribute("letras", game.getLetras());

        //log para ver las letras por consola
        _log.info("Letras generadas: " + game.getLetras().stream().map(l -> String.valueOf(l.getCaracter())).toList());

        //manda los puntos y las palabras encontradas al jsp
        request.setAttribute("puntos", game.getPuntuacion().getPuntosTotales());
        request.setAttribute("palabras", game.getPalabrasEncontradas());

        //redirige al index
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "pagina no encontrada");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //coge la palabra escrita
        String strPalabra = request.getParameter("palabra");
        Word palabra = new Word(strPalabra);
        String infoPalabra = "";

        //comprueba si la palabra es valida
        if (!WorldValidator.validarPalabra(palabra, letraCentral, game.getTodasLetras())) {
            infoPalabra = "Palabra no valida";
        } else {
            //intenta agregar la palabra al juego
            boolean palabraAgregada = game.agregarPalabra(strPalabra);
            if (palabraAgregada) {
                infoPalabra = "Palabra encontrada: " + strPalabra;
            } else {
                infoPalabra = "La palabra ya ha sido encontrada";
            }
        }

        //manda los datos actualizados al index
        request.setAttribute("infoPalabra", infoPalabra);
        request.setAttribute("letras", game.getLetras());
        request.setAttribute("puntos", game.getPuntuacion().getPuntosTotales());
        request.setAttribute("palabras", game.getPalabrasEncontradas());

        //redirige al index para mostrar el resultado
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}