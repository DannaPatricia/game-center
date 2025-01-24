/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para gestionar la inserción de nuevos productos (consolas o juegos) en el catálogo.
 * Verifica que el usuario sea un administrador antes de realizar cualquier operación.
 *
 * @author Administrator
 */
public class InsertaProdcutosServlet extends HttpServlet {

    /**
     * Procesa las solicitudes HTTP para métodos GET y POST.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de I/O
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verifica que el usuario esté logueado y sea un administrador
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");
            return;
        }

        // Recupera el tipo de producto a insertar (consola o juego)
        String opcionEnviada = request.getParameter("opcionEnviada");
        if ("consola".equals(opcionEnviada)) {
            // Si es consola, llama al método para insertar consola
            insertaConsola(request, response);
        } else {
            // Si es juego, llama al método para insertar juego
            insertarJuego(request, response);
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
     * Inserta una nueva consola en la base de datos.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws IOException si ocurre un error de I/O
     */
    private void insertaConsola(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Recupera los parámetros enviados en la solicitud
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String potenciaCpu = request.getParameter("potenciaCpu") != null ? request.getParameter("potenciaCpu") : "";
        String potenciaGpu = request.getParameter("potenciaGpu") != null ? request.getParameter("potenciaGpu") : "";
        String compania = request.getParameter("compania") != null ? request.getParameter("compania") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidadesDisponibles = request.getParameter("unidadesDisponibles") != null ? request.getParameter("unidadesDisponibles") : "";

        // Verifica si todos los valores necesarios están presentes y son válidos
        if (compruebaValores(nombre, potenciaCpu, potenciaGpu, compania, precio, unidadesDisponibles)) {
            ConsolaDAO consolaDao = new ConsolaDAO();
            // Intenta insertar la consola en la base de datos
            boolean insertado = consolaDao.insertarConsola(nombre, Double.parseDouble(potenciaCpu), Double.parseDouble(potenciaGpu), compania, Double.parseDouble(precio), Integer.parseInt(unidadesDisponibles));
            // Redirige dependiendo de si la inserción fue exitosa o no
            if (!insertado) {
                response.sendRedirect("index.html");
            } else {
                response.sendRedirect("ManejaCatalogos");
            }
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Inserta un nuevo juego en la base de datos.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @throws IOException si ocurre un error de I/O
     */
    private void insertarJuego(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Recupera los parámetros enviados en la solicitud
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String companiaDesarrolladora = request.getParameter("companiaDesarrolladora") != null ? request.getParameter("companiaDesarrolladora") : "";
        String genero = request.getParameter("genero") != null ? request.getParameter("genero") : "";
        String puntuacionMetacritic = request.getParameter("puntuacionMetacritic") != null ? request.getParameter("puntuacionMetacritic") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidadesDisponibles = request.getParameter("unidadesDisponibles") != null ? request.getParameter("unidadesDisponibles") : "";
        String plataforma = obtenerNombreId(request)[0];
        String idConsola = obtenerNombreId(request)[1];

        // Verifica si todos los valores necesarios están presentes y son válidos
        if (compruebaValores(nombre, plataforma, companiaDesarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles)) {
            JuegoDAO juegoDAO = new JuegoDAO();
            // Intenta insertar el juego en la base de datos
            boolean insertado = juegoDAO.insertarJuego(nombre, plataforma, companiaDesarrolladora, genero, Double.parseDouble(puntuacionMetacritic), Double.parseDouble(precio), Integer.parseInt(unidadesDisponibles), Integer.parseInt(idConsola));
            // Redirige dependiendo de si la inserción fue exitosa o no
            if (!insertado) {
                response.sendRedirect("index.html");
            } else {
                response.sendRedirect("ManejaCatalogos");
            }
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Obtiene el nombre de la plataforma y el ID de la consola a partir del parámetro "plataforma".
     *
     * @param request solicitud HTTP
     * @return un arreglo con el nombre de la plataforma y el ID de la consola
     */
    private String[] obtenerNombreId(HttpServletRequest request) {
        String plataforma = request.getParameter("plataforma") != null ? request.getParameter("plataforma") : "";
        String[] nombreId = plataforma.split("-");
        return nombreId;
    }

    /**
     * Verifica si los valores necesarios para insertar una consola son válidos.
     *
     * @param nombre nombre de la consola
     * @param potenciaCpu potencia de la CPU de la consola
     * @param potenciaGpu potencia de la GPU de la consola
     * @param compania compañía fabricante de la consola
     * @param precio precio de la consola
     * @param unidadesDisponibles unidades disponibles de la consola
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
     * Verifica si los valores necesarios para insertar un juego son válidos.
     *
     * @param nombre nombre del juego
     * @param plataforma plataforma del juego
     * @param companiaDesarrolladora compañía desarrolladora del juego
     * @param genero género del juego
     * @param puntuacionMetacritic puntuación del juego en Metacritic
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
        return "Servlet que gestiona la inserción de productos (consolas o juegos) en el catálogo.";
    }
}
