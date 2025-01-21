package dao;

import java.sql.*;

public class Conexion {

    public static Connection getConexion() {
        String url = "jdbc:mysql://localhost:3306/game_center";
        String user = "daw2";
        String password = "1234";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado correctamente.");
            Connection conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida correctamente.");
            return conexion;
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver MySQL.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
            return null;
        }
    }
}
