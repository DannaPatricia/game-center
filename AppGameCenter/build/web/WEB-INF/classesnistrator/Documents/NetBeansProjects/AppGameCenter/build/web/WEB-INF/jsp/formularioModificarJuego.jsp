<%-- 
    Document   : formularioRegistro
    Created on : 8 ene 2025, 13:04:05
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Game Center</title>
        <link href="style.css" rel="stylesheet">
    </head>
    <body>
        <header>
            <h1 class="titulo">GAME CENTER</h1>
        </header>
        <main>
            <section class="formulario-container">
                <form class="formulario" action="EjecutaOpcionesAdministradorServlet" method="post">
                    <h1>Modificar Juego</h1>
                    <input type="hidden" name="idProducto" value = "${id}">
                    <input type="hidden" name="tipoProducto" value = "${tipoProducto}">
                    <div class="input-group">
                        <label for="nombre">Nombre:</label>
                        <input type="text" name="nombre" id="nombre" value="${nombre}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="plataforma">Plataforma:</label>
                        ${opcionesPlataforma}
                    </div>
                    <div class="input-group">
                        <label for="companiaDesarrolladora">Compañía desarrolladora:</label>
                        <input type="text" name="companiaDesarrolladora" id="companiaDesarrolladora" value="${companiaDesarrolladora}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="genero">Género</label>
                        <input type="text" name="genero" id="genero" value="${genero}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="precio">Precio:</label>
                        <input type="number" name="precio" id="precio" value="${precio}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="puntuacionMetacritic">Puntuación metacritic: </label>
                        <input type="number" name="puntuacionMetacritic" id="puntuacionMetacritic" value="${puntuacionMetacritic}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="unidadesDisponibles">Unidades Disponibles:</label>
                        <input type="number" name="unidadesDisponibles" id="unidadesDisponibles" value="${unidadesDisponibles}" required><br>
                    </div>
                    <button type="submit" class="btn btn-primary" name = "opcionEnviada" value = "modificar">Modificar</button>
                </form>
                <button class="volver" name="opcionUsuario" type="button"><a href="ManejaCatalogos" class = "enlaceVolver">Volver</a></button>    
            </section>
        </main>
        <footer>
            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>
        </footer>
    </body>
</html>
