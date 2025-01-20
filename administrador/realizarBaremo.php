<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
    
include '../header.php';
include '../footer.php';
include '../formularios/input.php';
include '../conexionBd.php';
include 'consultasAdmin.php';

$html = '';
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $codigo = obtenerCodigoCurso($_POST['codigoCurso'] ?? '')[0];
    $nPlazas = $_POST['numeroPlazas'] ?? '';
    echo generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    if(obtenerPuntajes($pdo, $codigo, $nPlazas)){
        echo generaFooter();
    }else{
        $_SESSION['mensaje'] = '<p class = "mensaje">La baremación no se ha podido realizar</p>';
        header('Location:../index.php');
        exit();
    }
}else{
    $html = generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    $html .= obtenerCursosCerrados($pdo);
    $html .= generaFooter();
    echo $html;
}


function obtenerCodigoCurso($codigoCurso){
    return explode("-", $codigoCurso);
}
?>
