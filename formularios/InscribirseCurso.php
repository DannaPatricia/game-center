<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include '../header.php';
include '../footer.php';
include '../conexionBd.php';
include 'input.php';
include 'consultasUsuario.php';

$valorCampos = [
    'fechaNacimiento' => $_POST['fechaNacimiento'] ?? '',
    'codigoCentro' => $_POST['codigoCentro'] ?? '',
    'coordinadorTIC' => $_POST['coordinadorTIC'] ?? '',
    'grupoTIC' => $_POST['grupoTIC'] ?? '',
    'nombreGrupo' => $_POST['nombreGrupo'] ?? '',
    'programaBilingue' => $_POST['programaBilingue'] ?? '',
    'cargo' => $_POST['cargo'] ?? '',
    'nombreCargo' => $_POST['nombreCargo'] ?? '',
    'situacion' => $_POST['situacion'] ?? '',
    'especialidad' => $_POST['especialidad'] ?? '',
];
$camposValidos = [
    'fechaNacimiento' => true,
    'codigoCentro' => true,
    'coordinadorTIC' => true,
    'grupoTIC' => true,
    'nombreGrupo' => true,
    'programaBilingue' => true,
    'cargo' => true,
    'nombreCargo' => true,
    'situacion' => true,
    'especialidad' => true,
];
$mensaje = '';
$codigoCurso = '';
$nombreCurso = '';
$cargoJefe = 'jefe de departamento';
$cargos = ['secretario', 'director', 'jefe de estudios'];
if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if (!isset($_SESSION['nombre_usuario'])) {
    header('Location: login.php');
    exit();
}
if ($_SESSION['rol'] === 'admin') {
    $_SESSION['mensaje'] = '<p class = "mensaje">El administrador no puede realizar solicitudes</p>';
    header('Location: ../index.php');
    exit();
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $codigoCurso = $_POST['codigoCurso'] ?? '';
    $nombreCurso = $_POST['nombreCurso'] ?? '';
    $camposValidos = asignarValidez($camposValidos, $valorCampos);
    if (compruebaCampos($camposValidos)) {
        if(insertarSolicitante($pdo, $_SESSION['dni'], $_SESSION['nombre'], $_SESSION['apellidos'], $_SESSION['email'], $_SESSION['telefono'], $valorCampos)
        && guardarSolicitud($pdo, $_SESSION['dni'], $codigoCurso)){
            sumarPuntos($pdo, $cargos, $cargoJefe);
            echo generaResguardoHTML($valorCampos, $_SESSION['dni'], $_SESSION['nombre'], $_SESSION['apellidos'], $_SESSION['email'], $_SESSION['telefono']);
        }else{
            $mensaje = '<p class="mensaje">No se ha podido realizar la solicitud</p>';
            echo generaFormularioHTML($valorCampos, $camposValidos, $codigoCurso, $nombreCurso, $mensaje);
        }
    } else {
        $mensaje = '<p class="mensaje">Por favor, completa todos los campos</p>';
        echo generaFormularioHTML($valorCampos, $camposValidos, $codigoCurso, $nombreCurso, $mensaje);
    }
}else{
    if(!fechaValida(date('Y-m-d'), $_GET['plazoInscripcion']) || ($_GET['numeroPlazas'] <= '0')){
        $_SESSION['mensaje'] = '<p class="mensaje">El plazo de inscripcion ha acabado</p>';
        header('Location: ../index.php');
        exit();
    }elseif(buscarSolicitante($pdo, $_SESSION['dni'])){
        $codigoNombreCurso = codigoNombreCurso($_GET['btnEnviar']);
        $codigoCurso = $codigoNombreCurso[0];
        guardarSolicitud($pdo, $_SESSION['dni'], $codigoCurso);
        restarNPlazas($pdo, $codigoCurso);
        $_SESSION['mensaje'] = '<p class="mensajeBueno">Se ha enviado la solicitud</p>';
        header('Location: ../index.php');
        exit();
    }
    $codigoNombreCurso = codigoNombreCurso($_GET['btnEnviar']);
    $codigoCurso = $codigoNombreCurso[0];
    $nombreCurso = $codigoNombreCurso[1];
    echo generaFormularioHTML($valorCampos, $camposValidos, $codigoCurso, $nombreCurso, $mensaje);
}

function codigoNombreCurso($codigoNombre){
    return explode('-', $codigoNombre);
}

function compruebaCampos($camposValidos) {
    return !in_array(false, $camposValidos, true);
}

function generaFormularioHTML($valorCampos, $camposValidos, $codigoCurso, $nombreCurso, $mensaje){
    $html = generaHeader('logout.php', 'login.php', '../index.php', '../cursos.php', '../administrador/opcionesAdmin.php', '../css/style.css');
    $html .= $mensaje;
    $html .= generaFormulario($valorCampos, $camposValidos, $codigoCurso, $nombreCurso);
    $html .= generaFooter();
    return $html;
}

function generaResguardoHTML($valorCampos, $dni, $nombre, $apellidos, $correo, $telefono){
    $html = generaHeader('logout.php', 'login.php', '../index.php', '../cursos.php', '../administrador/opcionesAdmin.php', '../css/style.css');
    $html .= '<p class="mensajeBueno">Solicitud encviada correctamente</p>';
    $html .= generaResguardo($valorCampos, $dni, $nombre, $apellidos, $correo, $telefono);
    $html .= generaFooter();
    return $html;
}

function asignarValidez($camposValidos, $valorCampos){
    $camposValidos['fechaNacimiento'] = !empty($valorCampos['fechaNacimiento']);
    $camposValidos['codigoCentro'] = !empty($valorCampos['codigoCentro']);
    $camposValidos['coordinadorTIC'] = !empty($valorCampos['coordinadorTIC']);
    $camposValidos['grupoTIC'] = !empty($valorCampos['grupoTIC']);
    $camposValidos['nombreGrupo'] = !empty($valorCampos['nombreGrupo']);
    $camposValidos['programaBilingue'] = !empty($valorCampos['programaBilingue']);
    $camposValidos['cargo'] = !empty($valorCampos['cargo']);
    $camposValidos['nombreCargo'] = ($valorCampos['cargo'] === 'si') ? !empty($valorCampos['nombreCargo']) : true;
    $camposValidos['situacion'] = !empty($valorCampos['situacion']);
    $camposValidos['especialidad'] = !empty($valorCampos['especialidad']);
    return $camposValidos;
}

function generaFormulario($valores, $validos, $codigoCurso, $nombreCurso) {
    $html = '<main><form action="InscribirseCurso.php" method="post" class="formulario">';
    $html .= '<h2>Inscripcion a '.$nombreCurso.'</h2>';
    $html .= generaHidden('codigoCurso', $codigoCurso);
    $html .= generaHidden('nombreCurso', $nombreCurso);
    $html .= generaDate('fechaNacimiento', 'Fecha de nacimiento', $validos['fechaNacimiento'], $valores['fechaNacimiento'], '2005-01-01');
    $html .= generaTexto('codigoCentro', 'Código centro', $valores['codigoCentro'], $validos['codigoCentro'], 'Inserte el cópdigo del centro');
    $html .= generaRadio('coordinadorTIC', '¿Es coordinador TIC?', ['si', 'no'], $validos['coordinadorTIC'], $valores['coordinadorTIC']);
    $html .= generaRadio('grupoTIC', '¿Pertecene a un grupo TIC?', ['si', 'no'], $validos['grupoTIC'], $valores['grupoTIC']);
    $html .= generaTexto('nombreGrupo', 'NOmbre del grupo TIC', $valores['nombreGrupo'], $validos['nombreGrupo'], 'Inserte nombre del grupo');
    $html .= generaRadio('programaBilingue', '¿Sabe programación bilingüe?', ['si', 'no'], $validos['programaBilingue'], $valores['programaBilingue']);
    $html .= generaRadio('cargo', '¿Tiene algún cargo TIC?', ['si', 'no'], $validos['cargo'], $valores['cargo']);
    $html .= ($valores['cargo'] === 'si')? generaTexto('nombreCargo', 'NOmbre del cargo TIC', $valores['nombreCargo'], $validos['nombreCargo'], 'Inserte nombre del cargo') : '';
    $html .= generaRadio('situacion', '¿Se encuentra activo?', ['si', 'no'], $validos['situacion'], $valores['situacion']);
    $html .= generaTexto('especialidad', 'Especialidad', $valores['especialidad'], $validos['especialidad'], 'Inserte su especialidad');
    $html .= generaSubmit();
    $html .= '</form></main>';
    return $html;
}

function generaResguardo($valores, $dni, $nombre, $apellidos, $correo, $telefono){
    $html = '<main><form action="InscribirseCurso.php" method="post" class="formulario">';
    $html .= '<h2>Datos del solicitante</h2>';
    $html .= generaParrafo('DNI', $dni);
    $html .= generaParrafo('NOmbre', $nombre);
    $html .= generaParrafo('Apellidos', $apellidos);
    $html .= generaParrafo('Fecha de nacimiento', $valores['fechaNacimiento']);
    $html .= generaParrafo('Correo electronico', $correo);
    $html .= generaParrafo('Telefono', $telefono);
    $html .= generaParrafo('codigoCentro', $valores['codigoCentro']);
    $html .= generaParrafo('coordinadorTIC', $valores['coordinadorTIC']);
    $html .= generaParrafo('grupoTIC', $valores['grupoTIC']);
    $html .= generaParrafo('nombreGrupo', $valores['nombreGrupo']);
    $html .= generaParrafo('programaBilingue', $valores['programaBilingue']);
    $html .= generaParrafo('cargo', $valores['cargo']);
    $html .= generaParrafo('nombreCargo', $valores['nombreCargo']);
    $html .= generaParrafo('situacion', ($valores['situacion'] === 'si') ? 'activo' : 'inactivo');
    $html .= generaParrafo('especialidad', $valores['especialidad']);
    $html .= '<a href = "../index.php" class = "btnEnviar">Volver</a></div></form></main>';
    return $html;
}

function fechaValida($fechaHoy, $fechaLimite) {
    $fechaHoyConversion = strtotime($fechaHoy);
    $fechaLimiteConversion = strtotime($fechaLimite);
    return $fechaHoyConversion < $fechaLimiteConversion;
}
?>
