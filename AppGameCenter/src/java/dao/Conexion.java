package dao;

import java.sql.*;

public class Conexion {

    // Método estático que devuelve una conexión a la base de datos
    public static Connection getConexion() {
        // Parámetros para la conexión: URL, usuario y contraseña
        String url = "jdbc:mysql://localhost:3306/game_center";  // URL de la base de datos (localhost:3306 es el puerto y "game_center" es el nombre de la base de datos)
        String user = "daw2";  // Usuario para la base de datos
        String password = "1234";  // Contraseña para la base de datos

        try {
            // Cargar el driver JDBC de MySQL, necesario para establecer la conexión
            Class.forName("com.mysql.cj.jdbc.Driver");  // Registra el driver MySQL en el sistema
            System.out.println("Driver cargado correctamente.");  // Si el driver se carga correctamente, imprime un mensaje

            // Establecer la conexión con la base de datos usando la URL, usuario y contraseña
            Connection conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida correctamente.");  // Si la conexión es exitosa, imprime este mensaje

            return conexion;  // Devuelve la conexión establecida

        } catch (ClassNotFoundException e) {
            // Si no se encuentra el driver MySQL, se captura la excepción
            System.out.println("Error: No se encontró el driver MySQL.");
            e.printStackTrace();  // Imprime el error detallado
            return null;  // Retorna null si no se puede cargar el driver

        } catch (SQLException e) {
            // Si ocurre un error al establecer la conexión (por ejemplo, credenciales incorrectas), se captura la excepción
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();  // Imprime el error detallado
            return null;  // Retorna null si no se puede establecer la conexión
        }
    }
}
