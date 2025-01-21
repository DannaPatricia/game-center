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
import modelo.Usuario;
import dao.UsuarioDAO;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class ManejaLoginResgistroServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            String nombreUsuario = request.getParameter("nombreUsuario");
            String clave = request.getParameter("clave");
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.comprobarUsuario(nombreUsuario, clave);
            if (usuario != null) {
                iniciarSesion(request, response, usuario);
            } else {
                response.sendRedirect("index.html");
            }
        }
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
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String apellidos = request.getParameter("apellidos") != null ? request.getParameter("apellidos") : "";
        String email = request.getParameter("email") != null && esEmailValido(request.getParameter("email")) ? request.getParameter("email") : "";
        String nombreUsuario = request.getParameter("nombreUsuario") != null ? request.getParameter("nombreUsuario") : "";
        String clave = request.getParameter("clave") != null ? request.getParameter("clave") : "";

        if (esEmailValido(email) && compruebaValores(nombre, apellidos, email, nombreUsuario, clave)) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioRegistrado = usuarioDAO.registrarUsuario(nombre, apellidos, nombreUsuario, clave, email);
            try (PrintWriter out = response.getWriter()) {
                if (usuarioRegistrado != null) {
                    iniciarSesion(request, response, usuarioRegistrado);
                } else {
                    response.sendRedirect("index.html");
                }
            }
        } else {
            procesarErrores(request, email, nombre, apellidos, nombreUsuario, clave);
            request.getRequestDispatcher("formularioRegistro.jsp").forward(request, response);
        }
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws IOException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800); // 30 minutos
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("nombreUsuario", usuario.getNombreUsuario());
        session.setAttribute("rol", usuario.getRol());
        response.sendRedirect("ManejaCatalogos");
    }

    private void procesarErrores(HttpServletRequest request, String email, String nombre, String apellidos, String nombreUsuario, String clave) {
        if (!esEmailValido(email)) {
            request.setAttribute("errorEmail", "El correo electrónico no es válido");
        }
        if (!compruebaValores(nombre, apellidos, email, nombreUsuario, clave)) {
            request.setAttribute("errorMessage", "Debe completar todos los campos");
        }
        request.setAttribute("nombre", nombre);
        request.setAttribute("apellidos", apellidos);
        request.setAttribute("email", email);
        request.setAttribute("nombreUsuario", nombreUsuario);
    }

    private boolean compruebaValores(String nombre, String apellidos, String email, String nombreUsuario, String clave) {
        String[] valores = {nombre, apellidos, email, nombreUsuario, clave};
        for (String valor : valores) {
            if (valor == null || valor.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean esEmailValido(String email) {
        if (email != null && !email.isEmpty()) {
            // Expresión regular para un formato básico de correo electrónico
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            // Compilamos la expresión regular
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            // Si el correo cumple con la expresión regular, es válido
            return matcher.matches();
        } else {
            return false;
        }
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
