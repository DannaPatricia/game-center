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
import modelo.*;

/**
 *
 * @author Administrator
 */
public class FormularioInsertarProducto extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FormularioInsertarProducto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FormularioInsertarProducto at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
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
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            StringBuilder html = contenidoHeader();
            html.append(contenidoMain());
            html.append(contenidoFooter());
            out.println(html.toString());
        }
    }

    private String contenidoMain() {
        StringBuilder html = new StringBuilder();
        html.append("<main>\n"
                + "            <section class=\"formulario-container\">\n"
                + "                <h1>Insertar prodcuto</h1><br>\n"
                + "                <form class=\"formulario\" action=\"FormularioInsertarProducto\" method=\"post\">\n"
                + "                    <button type=\"submit\" class=\"btn btn-primary\" name = \"opcionEnviada\" value = \"consola\">Consola</button>\n"
                + "                    <button type=\"submit\" class=\"btn btn-primary\" name = \"opcionEnviada\" value = \"juego\">Juego</button>\n"
                + "                </form>  \n"
                + "                <button class=\"volver\" name=\"opcionUsuario\" type=\"button\"><a href=\"ManejaCatalogos\" class = \"enlaceVolver\">Volver</a></button>    \n"
                + "            </section>\n"
                + "        </main>\n"
        );
        return html.toString();
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
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");
            return;
        }
        String opcionEnviada = request.getParameter("opcionEnviada");
        StringBuilder html = contenidoHeader();
        html.append(contenidoMainProductos());
        if ("consola".equals(opcionEnviada)) {
            html.append(Consola.generaContenidoFormulario());
        } else {
            html.append(Juego.generaContenidoFormulario());
        }
        html.append("</form></section></main>");
        html.append(contenidoFooter());
        try (PrintWriter out = response.getWriter()) {
            out.println(html.toString());
        }
    }

    public static String contenidoMainProductos() {
        return "<main><section class=\"formulario-container\">\n"
                + "<form class=\"formulario\" action=\"InsertaProdcutosServlet\" method=\"get\">";
    }

    public static StringBuilder contenidoHeader() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Game Center</title>\n"
                + "        <link href=\"style.css\" rel=\"stylesheet\">\n"
                + "</head>\n"
                + "<body>\n"
                + " <header>\n"
                + "            <h1 class=\"titulo\">GAME CENTER</h1>"
                + "    </header>");
        return html;
    }

    private static String contenidoFooter() {
        return "    <footer>\n"
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
