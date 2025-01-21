/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package formularios;

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
public class FormularioOpcionesAdministradorSerlvet extends HttpServlet {

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
            response.sendRedirect("index.html");
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(generaFormulario());
        }
    }

    private String generaFormulario() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <title>Game Center</title>\n"
                + "        <link href=\"style.css\" rel=\"stylesheet\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <header>\n"
                + "            <h1 class=\"titulo\">GAME CENTER</h1>\n"
                + "        </header>\n"
                + "        <main>\n"
                + "            <section class=\"login-container\">\n"
                + "                <h2>Opciones de administrador</h2>\n"
                + "                <form class=\"formulario\" action=\"ManejaOpcionesAdministradorServlet\" method=\"get\">\n"
                + "                    <div class=\"input-group\">\n"
                + "                        <button class=\"btn btn-primary\" name=\"opcionUsuario\" type=\"submit\" value=\"insertar\">Insertar nuevo producto</button>    \n"
                + "                    </div>\n"
                + "                    <div class=\"input-group\">\n"
                + "                        <button class=\"btn btn-primary\" name=\"opcionUsuario\" type=\"submit\" value=\"modificar\">Modificar producto</button>    \n"
                + "                    </div>\n"
                + "                    <div class=\"input-group\">\n"
                + "                        <button class=\"btn btn-primary\" name=\"opcionUsuario\" type=\"submit\" value=\"eliminar\">Eliminar producto</button>    \n"
                + "                    </div>\n"
                + "                </form>\n"
                + "                <button class=\"volver\" name=\"opcionUsuario\" type=\"submit\" value=\"eliminar\"><a href=\"ManejaCatalogos\" class = \"enlaceVolver\">Volver</a></button>    \n"
                + "            </section>\n"
                + "        </main>\n"
                + "        <footer>\n"
                + "            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>\n"
                + "        </footer>\n"
                + "    </body>\n"
                + "</html>");
        return html.toString();
    }

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html");
            return null;
        }
        return session;
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
