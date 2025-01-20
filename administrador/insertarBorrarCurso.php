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
if (!isset($_SESSION['nombre_usuario']) || $_SESSION['rol'] === 'cliente') {
    header('Location: ../index.php');
    exit();
}

$valorCampos = [
    'nombre' => $_POST['nombre'] ?? '',
    'nPlazas' => $_POST['nPlazas'] ?? '',
    'fechaLimite' => $_POST['fechaLimite'] ?? '',
];
$camposValidos = [
    'nombre' => true,
    'nPlazas' => true,
    'fechaLimite' => true,
];

$html;
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $opcionAdmin = $_POST['opcionAdmin'];
    $codigo = obtenerCodigoCurso($_POST['curso'] ?? '')[0];
    if($opcionAdmin === 'insertar'){
        $camposValidos = asignarValidez($camposValidos, $valorCampos, $valorCampos['nPlazas']);
        if(compruebaCampos($camposValidos)){
            ejecutarInserccion($pdo, $valorCampos);
        }
        echo mostrarFormularioInsertar($valorCampos, $camposValidos);
    }else{
        ejecutarEliminar($pdo, $codigo);
    }
}else{
    $opcionSeleccionada = $_GET['modificar'];
    $html = ($opcionSeleccionada === 'insertar') ? mostrarFormularioInsertar($valorCampos, $camposValidos) : mostrarFormularioEliminar($pdo);
    echo $html;
}

function ejecutarInserccion($pdo, $valorCampos){
    $mensaje = '<p class=';
    $mensaje .= insertarCurso($pdo, $valorCampos) ? '"mensajeBueno">Insercción realizada con éxito' : '"mensaje">No se ha podido realizar la insercción';
    $mensaje .= '</p>';
    $_SESSION['mensaje'] = $mensaje;
    header('Location: ../index.php');
    exit();
}

function ejecutarEliminar($pdo, $codigo){
    $mensaje = '<p class=';
    $mensaje .= eliminarCurso($pdo, $codigo) ? '"mensajeBueno">Eliminación realizada con éxito' : '"mensaje">No se ha podido realizar la Eliminación';
    $mensaje .= '</p>';
    $_SESSION['mensaje'] = $mensaje;
    header('Location: ../index.php');
    exit();
}
 
function mostrarFormularioInsertar($valorCampos, $camposValidos){
    $html = generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    $html .= '<main><form action="insertarBorrarCurso.php" method="post" class="formulario">';
    $html .= '<br><h2>DATOS NUEVO CURSO</h2><br>';
    $html .= generaHidden('opcionAdmin', 'insertar');
    $html .= generaTexto('nombre', 'Nombre del curso', $valorCampos['nombre'], $camposValidos['nombre'], 'Inserte nombre del curso');
    $html .= generaCampoNumerico('nPlazas', 'Número de plazas', $valorCampos['nPlazas'], $camposValidos['nPlazas'], 'Inserte número de plazas', ' máx plazas 25');
    $html .= generaDate('fechaLimite', 'Fecha límite inscripción',  $camposValidos['fechaLimite'], $valorCampos['fechaLimite'], '');
    $html .= generaSubmit();
    $html .= '</form></main>';
    $html .= generaFooter();
    return $html;
}

function mostrarFormularioEliminar($pdo){
    $html = generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    $html .= '<main><form action="insertarBorrarCurso.php" method="post" class="formulario">';
    $html .= '<br><h2>ELIMINAR CURSO</h2><br>';
    $html .= generaHidden('opcionAdmin', 'eliminar');
    $html .= generaSelectBd($pdo);
    $html .= generaSubmit();
    $html .= '</form></main>';
    $html .= generaFooter();
    return $html;
}

function compruebaCampos($camposValidos) {
    return !in_array(false, $camposValidos, true);
}

function obtenerCodigoCurso($codigoCurso){
    return explode("-", $codigoCurso);
}

function asignarValidez($valoresValidos, $valorCampos, $plazas){
    $valoresValidos['nombre'] = !empty($valorCampos['nombre']);
    $valoresValidos['estado'] = !empty($valorCampos['estado']);
    $valoresValidos['nPlazas'] = !empty($valorCampos['nPlazas']) && compruebaPlazas((int)$plazas);
    $valoresValidos['fechaLimite'] = !empty($valorCampos['fechaLimite']);
    return $valoresValidos;
}

function compruebaPlazas($plazas){
    return 0<$plazas && $plazas<=25;
}

?>
