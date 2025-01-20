<?php
session_start();

function generaHtml($style)
{
    return '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="'.$style.'" media="all">
</head>
<body>';
}

function generaHeader($logout, $login,  $index, $cursos, $opcionesAdministrador, $style)
{
    $html = generaHtml($style);
    $html .= '<header>
    <nav aria-label="gestiona-opciones-usuarios">
        <h1 class = "titulo">Encuentra Cursos</h1>
        <ul>';
    $html .= isset($_SESSION['id_usuario'])
        ? '<li>'.$_SESSION['nombre_usuario'].'</li><li><a href="'.$logout.'">Cerrar sesión</a></li>'
        : '<li><a href="'.$login.'">Iniciar sesión</a></li>';

    $html .= isset($_SESSION['id_usuario']) && isset($_SESSION['rol']) && $_SESSION['rol'] == 'admin'
        ? '<li><a href="'.$opcionesAdministrador.'">Panel de administración</a></li>'
        : '';
    $html .= '</ul>
    </nav>
    <nav aria-label="gestiona-paginas">
        <ul>
            <li><a href="'.$index.'">Inicio</a></li>
            <li><a href="'.$cursos.'">Cursos activos</a></li>
            <li><a href="'.$cursos.'">Inscripción</a></li>
        </ul>
    </nav>
</header>';
    return $html;
}
