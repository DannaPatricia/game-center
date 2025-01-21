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
 *
 * @author Administrator
 */
public class JuegoDAO {

    public ArrayList<Juego> obtenerJuegos() {
        ArrayList<Juego> juegos = new ArrayList<>();
        String consulta = "SELECT * FROM juegos;";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
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
                juegos.add(juego);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }

    public ArrayList<Juego> obtenerJuegosPorId(int idConsola) {
        ArrayList<Juego> juegos = new ArrayList<>();
        String consulta = "SELECT * FROM juegos WHERE consola_id = ?;";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, idConsola);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Juego juego = new Juego();
                    juego.setId(rs.getInt("id"));
                    juego.setNombre(rs.getString("nombre"));
                    juego.setCompaniaDesarrolladora(rs.getString("compania_desarrolladora"));
                    juego.setGenero(rs.getString("genero"));
                    juego.setPuntuacionMetacritic(rs.getDouble("puntuacion_metacritic"));
                    juego.setPrecio(rs.getDouble("precio"));
                    juego.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                    juegos.add(juego);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener juegos para la consola con ID " + idConsola);
            e.printStackTrace();
        }
        return juegos;
    }

    public boolean insertarJuego(String nombre, String plataforma, String companiaDesarrolladora, String genero, double puntuacionMetacritic, double precio, int unidadesDisponibles, int consolaId) {
        String consulta = "INSERT INTO juegos (nombre, plataforma, compania_desarrolladora, genero, puntuacion_metacritic, precio, unidades_disponibles, consola_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, nombre);
            pstm.setString(2, plataforma);
            pstm.setString(3, companiaDesarrolladora);
            pstm.setString(4, genero);
            pstm.setDouble(5, puntuacionMetacritic);
            pstm.setDouble(6, precio);
            pstm.setInt(7, unidadesDisponibles);
            pstm.setInt(8, consolaId);
            int filas = pstm.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modificarJuego(int id, String nombre, String plataforma, String companiaDesarrolladora, String genero, double puntuacionMetacritic, double precio, int unidadesDisponibles, int consolaId) {
        String consulta = "UPDATE juegos SET nombre = ?, plataforma = ?, compania_desarrolladora = ?, genero = ?, puntuacion_metacritic = ?, precio = ?, unidades_disponibles = ?, consola_id = ?  WHERE id = ?";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, nombre);
            pstm.setString(2, plataforma);
            pstm.setString(3, companiaDesarrolladora);
            pstm.setString(4, genero);
            pstm.setDouble(5, puntuacionMetacritic);
            pstm.setDouble(6, precio);
            pstm.setInt(7, unidadesDisponibles);
            pstm.setInt(8, consolaId);
            pstm.setInt(9, id);
            int filas = pstm.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarJuego(int id) {
        String consulta = "DELETE FROM juegos WHERE id = ?";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            int filas = pstm.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean compraJuego(int id) {
        String consulta = "UPDATE juegos SET unidades_disponibles = unidades_disponibles - 1 WHERE id = ?";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            int filas = pstm.executeUpdate();
            if (filas > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Juego obtenerJuegoPorId(int id) {
        String consulta = "SELECT * FROM juegos WHERE id = ?";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
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
                    return juego;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
