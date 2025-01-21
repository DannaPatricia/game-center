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
                    <h1>Modificar Consola</h1>
                    <input type="hidden" name="idProducto" value = "${id}">
                    <input type="hidden" name="tipoProducto" value = "${tipoProducto}">
                    <div class="input-group">
                        <label for="nombre">Nombre:</label>
                        ${opcionesNombre}
                    </div>
                    <div class="input-group">
                        <label for="potenciaCpu">Potencia CPU:</label>
                        <input type="text" name="potenciaCpu" id="potenciaCpu" value="${potenciaCpu}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="potenciaGpu">Potencia GPU:</label>
                        <input type="text" name="potenciaGpu" id="potenciaGpu" value="${potenciaGpu}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="compania">Compañía:</label>
                        <input type="text" name="compania" id="compania" value="${compania}" required><br>
                    </div>
                    <div class="input-group">
                        <label for="precio">Precio:</label>
                        <input type="number" name="precio" id="precio" value="${precio}" required><br>
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
