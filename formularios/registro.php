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
    'dni' => $_POST['dni'] ?? '',
    'nombre' => $_POST['nombre'] ?? '',
    'apellidos' => $_POST['apellidos'] ?? '',
    'email' => $_POST['email'] ?? '',
    'telefono' => $_POST['telefono'] ?? '',
    'usuario' => $_POST['usuario'] ?? '',
    'clave' => $_POST['clave'] ?? '',
];
$camposValidos = [
    'dni' => true,
    'nombre' => true,
    'apellidos' => true,
    'email' => true,
    'telefono' => true,
    'usuario' => true,
    'clave' => true,
];

$mensaje = '';
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $camposValidos = asignarValidez($camposValidos, $valorCampos);
    if (compruebaCampos($camposValidos)) {
        $datosUsuario = insertaUsuario($pdo, $valorCampos['nombre'], $valorCampos['apellidos'], $valorCampos['email'], $valorCampos['telefono'], $valorCampos['usuario'], $valorCampos['clave'], $valorCampos['dni']);
        if ($datosUsuario === null) {
            $mensaje = '<p class="mensaje">No se ha podido registrar,dni con cuenta ya registrada o inserte correctamente los datos</p>';
        } else {
            session_start();
            darDatosSesion($datosUsuario);
            header('Location: login.php');
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

function compruebaCampos($camposValidos) {
    return !in_array(false, $camposValidos, true);
}

function validaNumero($numero){
    return strlen($numero) == 9;
}

function validaMail($email){
    return filter_var($email, FILTER_VALIDATE_EMAIL);
}

function darDatosSesion($datosUsuario){
            $_SESSION['id_usuario'] = $datosUsuario['id'];
            $_SESSION['nombre'] = $datosUsuario['nombre'];
            $_SESSION['apellidos'] = $datosUsuario['apellidos'];
            $_SESSION['email'] = $datosUsuario['email'];
            $_SESSION['telefono'] = $datosUsuario['telefono'];
            $_SESSION['nombre_usuario'] = $datosUsuario['nombre_usuario'];
            $_SESSION['rol'] = $datosUsuario['rol'];
            $_SESSION['dni'] = $datosUsuario['dni'];
}

function generaFormulario($valores, $validos) {
    $html = '<main><form action="registro.php" method="post" class="formulario">';
    $html .= '<h2>REGISTRO</h2>';
    $html .= generaTexto('dni', 'DNI', $valores['dni'], $validos['dni'], 'Inserte dni');
    $html .= generaTexto('nombre', 'Nombre', $valores['nombre'], $validos['nombre'], 'Inserte nombre');
    $html .= generaTexto('apellidos','Apellidos',  $valores['apellidos'], $validos['apellidos'], 'Inserte apellidos');
    $html .= generaTexto('email', 'Correo electrónico', $valores['email'], $validos['email'], 'Inserte email, ej: usuario@gmail.com');
    $html .= generaCampoNumerico('telefono', 'Número de teléfono', $valores['telefono'], $validos['telefono'], 'Inserte número de teléfono', ' deben ser 9 números');
    $html .= generaTexto('usuario', 'NOmbre de ususario', $valores['usuario'], $validos['usuario'], 'Inserte nombre de usuario');
    $html .= generaPassword($validos['clave']);
    $html .= generaSubmit();
    $html .= '</form></main>';
    return $html;
}

function asignarValidez($camposValidos, $valorCampos){
    $camposValidos['dni'] = !empty($valorCampos['dni']);
    $camposValidos['nombre'] = !empty($valorCampos['nombre']);
    $camposValidos['apellidos'] = !empty($valorCampos['apellidos']);
    $camposValidos['email'] = !empty($valorCampos['email']) && validaMail($valorCampos['email']);
    $camposValidos['telefono'] = !empty($valorCampos['telefono']) && validaNumero($valorCampos['telefono']);
    $camposValidos['usuario'] = !empty($valorCampos['usuario']);
    $camposValidos['clave'] = !empty($valorCampos['clave']);
    return $camposValidos;
}

?>
