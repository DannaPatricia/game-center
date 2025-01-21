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
 *
 * @author Administrator
 */
public class InsertaProdcutosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");
            return;
        }
        String opcionEnviada = request.getParameter("opcionEnviada");
        if ("consola".equals(opcionEnviada)) {
            insertaConsola(request, response);
        }else{
            insertarJuego(request, response);
        }
    }

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return null;
        }
        return session;
    }

    private void insertaConsola(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String potenciaCpu = request.getParameter("potenciaCpu") != null ? request.getParameter("potenciaCpu") : "";
        String potenciaGpu = request.getParameter("potenciaGpu") != null ? request.getParameter("potenciaGpu") : "";
        String compania = request.getParameter("compania") != null ? request.getParameter("compania") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidadesDisponibles = request.getParameter("unidadesDisponibles") != null ? request.getParameter("unidadesDisponibles") : "";
        if (compruebaValores(nombre, potenciaCpu, potenciaGpu, compania, precio, unidadesDisponibles)) {
            ConsolaDAO consolaDao = new ConsolaDAO();
            boolean insertado = consolaDao.insertarConsola(nombre, Double.parseDouble(potenciaCpu), Double.parseDouble(potenciaGpu), compania, Double.parseDouble(precio), Integer.parseInt(unidadesDisponibles));
            if (!insertado) {
                response.sendRedirect("index.html");
            } else {
                response.sendRedirect("ManejaCatalogos");
            }
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    private void insertarJuego(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String companiaDesarrolladora = request.getParameter("companiaDesarrolladora") != null ? request.getParameter("companiaDesarrolladora") : "";
        String genero = request.getParameter("genero") != null ? request.getParameter("genero") : "";
        String puntuacionMetacritic = request.getParameter("puntuacionMetacritic") != null ? request.getParameter("puntuacionMetacritic") : "";
        String precio = request.getParameter("precio") != null ? request.getParameter("precio") : "";
        String unidadesDisponibles = request.getParameter("unidadesDisponibles") != null ? request.getParameter("unidadesDisponibles") : "";
        String plataforma = obtenerNombreId(request)[0];
        String idConsola = obtenerNombreId(request)[1];
        if (compruebaValores(nombre, plataforma, companiaDesarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles)) {
            JuegoDAO juegoDAO = new JuegoDAO();
            boolean insertado = juegoDAO.insertarJuego(nombre, plataforma, companiaDesarrolladora, genero, Double.parseDouble(puntuacionMetacritic), Double.parseDouble(precio), Integer.parseInt(unidadesDisponibles), Integer.parseInt(idConsola));
            if (!insertado) {
                response.sendRedirect("index.html");
            } else {
                response.sendRedirect("ManejaCatalogos");
            }
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    private String[] obtenerNombreId(HttpServletRequest request) {
        String plataforma = request.getParameter("plataforma") != null ? request.getParameter("plataforma") : "";
        String[] nombreId = plataforma.split("-");
        return nombreId;
    }

    private boolean compruebaValores(String nombre, String potenciaCpu, String potenciaGpu, String compania, String precio, String unidadesDisponibles) {
        String[] valores = {nombre, potenciaCpu, potenciaGpu, compania, precio, unidadesDisponibles};
        for (String valor : valores) {
            if (valor == null || valor.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean compruebaValores(String nombre, String plataforma, String companiaDesarrolladora, String genero, String puntuacionMetacritic, String precio, String unidadesDisponibles) {
        String[] valores = {nombre, plataforma, companiaDesarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles};
        for (String valor : valores) {
            if (valor == null || valor.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
