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
import dao.*;

/**
 *
 * @author Administrator
 */
public class ManejaCarritoServlet extends HttpServlet {

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
        if (session == null) {
            response.sendRedirect("ManejaCatalogos");
            return;
        }
        String tipoProducto = obtenerTipoId(request, response)[0];
        String id = obtenerTipoId(request, response)[1];
        if ("consola".equals(tipoProducto)) {
            comprarConsola(Integer.parseInt(id), response);
        } else {
            comprarJuego(Integer.parseInt(id), response);
        }
    }

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return null;
        }
        return session;
    }

    private String[] obtenerTipoId(HttpServletRequest request, HttpServletResponse response) {
        String comprar = request.getParameter("comprar") != null ? request.getParameter("comprar") : "";
        return comprar.split("-");
    }

    private void comprarConsola(int id, HttpServletResponse response) throws IOException {
        ConsolaDAO consolaDao = new ConsolaDAO();
        boolean comprado = consolaDao.comprarConsola(id);
        if (!comprado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    private void comprarJuego(int id, HttpServletResponse response) throws IOException {
        JuegoDAO juegoDao = new JuegoDAO();
        boolean comprado = juegoDao.compraJuego(id);
        if (!comprado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
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
