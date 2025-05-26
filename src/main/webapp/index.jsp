<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%if (request.getAttribute("letras") == null) {
        response.sendRedirect("GameController");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Paraulogic We</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        .header-title {
            text-align: center;
            margin-top: 40px;
            margin-bottom: 30px;
            font-weight: 700;
        }
        .letters-box {
            border-radius: 10px;
            padding: 20px;
            font-size: 2.5rem;
            font-weight: 700;
            text-align: center;
            margin-bottom: 40px;
            user-select: none;
        }
        form {
            max-width: 480px;
            margin: 0 auto 60px auto;
            padding: 25px 30px;
            border-radius: 10px;
        }
        .btn-primary {
            width: 100%;
            font-weight: 600;
        }
        .info-text {
            text-align: center;
            margin-top: 20px;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>

    <h1 class="header-title">Paraulogic Web</h1>

    <div class="letters-box" aria-label="Letras generadas">
        <label for="letras">Letras: </label>
        ${letras}
    </div>

    <form action="GameController" method="POST" novalidate>

        <div class="mb-3">
            <label for="palabra" class="form-label">Palabra</label>
            <input type="text" class="form-control" id="palabra" name="palabra" required autocomplete="off" autofocus />
        </div>

        <button type="submit" class="btn btn-primary">Verificar Palabra</button>

        <div class="info-text">
            <p>Missatge: ${mensaje}</p>
            <p>Punts: ${puntos}</p>
            <p>Palabras Encontradas: ${palabras}</p>
        </div>
    </form>

</body>
</html>