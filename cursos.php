<?php
include 'header.php';
include 'footer.php';
include 'conexionBd.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $html = generaHeader('formularios/logout.php', 'formularios/login.php', 'index.php', 'cursos.php', 'administrador/opcionesAdmin.php', 'css/style.css');
    $html .= $_SESSION['mensaje'];
    $html .= obtenerCursosActivos($pdo);
    $html .= '</main>'.generaFooter();
    echo $html;
}

$_SESSION['mensaje'] = '';

function obtenerCursosActivos($pdo) {
    $html = '';
    $sql = "SELECT * FROM cursos WHERE abierto = 1";
    $stmt = $pdo->query($sql);
    if ($stmt->rowCount() > 0) {
        $html .= '<main>';
        while ($fila = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $html .= generaDatosCurso($fila);
        }
    } else {
        $html .= "<p class='mensaje'>No hay cursos activos en este momento.</p>";
    }
    return $html;
}

function generaDatosCurso($fila){
    return "<form class='formulario' action = 'formularios/InscribirseCurso.php' method = 'get'>
    <div class = 'containerInfoCurso'>
                <input type='hidden' name='plazoInscripcion' value = '{$fila['plazoinscripcion']}'>
                <input type='hidden' name='numeroPlazas' value = '{$fila['numeroplazas']}'>
                <h2>{$fila['nombre']}</h2>
                <p>Plazo inscripción: {$fila['plazoinscripcion']}</p>
                <button type = 'submit' class = 'btnEnviar' name = 'btnEnviar' value = '{$fila['codigo']}-{$fila['nombre']}'>Inscribirse</button>
              </div></form>";
}

?>
