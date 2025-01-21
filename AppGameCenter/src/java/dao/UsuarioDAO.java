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
 *
 * @author Administrator
 */
public class UsuarioDAO {

    public Usuario comprobarUsuario(String nombreUsuario, String clave) {
        String consulta = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?;";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, nombreUsuario);
            pstm.setString(2, clave);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre_usuario"),
                            rs.getString("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario registrarUsuario(String nombre, String apellidos, String nombreUsuario, String clave, String email) {
        if (comprobarUsuario(nombreUsuario, clave) == null) {
            String consulta = "INSERT INTO usuarios (nombre, apellidos, nombre_usuario, contrasena, email) VALUES (?, ?, ?, ?, ?);";
            try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstm.setString(1, nombre);
                pstm.setString(2, apellidos);
                pstm.setString(3, nombreUsuario);
                pstm.setString(4, clave);
                pstm.setString(5, email);
                int filas = pstm.executeUpdate();
                if (filas > 0) {
                    try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1); // Obtener el ID generado
                            return new Usuario(id,nombreUsuario, "cliente");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
