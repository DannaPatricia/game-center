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
            <section class="login-container">
                <h2>Resgistrarse</h2>
                <c:if test="${not empty errorEmail}">
                    <div class = "mensajeError">${errorEmail}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class = "mensajeError">${errorMessage}</div>
                </c:if>
                <form class="formulario" action="ManejaLoginResgistroServlet" method="post">
                    <input type="hidden"  name = "enviado" value = "verTodo">
                    <div class="input-group">
                        <label for="nombre">Nombre</label>
                        <input name="nombre" type="text" placeholder="Ingresa tu nombre" value = "${nombre}">
                    </div>
                    <div class="input-group">
                        <label for="apellidos">Apellidos</label>
                        <input  name="apellidos" type="text" placeholder="Ingresa tus apellidos" value = "${apellidos}">
                    </div>
                    <div class="input-group">
                        <label for="email">Email</label>
                        <input  name="email" type="text" placeholder="Ingresa tus email" value = "${email}">
                    </div>
                    <div class="input-group">
                        <label for="nombreUsuario">Nombre de usuario</label>
                        <input  name="nombreUsuario" type="text" placeholder="Ingresa tu nombre de usuario" value = "${nombreUsuario}">
                    </div>
                    <div class="input-group">
                        <label for="clave">Contraseña</label>
                        <input  name="clave" type="password" placeholder="Ingresa tu contraseña">
                    </div>
                    <button class="btn btn-primary" name="opcionUsuario" type="submit" value="registrarse">Resgistrarse</button>    
                </form>
            </section>
        </main>
        <footer>
            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>
        </footer>
    </body>
</html>
