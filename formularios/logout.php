<?php
session_start();

if (isset($_SESSION['id_usuario'])) {
    session_unset();
    session_destroy();
    header('Location: ../index.php');
    exit();
} else {
    header('Location: ../cursos.php');
    exit();
}
?>
