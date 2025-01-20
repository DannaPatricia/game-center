<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include '../header.php';
include '../footer.php';
include '../formularios/input.php';
include '../conexionBd.php';
include 'consultasAdmin.php';

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if (!isset($_SESSION['nombre_usuario']) && $_SESSION['rol'] === 'cliente') {
    header('Location: ../index.php');
    exit();
}

$booleano;
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $opcionAdmin = $_POST['opcionAdmin'] ?? '';
    $codigo = obtenerCodigoCurso($_POST['curso'] ?? '')[0];
    $curso = obtenerCodigoCurso($_POST['curso'] ?? '')[1];
    if($opcionAdmin === 'abrirCerrar'){
        $abrirCerrar = $_POST['abrirCerrar'] ?? '';
        ejecutarCambioDEstado($pdo, $codigo, $abrirCerrar);
    }elseif($opcionAdmin === 'obtenerListado'){
        echo mostrarListado($pdo, $codigo, $curso);
    }
}

function ejecutarCambioDEstado($pdo, $codigo, $abrirCerrar){
    $valor = ($abrirCerrar === 'abrir') ? 1 : 0;
    $mensaje = '<p class=';
    $mensaje .= (activarDesactivarCurso($pdo, $valor, $codigo)) ? '"mensajeBueno">Se ha realizado la acción' : '"mensaje">No se ha podido realizar la acción, seleccione un curso válido';
    $mensaje .= '</p>';
    $_SESSION['mensaje'] = $mensaje;
    header('Location: ../index.php');
    exit();
}

function obtenerCodigoCurso($codigoCurso){
    return explode("-", $codigoCurso);
}

function mostrarListado ($pdo, $codigoCurso, $nombreCurso){
    $html = generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    $html .= '<br><h2>ADMITIDOS EN '.strtoupper($nombreCurso).'</h2>';
    $html .= obtenerListado($pdo, $codigoCurso);
    $html .= generaFooter();
    return $html;
}
?>
