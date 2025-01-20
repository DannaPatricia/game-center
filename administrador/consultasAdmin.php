<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

function obtenerPuntajes($pdo, $codigoCurso, $nPlazas){
    try {
        $sql = "SELECT * FROM solicitantes INNER JOIN solicitudes ON solicitantes.dni = solicitudes.dni
        WHERE solicitudes.codigocurso = :codigo ORDER BY solicitantes.puntos DESC LIMIT :nPlazas";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':codigo', $codigoCurso, PDO::PARAM_STR);
        $stmt->bindParam(':nPlazas', $nPlazas, PDO::PARAM_INT);
        $stmt->execute();
        if ($stmt->rowCount() > 0) {
            while ($fila = $stmt->fetch(PDO::FETCH_ASSOC)) {
                admitirSolicitantes($pdo, $codigoCurso, $fila['dni']);
                mostrarAdmitidos($pdo, $codigoCurso, $fila['nombre'], $fila['dni']);
            }
            return true;
        }else{
            echo "<p class = 'mensaje'>no hay admitidos</p>";
        }
    } catch (PDOException $e) {
        echo $e->getMessage();
        return false;
    }
    return false;
}

function admitirSolicitantes($pdo, $codigoCurso, $dni){
    try {
        $sql = "UPDATE `solicitudes` SET `admitido`= 1 WHERE codigocurso = ? AND DNI = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$codigoCurso, $dni]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        echo $e->getMessage();
        return false;
    }
    return false;

}

function mostrarAdmitidos($pdo, $codigoCurso, $nombre, $dni){
    try {
        $sql = "SELECT * FROM solicitudes WHERE codigocurso = :codigo AND admitido = 1 AND dni = :dni";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':codigo', $codigoCurso, PDO::PARAM_STR);
        $stmt->bindParam(':dni', $dni, PDO::PARAM_STR);
        $stmt->execute();
        if ($stmt->rowCount() > 0) {
            while ($fila = $stmt->fetch(PDO::FETCH_ASSOC)) {
                echo generaDatosAdmitido($fila, $nombre);
            }
            return true;
        } else {
            echo "<p class='mensaje'>no hay admitidos</p>";
        }
    } catch (PDOException $e) {
        echo $e->getMessage();
        return false;
    }
    return false;
}


function generaDatosAdmitido($fila, $nombre){
    return "<div class='formulario'>
    <div class = 'containerInfoCurso'>
                <h2>".ucfirst($nombre)."</h2>
                <p>Fecha solicitud: {$fila['fechasolicitud']}</p>
              </div>
    </div>";
}


function generaSelectBd($pdo, $conRestriccion = false) {
    try {
        $sql = "SELECT * FROM cursos";
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        if ($stmt->rowCount() > 0) {
            $html = '<select name="curso" id="curso">';
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                if (!$conRestriccion || fechaValida(date('Y-m-d'), $row['plazoinscripcion'])) {
                    $valor = $row['codigo'] . '-' . $row['nombre'];
                    $html .= '<option value="' . htmlspecialchars($valor) . '">' . htmlspecialchars($row['nombre']) . '</option>';
                }
            }
            $html .= '</select>';
            return $html;
        }
    } catch (PDOException $e) {
        return null;
    }
    return null;
}


function obtenerCursosCerrados($pdo) {
    $html = '';
    $sql = "SELECT * FROM cursos WHERE abierto = 0";
    $stmt = $pdo->query($sql);
    if ($stmt->rowCount() > 0) {
        $html .= '<main>';
        while ($fila = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $html .= generaDatosCurso($fila);
        }
    } else {
        $html .= "<p class='mensaje'>No hay cursos cerrados en este momento.</p>";
    }
    return $html;
}

function generaDatosCurso($fila){
    return "<form class='formulario' action = 'realizarBaremo.php' method = 'post'>
    <div class = 'containerInfoCurso'>
                <input type='hidden' name='plazoInscripcion' value = '{$fila['plazoinscripcion']}'>
                <input type='hidden' name='numeroPlazas' value = '{$fila['numeroplazas']}'>
                <h2>{$fila['nombre']}</h2>
                <p>Plazo inscripción: {$fila['plazoinscripcion']}</p>
                <p>Número plazas: {$fila['numeroplazas']}</p>
                <button type = 'submit' class = 'btnEnviar' name = 'codigoCurso' value = '{$fila['codigo']}-{$fila['nombre']}'>Realizar baremo</button>
              </div></form>";
}

function fechaValida($fechaHoy, $fechaLimite) {
    $fechaHoyConversion = strtotime($fechaHoy);
    $fechaLimiteConversion = strtotime($fechaLimite);
    return $fechaHoyConversion > $fechaLimiteConversion;
}

function insertarCurso($pdo, $valorCampos){
    $estado = ($valorCampos['estado'] === 'abrir') ? 1 : 0;
    try {
        $sql = "INSERT INTO `cursos`(`nombre`, `abierto`, `numeroplazas`, `plazoinscripcion`) VALUES (?, ?, ?, ?)";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$valorCampos['nombre'], $estado, $valorCampos['nPlazas'], $valorCampos['fechaLimite']]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function activarDesactivarCurso($pdo, $booleano, $codigoCurso){
    try {
        $sql = "UPDATE `cursos` SET `abierto`= ? WHERE codigo = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$booleano, $codigoCurso]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function asignarNPlazas($pdo, $nPlazas, $codigoCurso){
    try {
        $sql = "UPDATE `cursos` SET `numeroplazas`= ? WHERE codigo = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$nPlazas, $codigoCurso]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function eliminarCurso($pdo, $codigo){
    try {
        $sql = "DELETE FROM `cursos` WHERE codigo = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$codigo]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function obtenerListado($pdo, $codigoCurso){
    $html = '';
    $sql = "SELECT * FROM solicitudes INNER JOIN usuarios ON solicitudes.dni = usuarios.dni WHERE solicitudes.admitido = 1 AND solicitudes.codigocurso = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$codigoCurso]);
    if ($stmt->rowCount() > 0) {
        $html .= '<main>';
        while ($fila = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $html .= generaDatoSolicitante($fila);
        }
    } else {
        $html .= "<p class='mensaje'>No hay admitidos en este momento.</p>";
    }
    return $html.'<main>';
}


function generaDatoSolicitante($fila){
    return "<form class='formulario' action = '' method = 'get'>
    <div class = 'container'>
                <p class = 'textoDestacado'>DNI: {$fila['dni']}</p>
                <p>Nombre: {$fila['nombre']}</p>
                <p>Apellido: {$fila['apellidos']}</p>
                <p>Fecha de solicitud: {$fila['fechasolicitud']}</p>
              </div></form>";
}



?>
