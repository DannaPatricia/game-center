/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Usuario;

/**
 * Clase encargada de la gestión de operaciones relacionadas con la tabla 'usuarios' en la base de datos
 */
public class UsuarioDAO {

    // Método para comprobar si un usuario existe en la base de datos con el nombre de usuario y la contraseña proporcionados
    public Usuario comprobarUsuario(String nombreUsuario, String clave) {
        // Consulta SQL para verificar si existe un usuario con las credenciales proporcionadas
        String consulta = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?;";
        
        // Establecer la conexión, preparar la consulta y ejecutar la búsqueda
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            
            // Establecer los parámetros de la consulta
            pstm.setString(1, nombreUsuario);
            pstm.setString(2, clave);
            
            // Ejecutar la consulta y obtener los resultados
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Si se encuentra un usuario con las credenciales, crear un objeto Usuario
                    return new Usuario(
                            rs.getInt("id"),               // ID del usuario
                            rs.getString("nombre_usuario"), // Nombre de usuario
                            rs.getString("rol")            // Rol del usuario (cliente, admin, etc.)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error en caso de una excepción de SQL
        }
        
        return null;  // Retornar null si no se encuentra un usuario con esas credenciales
    }

    // Método para registrar un nuevo usuario en la base de datos
    public Usuario registrarUsuario(String nombre, String apellidos, String nombreUsuario, String clave, String email) {
        // Verificar si el usuario ya existe antes de intentar registrarlo
        if (comprobarUsuario(nombreUsuario, clave) == null) {
            // Consulta SQL para insertar un nuevo usuario en la base de datos
            String consulta = "INSERT INTO usuarios (nombre, apellidos, nombre_usuario, contrasena, email) VALUES (?, ?, ?, ?, ?);";
            
            // Establecer la conexión, preparar la sentencia y ejecutar la inserción
            try (Connection conexion = Conexion.getConexion(); 
                 PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
                
                // Establecer los parámetros de la consulta
                pstm.setString(1, nombre);
                pstm.setString(2, apellidos);
                pstm.setString(3, nombreUsuario);
                pstm.setString(4, clave);
                pstm.setString(5, email);
                
                // Ejecutar la consulta y verificar si se insertaron filas
                int filas = pstm.executeUpdate();
                if (filas > 0) {
                    // Obtener las claves generadas (en este caso, el ID del nuevo usuario)
                    try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1); // Obtener el ID generado del nuevo usuario
                            // Retornar un objeto Usuario con el ID generado y rol 'cliente'
                            return new Usuario(id, nombreUsuario, "cliente");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Imprimir el error si ocurre una excepción SQL
            }
        }
        
        return null;  // Retornar null si el usuario no fue registrado (ya existe o ocurrió un error)
    }
}
