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
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author Administrator
 */
public class ManejaJuegosPorIdServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"));
            html.append("<form class=\"formulario\" action=\"ManejaJuegosPorIdServlet\" method=\"post\">");
            html.append(muestraElemento(obtenerConsolas()));
            html.append("</form>");
            html.append(contenidoCatalogoFooter());
            out.println(html);
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
        response.setContentType("text/html;charset=UTF-8");
        String idConsola = request.getParameter("enviado");
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"));
            html.append("<form class=\"formulario\" action=\"ManejaCarritoServlet\" method=\"post\">");
            html.append(muestraElemento(obtenerJuegos(Integer.parseInt(idConsola))));
            html.append("</form>");
            html.append(contenidoCatalogoFooter());
            out.println(html);
        }
    }

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html");
            return null;
        }
        return session;
    }

    private static String muestraElemento(ArrayList<? extends Producto> lista) {
        StringBuilder html = new StringBuilder();
        for (Producto producto : lista) {
            if (producto instanceof Consola) {
                Consola consola = (Consola) producto;
                html.append(consola.muestraBtnConId());
            } else if (producto instanceof Juego) {
                Juego juego = (Juego) producto;
                html.append(juego.mostrarInformacionPorConsola());
            }
        }
        return html.toString();
    }

    private ArrayList<Juego> obtenerJuegos(int idConsola) {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegosPorId(idConsola);
    }

    private static String muestraConsola(ArrayList<Consola> consolas) {
        StringBuilder html = new StringBuilder();
        for (Consola consola : consolas) {
            html.append(consola.muestraBtnConId());
        }
        return html.toString();
    }

    private ArrayList<Consola> obtenerConsolas() {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolas();
    }

    public static StringBuilder contenidoCatalogoHeader(String rolUsuario) {
        String contenidoAdminHtml = ("administrador".equals(rolUsuario) ? "<button class=\"opcionUsuario\" name=\"enviado\" type=\"submit\" value=\"opcionesAdmin\">Opciones de administrador</button>\n" : "");
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Game Center</title>\n"
                + "    <link href=\"styleCatalogo.css\" rel=\"stylesheet\">"
                + "</head>\n"
                + "<body>\n"
                + " <header>\n"
                + "        <div id = \"container-logo-usuarios\">\n"
                + "            <h1 class=\"titulo\">GAME CENTER</h1>"
                + " <nav aria-label=\"ManejaUsuarios\">\n"
                + "                <form action=\"ManejaOpcionesUsuarioServlet\" method = \"get\">\n"
                + "                    <button class=\"opcionUsuario\" type=\"button\">Nombre Usuario</button>\n"
                + "                    <button class=\"opcionUsuario\" name=\"enviado\" type=\"submit\" value=\"verCarrito\">Carrito</button>\n"
                + contenidoAdminHtml
                + "                    <button class=\"opcionUsuario\" name=\"enviado\" type=\"submit\" value=\"cerrarSesion\">Cerrar sesion</button>\n"
                + "                </form>\n"
                + "            </nav>"
                + "        </div>\n"
                + "        <nav aria-label=\"ManejaCatalogos\">\n"
                + "            <form id=\"formulario\" action=\"ManejaCatalogos\" method=\"post\">\n"
                + "                <div class=\"containerFormulario\">\n"
                + "                    <button class=\"opcion\" name=\"enviado\" type=\"submit\" value=\"verConsolas\">Ver nuestras Consolas</button>\n"
                + "                    <button class=\"opcion\" name=\"enviado\" type=\"submit\" value=\"verJuegos\">Ver nuestros Juegos</button>\n"
                + "                    <button class=\"opcion\" name=\"enviado\" type=\"submit\" value=\"verTodo\">Ver Todos nuestros Productos</button>\n"
                + "                    <button class=\"opcion\" name=\"enviado\" type=\"submit\" value=\"verPlataformas\">Ver por Plataforma</button>\n"
                + "                </div>\n"
                + "            </form>\n"
                + "        </nav>\n"
                + "    </header>"
                + "    <main>\n");
        return html;
    }

    private static String contenidoCatalogoFooter() {
        return "    </main>\n"
                + "    <footer>\n"
                + "            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>\n"
                + "    </footer>\n"
                + "</body>\n"
                + "</html>";
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
