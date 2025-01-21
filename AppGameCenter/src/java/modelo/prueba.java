/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import dao.*;
import modelo.*;

/**
 *
 * @author Administrator
 */
public class prueba {

    public static void main(String args[]) {
        ConsolaDAO consoladao = new ConsolaDAO();
        boolean consola = consoladao.modificarConsola(4, "Nintendo Switch", 0.10, 0.40, "Nintendo", 299.99, 207);
        if(consola == false){
             System.out.println("no");
        }else{
             System.out.println("si");
        }
//        UsuarioDAO usuarioDao = new UsuarioDAO();
//        Usuario usuarioRegistrado = usuarioDao.registrarUsuario("elUsuario2", "segundo", "user2", "1234", "user2@gmail.com");
//        if (usuarioRegistrado
//                == null) {
//            System.out.println("hola");
//        } else {
//            System.out.println("no");
//
//        }

    }

    public static Consola obtenerConsolaSeleccionada(int id) {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolaPorId(id);
    }

}
