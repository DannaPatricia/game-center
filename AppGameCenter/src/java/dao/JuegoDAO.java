/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Juego;

/**
 * Clase encargada de la gestión de operaciones sobre la tabla 'juegos' en la base de datos
 */
public class JuegoDAO {

    // Método para obtener todos los juegos almacenados en la base de datos
    public ArrayList<Juego> obtenerJuegos() {
        // Lista que almacenará los objetos de tipo Juego recuperados
        ArrayList<Juego> juegos = new ArrayList<>();
        
        // Consulta SQL para obtener todos los juegos
        String consulta = "SELECT * FROM juegos;";
        
        // Establecer la conexión, ejecutar la consulta y recorrer los resultados
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta); 
             ResultSet rs = pstm.executeQuery()) {
            
            // Recorrer los resultados de la consulta
            while (rs.next()) {
                Juego juego = new Juego();  // Crear una instancia de Juego
                juego.setId(rs.getInt("id"));
                juego.setNombre(rs.getString("nombre"));
                juego.setPlataforma(rs.getString("plataforma"));
                juego.setCompaniaDesarrolladora(rs.getString("compania_desarrolladora"));
                juego.setGenero(rs.getString("genero"));
                juego.setPuntuacionMetacritic(rs.getDouble("puntuacion_metacritic"));
                juego.setPrecio(rs.getDouble("precio"));
                juego.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                juego.setConsolaId(rs.getInt("consola_id"));
                juegos.add(juego);  // Agregar el juego a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejar cualquier error de SQL
        }
        
        // Retornar la lista con los juegos obtenidos
        return juegos;
    }

    // Método para obtener los juegos filtrados por el id de la consola
    public ArrayList<Juego> obtenerJuegosPorId(int idConsola) {
        // Lista que almacenará los juegos de la consola solicitada
        ArrayList<Juego> juegos = new ArrayList<>();
        
        // Consulta SQL para obtener juegos por el id de la consola
        String consulta = "SELECT * FROM juegos WHERE consola_id = ?;";
        
        // Establecer la conexión, preparar la consulta y ejecutar
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, idConsola);  // Establecer el valor del parámetro en la consulta
            try (ResultSet rs = pstm.executeQuery()) {
                // Recorrer los resultados obtenidos
                while (rs.next()) {
                    Juego juego = new Juego();  // Crear un objeto Juego
                    juego.setId(rs.getInt("id"));
                    juego.setNombre(rs.getString("nombre"));
                    juego.setCompaniaDesarrolladora(rs.getString("compania_desarrolladora"));
                    juego.setGenero(rs.getString("genero"));
                    juego.setPuntuacionMetacritic(rs.getDouble("puntuacion_metacritic"));
                    juego.setPrecio(rs.getDouble("precio"));
                    juego.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                    juegos.add(juego);  // Agregar el juego a la lista
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener juegos para la consola con ID " + idConsola);
            e.printStackTrace();  // Mostrar un error si la consulta falla
        }
        
        // Retornar la lista de juegos para esa consola
        return juegos;
    }

    // Método para insertar un nuevo juego en la base de datos
    public boolean insertarJuego(String nombre, String plataforma, String companiaDesarrolladora, String genero, double puntuacionMetacritic, double precio, int unidadesDisponibles, int consolaId) {
        // Consulta SQL para insertar un juego nuevo
        String consulta = "INSERT INTO juegos (nombre, plataforma, compania_desarrolladora, genero, puntuacion_metacritic, precio, unidades_disponibles, consola_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Establecer la conexión, preparar la sentencia y ejecutar la inserción
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // Establecer los parámetros de la consulta
            pstm.setString(1, nombre);
            pstm.setString(2, plataforma);
            pstm.setString(3, companiaDesarrolladora);
            pstm.setString(4, genero);
            pstm.setDouble(5, puntuacionMetacritic);
            pstm.setDouble(6, precio);
            pstm.setInt(7, unidadesDisponibles);
            pstm.setInt(8, consolaId);
            
            // Ejecutar la consulta y verificar si se insertaron filas
            int filas = pstm.executeUpdate();
            return filas > 0;  // Retornar verdadero si se insertó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error si ocurre una excepción
        }
        
        return false;  // Si no se insertó nada, retornar falso
    }

    // Método para modificar los datos de un juego existente en la base de datos
    public boolean modificarJuego(int id, String nombre, String plataforma, String companiaDesarrolladora, String genero, double puntuacionMetacritic, double precio, int unidadesDisponibles, int consolaId) {
        // Consulta SQL para actualizar un juego específico por su ID
        String consulta = "UPDATE juegos SET nombre = ?, plataforma = ?, compania_desarrolladora = ?, genero = ?, puntuacion_metacritic = ?, precio = ?, unidades_disponibles = ?, consola_id = ?  WHERE id = ?";
        
        // Establecer la conexión, preparar la sentencia y ejecutar la actualización
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            
            // Establecer los parámetros de la consulta
            pstm.setString(1, nombre);
            pstm.setString(2, plataforma);
            pstm.setString(3, companiaDesarrolladora);
            pstm.setString(4, genero);
            pstm.setDouble(5, puntuacionMetacritic);
            pstm.setDouble(6, precio);
            pstm.setInt(7, unidadesDisponibles);
            pstm.setInt(8, consolaId);
            pstm.setInt(9, id);
            
            // Ejecutar la consulta y verificar el número de filas afectadas
            int filas = pstm.executeUpdate();
            return filas > 0;  // Retornar verdadero si la actualización afectó filas
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Retornar falso si no se modificaron filas
    }

    // Método para eliminar un juego de la base de datos
    public boolean eliminarJuego(int id) {
        // Consulta SQL para eliminar un juego por su ID
        String consulta = "DELETE FROM juegos WHERE id = ?";
        
        // Establecer la conexión, preparar la sentencia y ejecutar la eliminación
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);  // Establecer el ID del juego a eliminar
            int filas = pstm.executeUpdate();
            return filas > 0;  // Retornar verdadero si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Retornar falso si no se eliminó ninguna fila
    }

    // Método para realizar la compra de un juego (disminuir unidades disponibles)
    public boolean compraJuego(int id) {
        // Consulta SQL para reducir las unidades disponibles del juego
        String consulta = "UPDATE juegos SET unidades_disponibles = unidades_disponibles - 1 WHERE id = ?";
        
        // Establecer la conexión, preparar la sentencia y ejecutar la compra
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);  // Establecer el ID del juego a comprar
            int filas = pstm.executeUpdate();
            return filas > 0;  // Retornar verdadero si la compra se realizó exitosamente
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Retornar falso si no se actualizó ninguna fila
    }

    // Método para obtener los detalles de un juego por su ID
    public Juego obtenerJuegoPorId(int id) {
        // Consulta SQL para obtener un juego por su ID
        String consulta = "SELECT * FROM juegos WHERE id = ?";
        
        // Establecer la conexión, preparar la sentencia y ejecutar la consulta
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);  // Establecer el ID del juego a obtener
            try (ResultSet rs = pstm.executeQuery()) {
                // Si hay resultados, crear un objeto Juego con los datos
                if (rs.next()) {
                    Juego juego = new Juego();
                    juego.setId(rs.getInt("id"));
                    juego.setNombre(rs.getString("nombre"));
                    juego.setPlataforma(rs.getString("plataforma"));
                    juego.setCompaniaDesarrolladora(rs.getString("compania_desarrolladora"));
                    juego.setGenero(rs.getString("genero"));
                    juego.setPuntuacionMetacritic(rs.getDouble("puntuacion_metacritic"));
                    juego.setPrecio(rs.getDouble("precio"));
                    juego.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                    juego.setConsolaId(rs.getInt("consola_id"));
                    return juego;  // Retornar el objeto Juego con los datos obtenidos
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return null;  // Si no se encuentra el juego, retornar null
    }
}
