<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

function compruebaUsuario($pdo, $nombreUsuario, $clave) {
    try {
        $sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND clave = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$nombreUsuario, $clave]);
        if ($stmt->rowCount() > 0) {
            return $stmt->fetch(PDO::FETCH_ASSOC);
        }
    } catch (PDOException $e) {
        return null;
    }
    return null;
}

function compruebaDni($pdo, $dni) {
    try {
        $sql = "SELECT * FROM usuarios WHERE dni = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$dni]);
        if ($stmt->rowCount() > 0) {
            return $stmt->fetch(PDO::FETCH_ASSOC);
        }
    } catch (PDOException $e) {
        return null;
    }
    return null;
}

function insertaUsuario($pdo, $nombre, $apellidos, $correo, $telefono, $nombreUsuario, $clave, $dni){
    if(!compruebaUsuario($pdo, $nombreUsuario, $clave) && !compruebaDni($pdo, $dni)){
        try {
            $sql = "INSERT INTO `usuarios`(`dni`,`nombre`, `apellidos`, `correo`, `telefono`, `nombre_usuario`, `clave`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute([$dni, $nombre, $apellidos, $correo, $telefono, $nombreUsuario, $clave]);
            if ($stmt->rowCount() > 0) {
                return [
                    'id' => $pdo->lastInsertId(),  // Devuelve el último ID insertado
                    'nombre' => $nombre,
                    'apellidos' => $apellidos,
                    'correo' => $correo,
                    'telefono' => $telefono,
                    'nombre_usuario' => $nombreUsuario,
                    'dni' => $dni,
                    'rol' => 'cliente'
                ];            }
        } catch (PDOException $e) {
            return null;
        }
        return null;
    }
}

function buscarSolicitante($pdo, $dni){
    try {
        $sql = "SELECT * FROM solicitantes WHERE dni = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$dni]);
        if ($stmt->rowCount() > 0) {
            return $stmt->fetch(PDO::FETCH_ASSOC);
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function insertarSolicitante($pdo, $dni, $nombre, $apellidos, $correo, $telefono, $valorCampos){
    $nombreCargo = empty($valorCampos['nombreCargo']) ? 'vacio' : $valorCampos['nombreCargo'];
    try {
        $sql = "INSERT INTO `solicitantes`(`dni`, `apellidos`, `nombre`, `telefono`, `correo`, `codigocentro`, `coordinadortc`, `grupotc`, `nombregrupo`, `pbilin`, `cargo`, `nombrecargo`, `situacion`, `fechanac`, `especialidad`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$dni, $apellidos, $nombre, $telefono, $correo, $valorCampos['codigoCentro'], (int)siNo($valorCampos['coordinadorTIC']), (int)siNo($valorCampos['grupoTIC']), $valorCampos['nombreGrupo'], (int)siNo($valorCampos['programaBilingue']), (int)siNo($valorCampos['cargo']), $nombreCargo, activoNo($valorCampos['situacion']), $valorCampos['fechaNacimiento'], $valorCampos['especialidad']]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        echo $e->getMessage();
    }
    return false;
}

function siNo($valor){
    return ($valor === 'si') ? '1' : '0';
}

function activoNo($valor){
    return ($valor === 'si') ? 'activo' : 'inactivo';
}

function guardarSolicitud($pdo, $dni, $codigoCurso){
    try {
        $sql = "INSERT INTO `solicitudes`(`dni`, `codigocurso`, `fechasolicitud`) VALUES (?, ?, ?)";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$dni, $codigoCurso, date('Y-m-d')]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function restarNPlazas($pdo, $codigoCurso){
    try {
        $sql = "UPDATE `cursos` SET `numeroplazas`= `numeroplazas` - 1 WHERE codigo = ?";
        $stmt = $pdo->prepare($sql);
        $stmt->execute([$codigoCurso]);
        if ($stmt->rowCount() > 0) {
            return true;
        }
    } catch (PDOException $e) {
        return false;
    }
    return false;
}

function sumarPuntos($pdo, $cargos, $cargoJefe){
    try {
        $sql = "SELECT * FROM solicitantes";
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        if ($stmt->rowCount() > 0) {
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
                $puntos = establecerPuntos($row, $cargos, $cargoJefe);
                actualizarPuntos($pdo, $puntos, $row['dni']);
            }
            return true;
        }
    } catch (PDOException $e) {
        return null;
    }
    return null;
}

function actualizarPuntos($pdo, $puntos, $dni){
try {
    $sql = "UPDATE `solicitantes` SET `puntos`= ? WHERE dni = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$puntos, $dni]);
    if ($stmt->rowCount() > 0) {
        return true;
    }
} catch (PDOException $e) {
    return false;
}
return false;
}

function establecerPuntos($row, $cargos, $cargoJefe){
$puntos = 0;
$cargoTabla = strtolower(trim($row['nombrecargo']));
$puntos += ($row['coordinadortc'] === 1) ? 4 : 0;
$puntos += ($row['pbilin'] === 1) ? 3 : 0;
$puntos += ($row['situacion'] === 'activo') ? 1 : 0;
$puntos += (compruebaFechaNac($row['fechanac']) > 15) ? 1 : 0;
$puntos += (in_array($cargoTabla, $cargos)) ? 2 : 0;
$puntos += ($cargoTabla === $cargoJefe) ? 1 : 0;
$puntos += ($row['grupotc'] === 1) ? 3 : 0;
return $puntos;
}


function compruebaFechaNac($fechaNac){
$fecha = new DateTime($fechaNac);
$hoy = new DateTime();
$interval = $hoy->diff($fecha);
return $interval->y;
}
?>
