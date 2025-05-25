<%--
  Created by IntelliJ IDEA.
  User: Iker
  Date: 25/05/2025
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ca">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Nou Usuari</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <h1 class="mt-4">Afegir Nou Usuari</h1>

  <form action="GameController" method="POST">
    <div class="mb-3">
      <label for="nombre" class="form-label">LETRAS</label>
      <label for="letras" class="form-label">${letras}</label>
    </div>

    <div class="mb-3">
      <label for="mail" class="form-label">Paraula</label>
      <input type="text" class="form-control" id="palabra" name="palabra" required>
    </div>

    <button type="submit" class="btn btn-primary">Verificar Palabra</button>

    <p>Mensaje: ${mensaje}</p>
    <p>Puntos: ${puntos}</p>
    <p>Palabras : ${palabras}</p>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
