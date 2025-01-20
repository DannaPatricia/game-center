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

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $btnEnviar = $_POST['btnEnviar'] ?? '';
    $html = generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    if($btnEnviar === 'activarDesactivar'){
        $html .= generaRadioAbreCierra();
    }elseif($btnEnviar === 'listado'){
        $html .= generaOpcionesListado($pdo);
    }elseif($btnEnviar === 'modificarCursos'){
        $html .= generaOpcionesModificar();
    }elseif($btnEnviar === 'baremo'){
        header('Location:realizarBaremo.php');
        exit();
    }elseif($btnEnviar === 'ejecutarAbrirCerrar'){
        $abrirCerrar = $_POST['abrirCerrar'] ?? '';
        $html .= generaSelectAbreCierra($pdo, $abrirCerrar);
    }elseif($btnEnviar === 'asignarNPlazas'){
        $nPlazas = $_POST['nPlazas'] ?? '';
        $nPlazasValido = !empty($nPlazas) && validaPlazas((int)$nPlazas);
        $html .= generaOpcionesNPlazas($pdo, $nPlazas);
        if($nPlazasValido){
            $codigo = obtenerCodigoCurso($_POST['curso'] ?? '')[0];
            if(asignarNPlazas($pdo, $nPlazas, $codigo)){
                $_SESSION['mensaje'] = '<p class = "mensajeBueno">Operación realizada con éxito</p>';
            }else{
                $_SESSION['mensaje'] = '<p class = "mensaje">Operación no se ha podido realizar</p>';
            }
            header('Location: ../index.php');
            exit();
        }
    }
    $html .= generaFooter();
    echo $html;
}else{
    echo generaMenu();
}


function generaMenu(){
    $html = generaHeader('../formularios/logout.php', '../formularios/login.php', '../index.php', '../cursos.php', 'opcionesAdmin.php', '../css/style.css');
    $html .= generaOpcionesMenu();
    $html .= generaFooter();
    return $html;
}

function generaOpcionesMenu(){
    $html = '<main><form action="opcionesAdmin.php" method="post" class="formulario">';
    $html .= '<br><h2>Opciones de administrador</h2><br>';
    $html .= generaOpcion('Abrir/Cerrar curso', 'activarDesactivar');
    $html .= generaOpcion('Mostrar listado de admitidos en un curso', 'listado');
    $html .= generaOpcion('Insertar/Eliminar curso', 'modificarCursos');
    $html .= generaOpcion('Baremación', 'baremo');
    $html .= generaOpcion('Insertar número de plazas', 'asignarNPlazas');
    $html .= '</form></main>';
    return $html;
}


function generaRadioAbreCierra(){
    $html = '<main><form action="opcionesAdmin.php" method="post" class="formulario">';
    $html .= '<br><h2>ELIGE OPCIÓN</h2><br>';
    $html .= generaHidden('opcionAdmin', 'abrirCerrar');
    $html .= generaRadio('abrirCerrar', 'Abrir/Cerrar curso', ['abrir', 'cerrar'], true, 'abrir');
    $html .= generaOpcion("Enviar", "ejecutarAbrirCerrar");
    $html .= '</form></main>';
    return $html;
}

function generaSelectAbreCierra($pdo, $abrirCerrar){
    $html = '<main><form action="ejecutaOpciones.php" method="post" class="formulario">';
    $html .= '<br><h2>'.strtoupper($abrirCerrar).' CURSO</h2><br>';
    $html .= generaHidden('opcionAdmin', 'abrirCerrar');
    $html .= generaHidden('abrirCerrar', $abrirCerrar);
    $html .= generaSelectBd($pdo, ($abrirCerrar === 'cerrar') ? true : false);
    $html .= generaSubmit();
    $html .= '</form></main>';
    return $html;
}

function generaOpcionesListado($pdo){
    $html = '<main><form action="ejecutaOpciones.php" method="post" class="formulario">';
    $html .= '<br><h2>Elige curso</h2><br>';
    $html .= generaHidden('opcionAdmin', 'obtenerListado');
    $html .= generaSelectBd($pdo);
    $html .= generaSubmit();
    $html .= '</form></main>';
    return $html;
}

function generaOpcionesModificar(){
    $html = '<main><form action="insertarBorrarCurso.php" method="get" class="formulario">';
    $html .= '<br><h2>Elige Opcion</h2><br>';
    $html .= generaHidden('opcionAdmin', 'modificar');
    $html .= generaRadio('modificar', 'Insertar/Eliminar curso', ['insertar', 'eliminar'], true, 'insertar');
    $html .= generaSubmit();
    $html .= '</form></main>';
    return $html;
}


function generaOpcionesNPlazas($pdo, $nPlazas){
    $html = '<main><form action="opcionesAdmin.php" method="post" class="formulario">';
    $html .= '<br><h2>ASIGNAR NÚMERO DE PLAZAS</h2><br>';
    $html .= generaHidden('opcionAdmin', 'asignarNPlazas');
    $html .= generaCampoNumerico('nPlazas', 'Inserta número de plazas', $nPlazas, true, 'Inserta número de plazas, mínimo 10 y máximo 30', 'Minimo 10 y máximo 30');
    $html .= generaSelectBd($pdo, true);
    $html .= generaOpcion("Enviar", "asignarNPlazas");
    $html .= '</form></main>';
    return $html;
}

function validaPlazas($plazas){
    return $plazas > 10 && $plazas <= 30;
}

function obtenerCodigoCurso($codigoCurso){
    return explode("-", $codigoCurso);
}
?>
