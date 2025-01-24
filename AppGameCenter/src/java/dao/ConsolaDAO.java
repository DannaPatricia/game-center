package dao;

import java.sql.*;
import modelo.Consola;
import java.util.ArrayList;

public class ConsolaDAO {

    // Método para obtener todas las consolas de la base de datos
    public ArrayList<Consola> obtenerConsolas() {
        // Lista que almacenará las consolas recuperadas
        ArrayList<Consola> consolas = new ArrayList<>();
        
        // Consulta SQL para obtener todos los registros de la tabla "consolas"
        String consulta = "SELECT * FROM consolas";
        
        // Establecer la conexión y ejecutar la consulta
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta); 
             ResultSet rs = pstm.executeQuery()) {
            
            // Recorrer los resultados de la consulta
            while (rs.next()) {
                Consola consola = new Consola();  // Crear un nuevo objeto Consola
                consola.setId(rs.getInt("id"));
                consola.setNombre(rs.getString("nombre"));
                consola.setPotenciaCpu(rs.getDouble("potencia_cpu"));
                consola.setPotenciaGpu(rs.getDouble("potencia_gpu"));
                consola.setCompania(rs.getString("compania"));
                consola.setPrecio(rs.getDouble("precio"));
                consola.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                consolas.add(consola);  // Agregar la consola a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        // Retornar la lista de consolas
        return consolas;
    }

    // Método para insertar una nueva consola en la base de datos
    public boolean insertarConsola(String nombre, double potenciaCpu, double potenciaGpu, String compania, double precio, int unidadesDisponibles) {
        // Consulta SQL para insertar una nueva consola
        String consulta = "INSERT INTO consolas (nombre, potencia_cpu, potencia_gpu, compania, precio, unidades_disponibles) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Establecer la conexión y preparar la sentencia SQL
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            // Asignar valores a los parámetros de la consulta
            pstm.setString(1, nombre);
            pstm.setDouble(2, potenciaCpu);
            pstm.setDouble(3, potenciaGpu);
            pstm.setString(4, compania);
            pstm.setDouble(5, precio);
            pstm.setInt(6, unidadesDisponibles);
            
            // Ejecutar la consulta de inserción y verificar el número de filas afectadas
            int filas = pstm.executeUpdate();
            return filas > 0;  // Si se insertaron filas, retornar verdadero
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Si no se insertó ninguna fila, retornar falso
    }

    // Método para modificar los datos de una consola existente
    public boolean modificarConsola(int id, String nombre, double potenciaCpu, double potenciaGpu, String compania, double precio, int unidadesDisponibles) {
        // Consulta SQL para actualizar una consola existente por su id
        String consulta = "UPDATE consolas SET nombre = ?, potencia_cpu = ?, potencia_gpu = ?, compania = ?, precio = ?, unidades_disponibles = ? WHERE id = ?";
        
        // Establecer la conexión y preparar la sentencia SQL
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            
            // Asignar valores a los parámetros de la consulta
            pstm.setString(1, nombre);
            pstm.setDouble(2, potenciaCpu);
            pstm.setDouble(3, potenciaGpu);
            pstm.setString(4, compania);
            pstm.setDouble(5, precio);
            pstm.setInt(6, unidadesDisponibles);
            pstm.setInt(7, id);
            
            // Ejecutar la consulta de actualización y verificar el número de filas afectadas
            int filas = pstm.executeUpdate();
            if (filas > 0) {
                return true;  // Si se actualizó alguna fila, retornar verdadero
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Si no se actualizó ninguna fila, retornar falso
    }

    // Método para eliminar una consola por su id
    public boolean eliminarConsola(int id) {
        // Consulta SQL para eliminar una consola por su id
        String consulta = "DELETE FROM consolas WHERE id = ?";
        
        // Establecer la conexión y preparar la sentencia SQL
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            
            // Asignar el valor del id al parámetro de la consulta
            pstm.setInt(1, id);
            
            // Ejecutar la consulta de eliminación y verificar el número de filas afectadas
            int fila = pstm.executeUpdate();
            return fila > 0;  // Si se eliminó una fila, retornar verdadero
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Si no se eliminó ninguna fila, retornar falso
    }

    // Método para realizar una compra de una consola (disminuir unidades disponibles)
    public boolean comprarConsola(int id) {
        // Consulta SQL para reducir las unidades disponibles de una consola
        String consulta = "UPDATE consolas SET unidades_disponibles = unidades_disponibles - 1 WHERE id = ?";
        
        // Establecer la conexión y preparar la sentencia SQL
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            
            // Asignar el valor del id al parámetro de la consulta
            pstm.setInt(1, id);
            
            // Ejecutar la consulta de actualización y verificar el número de filas afectadas
            int filas = pstm.executeUpdate();
            if (filas > 0) {
                return true;  // Si se actualizó alguna fila, retornar verdadero
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return false;  // Si no se actualizó ninguna fila, retornar falso
    }

    // Método para obtener una consola por su id
    public Consola obtenerConsolaPorId(int id) {
        // Consulta SQL para obtener una consola por su id
        String consulta = "SELECT * FROM consolas WHERE id = ?";
        
        // Establecer la conexión y preparar la sentencia SQL
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta)) {
            
            // Asignar el valor del id al parámetro de la consulta
            pstm.setInt(1, id);
            
            // Ejecutar la consulta y verificar si hay resultados
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Consola consola = new Consola();  // Crear un nuevo objeto Consola
                    consola.setId(rs.getInt("id"));
                    consola.setNombre(rs.getString("nombre"));
                    consola.setPotenciaCpu(rs.getDouble("potencia_cpu"));
                    consola.setPotenciaGpu(rs.getDouble("potencia_gpu"));
                    consola.setCompania(rs.getString("compania"));
                    consola.setPrecio(rs.getDouble("precio"));
                    consola.setUnidadesDisponibles(rs.getInt("unidades_disponibles"));
                    return consola;  // Retornar la consola encontrada
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return null;  // Si no se encontró la consola, retornar null
    }

    // Método para generar un HTML con opciones <option> para un formulario select
    public static String generaOpcionMultiple() {
        StringBuilder html = new StringBuilder();
        String consulta = "SELECT id, nombre FROM consolas";
        
        // Establecer la conexión y ejecutar la consulta
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta); 
             ResultSet rs = pstm.executeQuery()) {
            
            // Recorrer los resultados de la consulta y construir las opciones <option> para HTML
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
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return html.toString();  // Retornar las opciones generadas
    }

    // Método sobrecargado para generar opciones <option> con una consola seleccionada
    public static String generaOpcionMultiple(String nombreConsola) {
        StringBuilder html = new StringBuilder();
        String consulta = "SELECT id, nombre FROM consolas";
        
        // Establecer la conexión y ejecutar la consulta
        try (Connection conexion = Conexion.getConexion(); 
             PreparedStatement pstm = conexion.prepareStatement(consulta); 
             ResultSet rs = pstm.executeQuery()) {
            
            // Recorrer los resultados de la consulta y construir las opciones <option> para HTML
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
            e.printStackTrace();  // Imprimir error si ocurre una excepción
        }
        
        return html.toString();  // Retornar las opciones generadas con el valor seleccionado
    }
}
