<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Paraulogic Web</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .letras { font-size: 20px; margin: 5px; display: inline-block; }
        .central { color: red; font-weight: bold; }
    </style>
</head>
<body>
<h1>Paraulogic</h1>

<h3>Letras:</h3>
<div>
    <c:forEach var="l" items="${letras}">
        <span class="letras ${l.central ? 'central' : ''}">${l.character}</span>
    </c:forEach>
</div>

<form method="post" action="GameController">
    <input type="text" name="palabra" required placeholder="Escribe una palabra" />
    <button type="submit">Enviar</button>
</form>

<h3 style="color:darkblue;">${mensaje}</h3>

<h3>Puntuación: ${puntos}</h3>

<h3>Palabras encontradas:</h3>
<ul>
    <c:forEach var="p" items="${palabras}">
        <li>${p}</li>
    </c:forEach>
</ul>
</body>
</html>