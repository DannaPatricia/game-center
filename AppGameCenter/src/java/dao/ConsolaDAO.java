package dao;

import java.sql.*;
import modelo.Consola;
import java.util.ArrayList;

public class ConsolaDAO {

    public ArrayList<Consola> obtenerConsolas() {
        ArrayList<Consola> consolas = new ArrayList<>();
        String consulta = "SELECT * FROM consolas";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                Consola consola = new Consola();
                consola.setId(rs.getInt("id"));
                consola.setNombre(rs.getString("nombre"));
                consola.setPotenciaCpu(rs.getDouble("potencia_cpu"));
                consola.setPotenciaGpu(rs.getDouble("potencia_gpu"));
                consola.setCompania(rs.getString("compania"));
                consola.setPrecio(rs.getDouble("precio"));
                consola.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                consolas.add(consola);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consolas;
    }

    public boolean insertarConsola(String nombre, double potenciaCpu, double potenciaGpu, String compania, double precio, int unidadesDisponibles) {
        String consulta = "INSERT INTO consolas (nombre, potencia_cpu, potencia_gpu, compania, precio, unidades_disponibles) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, nombre);
            pstm.setDouble(2, potenciaCpu);
            pstm.setDouble(3, potenciaGpu);
            pstm.setString(4, compania);
            pstm.setDouble(5, precio);
            pstm.setInt(6, unidadesDisponibles);
            int filas = pstm.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modificarConsola(int id, String nombre, double potenciaCpu, double potenciaGpu, String compania, double precio, int unidadesDisponibles) {
        String consulta = "UPDATE consolas SET nombre = ?, potencia_cpu = ?, potencia_gpu = ?, compania = ?, precio = ?, unidades_disponibles = ? WHERE id = ?";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setString(1, nombre);
            pstm.setDouble(2, potenciaCpu);
            pstm.setDouble(3, potenciaGpu);
            pstm.setString(4, compania);
            pstm.setDouble(5, precio);
            pstm.setInt(6, unidadesDisponibles);
            pstm.setInt(7, id);
            int filas = pstm.executeUpdate();
            if (filas > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarConsola(int id) {
        String consulta = "DELETE FROM consolas WHERE id = ?;";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            int fila = pstm.executeUpdate();
            return fila > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean comprarConsola(int id) {
        String consulta = "UPDATE consolas SET unidades_disponibles = unidades_disponibles - 1 WHERE id = ?";
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

    public Consola obtenerConsolaPorId(int id) {
        String consulta = "SELECT * FROM consolas WHERE id = ?";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Consola consola = new Consola();
                    consola.setId(rs.getInt("id"));
                    consola.setNombre(rs.getString("nombre"));
                    consola.setPotenciaCpu(rs.getDouble("potencia_cpu"));
                    consola.setPotenciaGpu(rs.getDouble("potencia_gpu"));
                    consola.setCompania(rs.getString("compania"));
                    consola.setPrecio(rs.getDouble("precio"));
                    consola.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                    return consola;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generaOpcionMultiple() {
        StringBuilder html = new StringBuilder();
        String consulta = "SELECT id, nombre FROM consolas";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String valor = nombre + "-" + id;
                html.append("<option value=\"");
                html.append(valor).append("\">");
                html.append(nombre);
                html.append("</option>\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return html.toString();
    }

    public static String generaOpcionMultiple(String nombreConsola) {
        StringBuilder html = new StringBuilder();
        String consulta = "SELECT id, nombre FROM consolas";
        try (Connection conexion = Conexion.getConexion(); PreparedStatement pstm = conexion.prepareStatement(consulta); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String valor = nombre + "-" + rs.getInt("id");
                String seleccionado = nombre.equals(nombreConsola) ? " selected" : "";
                html.append("<option value=\"").append(valor);
                html.append("\"").append(seleccionado).append(">");
                html.append(nombre);
                html.append("</option>\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return html.toString();
    }
}
