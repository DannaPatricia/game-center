/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa un Usuario dentro del sistema.
 * Un usuario tiene un identificador único, un nombre de usuario y un rol (por ejemplo: administrador, cliente, etc.).
 * 
 * @author Administrator
 */
public class Usuario {
    
    // Atributos de la clase Usuario
    private int id;            // Identificador único del usuario
    private String nombreUsuario; // Nombre de usuario del usuario
    private String rol;           // Rol del usuario (por ejemplo: "admin", "cliente")

    // Constructor que inicializa los atributos del usuario
    public Usuario(int id, String nombreUsuario, String rol) {
        this.id = id;                 // Establece el id del usuario
        this.nombreUsuario = nombreUsuario; // Establece el nombre de usuario
        this.rol = rol;               // Establece el rol del usuario
    }

    // Método getter para obtener el id del usuario
    public int getId() {
        return id;
    }

    // Método setter para establecer el id del usuario
    public void setId(int id) {
        this.id = id;
    }

    // Método getter para obtener el nombre de usuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // Método setter para establecer el nombre de usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // Método getter para obtener el rol del usuario
    public String getRol() {
        return rol;
    }

    // Método setter para establecer el rol del usuario
    public void setRol(String rol) {
        this.rol = rol;
    }
}
