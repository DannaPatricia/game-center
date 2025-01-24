/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.*;

/**
 * Servlet que maneja las acciones relacionadas con la compra de productos (consolas y juegos).
 * Verifica si el usuario está autenticado antes de proceder con la compra.
 * 
 * @author Administrator
 */
public class ManejaCarritoServlet extends HttpServlet {

    /**
     * Procesa las solicitudes HTTP tanto para métodos GET como POST.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verifica que el usuario esté autenticado
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            // Si no hay sesión válida, redirige al catálogo
            response.sendRedirect("ManejaCatalogos");
            return;
        }

        // Recupera el tipo de producto y el ID del producto a comprar
        String tipoProducto = obtenerTipoId(request, response)[0];
        String id = obtenerTipoId(request, response)[1];

        // Si el tipo de producto es consola, llama al método para comprar consola
        if ("consola".equals(tipoProducto)) {
            comprarConsola(Integer.parseInt(id), response);
        } else {
            // Si el tipo de producto es juego, llama al método para comprar juego
            comprarJuego(Integer.parseInt(id), response);
        }
    }

    /**
     * Verifica si la sesión del usuario es válida.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @return la sesión válida si existe, o null si no es válida
     * @throws IOException si ocurre un error de I/O
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return null;
        }
        return session;
    }

    /**
     * Obtiene el tipo de producto (consola o juego) y el ID del producto a partir del parámetro "comprar".
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @return un arreglo con el tipo de producto y el ID
     */
    private String[] obtenerTipoId(HttpServletRequest request, HttpServletResponse response) {
        String comprar = request.getParameter("comprar") != null ? request.getParameter("comprar") : "";
        return comprar.split("-");
    }

    /**
     * Maneja la compra de una consola.
     *
     * @param id ID de la consola
     * @param response respuesta HTTP
     * @throws IOException si ocurre un error de I/O
     */
    private void comprarConsola(int id, HttpServletResponse response) throws IOException {
        ConsolaDAO consolaDao = new ConsolaDAO();
        // Intenta realizar la compra de la consola con el ID proporcionado
        boolean comprado = consolaDao.comprarConsola(id);
        // Redirige dependiendo de si la compra fue exitosa o no
        if (!comprado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Maneja la compra de un juego.
     *
     * @param id ID del juego
     * @param response respuesta HTTP
     * @throws IOException si ocurre un error de I/O
     */
    private void comprarJuego(int id, HttpServletResponse response) throws IOException {
        JuegoDAO juegoDao = new JuegoDAO();
        // Intenta realizar la compra del juego con el ID proporcionado
        boolean comprado = juegoDao.compraJuego(id);
        // Redirige dependiendo de si la compra fue exitosa o no
        if (!comprado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Maneja el método HTTP GET.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Maneja el método HTTP POST.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Devuelve una breve descripción del servlet.
     *
     * @return una cadena con la descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet que maneja las compras de productos (consolas y juegos) en el carrito.";
    }// </editor-fold>

}
