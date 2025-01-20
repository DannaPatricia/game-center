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
    'nombre' => $_POST['nombre'] ?? '',
    'clave' => $_POST['clave'] ?? '',
];
$camposValidos = [
    'nombre' => true,
    'clave' => true,
];
$mensaje = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $camposValidos['nombre'] = !empty($valorCampos['nombre']);
    $camposValidos['clave'] = !empty($valorCampos['clave']);
    if (compruebaCampos($camposValidos)) {
        $datosUsuario = compruebaUsuario($pdo, $valorCampos['nombre'], $valorCampos['clave']);
        if ($datosUsuario === null) {
            $mensaje = '<p class="mensaje">Credenciales incorrectas o usuario no existe</p>';
        } else {
            session_start();
            darDatosSesion($datosUsuario);
            header('Location: ../cursos.php');
            exit();
        }
    } else {
        $mensaje = '<p class="mensaje">Por favor, completa todos los campos</p>';
    }
}

$html = generaHeader('login.php', 'logout.php', '../index.php', '../cursos.php', '../administrador/opcionesAdmin.php', '../css/style.css');
$html .= $mensaje;
$html .= generaFormulario($valorCampos, $camposValidos);
$html .= generaFooter();
echo $html;

function generaFormulario($valores, $validos) {
    $html = '<main><form action="login.php" method="post" class="formulario">';
    $html .= '<h2>LOGIN</h2>';
    $html .= generaTexto('nombre', 'Nombre de usuario', $valores['nombre'], $validos['nombre'], 'Inserte nombre de usuario');
    $html .= generaPassword($validos['clave']);
    $html .= '<p>¿Aún no tienes una cuenta? <a href="registro.php">Registrarse</a></p>';
    $html .= generaSubmit();
    $html .= '</form></main>';
    return $html;
}

function compruebaCampos($camposValidos) {
    return !in_array(false, $camposValidos, true);
}

function darDatosSesion($datosUsuario){
    $_SESSION['id_usuario'] = $datosUsuario['id'];
    $_SESSION['nombre'] = $datosUsuario['nombre'];
    $_SESSION['apellidos'] = $datosUsuario['apellidos'];
    $_SESSION['email'] = $datosUsuario['correo'];
    $_SESSION['telefono'] = $datosUsuario['telefono'];
    $_SESSION['nombre_usuario'] = $datosUsuario['nombre_usuario'];
    $_SESSION['rol'] = $datosUsuario['rol'];
    $_SESSION['dni'] = $datosUsuario['dni'];
}
