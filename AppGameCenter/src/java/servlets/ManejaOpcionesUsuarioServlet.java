/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet que maneja las opciones disponibles para los usuarios.
 * Permite al usuario gestionar opciones como ir a la página de administración o cerrar sesión.
 *
 * @author Administrator
 */
public class ManejaOpcionesUsuarioServlet extends HttpServlet {

    /**
     * Procesa las solicitudes tanto para los métodos HTTP GET como POST.
     * Este método se encarga de dirigir las peticiones según el parámetro "enviado".
     * Si el usuario es válido, redirige a diferentes páginas dependiendo de la acción solicitada.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Verificar si la sesión del usuario es válida
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return; // Si no hay sesión válida, terminamos la ejecución
        }

        // Obtener el parámetro enviado para determinar qué acción tomar
        String enviado = request.getParameter("enviado");
        
        // Si el parámetro enviado es "opcionesAdmin", redirige al formulario de opciones del administrador
        if ("opcionesAdmin".equals(enviado)) {
            request.getRequestDispatcher("FormularioOpcionesAdministradorSerlvet").forward(request, response);
        } 
        // Si el parámetro enviado es "cerrarSesion", invalida la sesión y redirige al inicio
        else if ("cerrarSesion".equals(enviado)) {
            session.invalidate(); // Invalida la sesión actual
            response.sendRedirect("index.html"); // Redirige al usuario a la página de inicio
        } 
        // Este bloque está comentado, pero normalmente redirigiría al usuario a su carrito
        // else if ("verCarrito".equals(enviado)) {
        //    response.sendRedirect("ManejaCarritoServlet");
        // }
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos del HttpServlet. Haz clic en el + para expandir.">
    
    /**
     * Maneja las solicitudes HTTP GET.
     * Llama al método processRequest para procesar la solicitud.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja las solicitudes HTTP POST.
     * Llama al método processRequest para procesar la solicitud.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Verifica si la sesión del usuario es válida.
     * Si la sesión no es válida o el usuario no está autenticado, redirige a la página de inicio.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @return la sesión del usuario si es válida, o redirige al inicio si no lo es
     * @throws IOException si ocurre un error de entrada/salida
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Obtiene la sesión sin crear una nueva
        // Verifica si la sesión es nula o si no tiene el atributo "usuarioId" que indica que está autenticado
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html"); // Redirige a la página de inicio si la sesión no es válida
            return null;
        }
        return session; // Devuelve la sesión si es válida
    }

    /**
     * Devuelve una descripción corta del servlet.
     *
     * @return una cadena que contiene una descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet que maneja las opciones del usuario, como cerrar sesión o redirigir a la página de administración.";
    }// </editor-fold>

}
