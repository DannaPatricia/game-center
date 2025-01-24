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
import modelo.*;
import dao.*;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para gestionar las opciones del administrador, como modificar productos
 * o insertar nuevos productos en el catálogo.
 * 
 * @author Administrator
 */
public class EjecutaOpcionesAdministradorServlet extends HttpServlet {

    /**
     * Procesa las solicitudes para ambos métodos HTTP <code>GET</code> y <code>POST</code>.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verifica si la sesión es válida y si el usuario es un administrador
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            // Si no es administrador, redirige al índice
            response.sendRedirect("index.html");
            return;
        }

        // Recupera los parámetros enviados con la solicitud
        String opcionEnviada = request.getParameter("opcionEnviada");
        String idProducto = request.getParameter("idProducto");
        String tipoProducto = request.getParameter("tipoProducto");

        // Llama a los métodos correspondientes dependiendo de la opción enviada
        if ("modificar".equals(opcionEnviada)) {
            if ("consola".equals(tipoProducto)) {
                modificarConsola(request, response, Integer.parseInt(idProducto));
            } else {
                modificarJuego(request, response, Integer.parseInt(idProducto));
            }
        } else if ("insertar".equals(opcionEnviada)) {
            // Redirige a la página de inserción de producto
            response.sendRedirect("FormularioInsertarProducto");
        }
    }

    /**
     * Verifica si la sesión es válida y si el usuario tiene sesión activa.
     * 
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @return la sesión válida si existe, de lo contrario redirige a "index.html"
     * @throws IOException si ocurre un error de I/O
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        // Si no hay sesión activa o el usuario no tiene sesión válida, redirige
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html");
            return null;
        }
        return session;
    }

    /**
     * Modifica los datos de una consola en el sistema.
     * 
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @param id ID de la consola a modificar
     * @throws IOException si ocurre un error de I/O
     */
    private void modificarConsola(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {
        // Recupera los parámetros de la consola a modificar
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String potenciaCpu = request.getParameter("potenciaCpu") != null ? request.getParameter("potenciaCpu") : "";
        String potenciaGpu = request.getParameter("potenciaGpu") != null ? request.getParameter("potenciaGpu") : "";
        String compania = request.getParameter("compania") != null ? request.getParameter("compania") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidadesDisponibles = request.getParameter("unidadesDisponibles") != null ? request.getParameter("unidadesDisponibles") : "";

        // Verifica si todos los valores son válidos antes de intentar modificar
        if (compruebaValores(nombre, potenciaCpu, potenciaGpu, compania, precio, unidadesDisponibles)) {
            ConsolaDAO consolaDao = new ConsolaDAO();
            // Intenta modificar la consola en la base de datos
            boolean modificado = consolaDao.modificarConsola(id, nombre, Double.parseDouble(potenciaCpu), Double.parseDouble(potenciaGpu), compania, Double.parseDouble(precio), Integer.parseInt(unidadesDisponibles));
            // Redirige dependiendo del resultado
            if (!modificado) {
                response.sendRedirect("index.html");
            } else {
                response.sendRedirect("ManejaCatalogos");
            }
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Modifica los datos de un juego en el sistema.
     * 
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @param id ID del juego a modificar
     * @throws IOException si ocurre un error de I/O
     */
    private void modificarJuego(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {
        // Recupera los parámetros del juego a modificar
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String companiaDesarrolladora = request.getParameter("companiaDesarrolladora") != null ? request.getParameter("companiaDesarrolladora") : "";
        String genero = request.getParameter("genero") != null ? request.getParameter("genero") : "";
        String puntuacionMetacritic = request.getParameter("puntuacionMetacritic") != null ? request.getParameter("puntuacionMetacritic") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidadesDisponibles = request.getParameter("unidadesDisponibles") != null ? request.getParameter("unidadesDisponibles") : "";
        String plataforma = obtenerNombreId(request)[0];
        String idConsola = obtenerNombreId(request)[1];

        // Verifica si todos los valores son válidos antes de intentar modificar
        if (compruebaValores(nombre, plataforma, companiaDesarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles)) {
            JuegoDAO juegoDAO = new JuegoDAO();
            // Intenta modificar el juego en la base de datos
            boolean modificado = juegoDAO.modificarJuego(id, nombre, plataforma, companiaDesarrolladora, genero, Double.parseDouble(puntuacionMetacritic), Double.parseDouble(precio), Integer.parseInt(unidadesDisponibles), Integer.parseInt(idConsola));
            // Redirige dependiendo del resultado
            if (!modificado) {
                response.sendRedirect("index.html");
            } else {
                response.sendRedirect("ManejaCatalogos");
            }
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Extrae y divide el valor de la plataforma en el formulario (plataforma-idConsola).
     * 
     * @param request solicitud HTTP
     * @return arreglo con el nombre de la plataforma y el ID de la consola
     */
    private String[] obtenerNombreId(HttpServletRequest request) {
        String plataforma = request.getParameter("plataforma") != null ? request.getParameter("plataforma") : "";
        String[] nombreId = plataforma.split("-");
        return nombreId;
    }

    /**
     * Verifica que todos los valores necesarios para modificar un producto sean válidos.
     * 
     * @param nombre nombre del producto
     * @param potenciaCpu potencia de la CPU (si es una consola)
     * @param potenciaGpu potencia de la GPU (si es una consola)
     * @param compania nombre de la compañía (si es una consola)
     * @param precio precio del producto
     * @param unidadesDisponibles unidades disponibles del producto
     * @return true si todos los valores son válidos, false en caso contrario
     */
    private boolean compruebaValores(String nombre, String potenciaCpu, String potenciaGpu, String compania, String precio, String unidadesDisponibles) {
        String[] valores = {nombre, potenciaCpu, potenciaGpu, compania, precio, unidadesDisponibles};
        for (String valor : valores) {
            if (valor == null || valor.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica que todos los valores necesarios para modificar un juego sean válidos.
     * 
     * @param nombre nombre del juego
     * @param plataforma plataforma del juego
     * @param companiaDesarrolladora compañía desarrolladora del juego
     * @param genero género del juego
     * @param puntuacionMetacritic puntuación de Metacritic del juego
     * @param precio precio del juego
     * @param unidadesDisponibles unidades disponibles del juego
     * @return true si todos los valores son válidos, false en caso contrario
     */
    private boolean compruebaValores(String nombre, String plataforma, String companiaDesarrolladora, String genero, String puntuacionMetacritic, String precio, String unidadesDisponibles) {
        String[] valores = {nombre, plataforma, companiaDesarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles};
        for (String valor : valores) {
            if (valor == null || valor.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Métodos HTTP para gestionar las solicitudes GET y POST
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que ejecuta las opciones del administrador para modificar o insertar productos.";
    }
}
