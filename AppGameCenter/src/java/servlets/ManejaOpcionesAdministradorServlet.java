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
public class ManejaOpcionesAdministradorServlet extends HttpServlet {

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
            out.println("<title>Servlet ManejaOpcionesAdministrador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManejaOpcionesAdministrador POSSST at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");
            return;
        }
        String opcionUsuario = request.getParameter("opcionUsuario");
        StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"));
        if ("modificar".equals(opcionUsuario)) {
            html.append(verOpcionModificar());
        } else if ("eliminar".equals(opcionUsuario)) {
            html.append(verOpcionEliminar());
        } else {
            request.getRequestDispatcher("FormularioInsertarProducto").forward(request, response);
        }
        html.append(contenidoCatalogoFooter());
        try (PrintWriter out = response.getWriter()) {
            out.println(html.toString());
        }
    }

    private String verOpcionModificar() {
        StringBuilder html = new StringBuilder();
        html.append("<form class=\"formulario\" action=\"ManejaOpcionesAdministradorServlet\" method=\"post\">");
        html.append(muestraElemento(obtenerConsolas(), "Modificar"));
        html.append(muestraElemento(obtenerJuegos(), "Modificar"));
        html.append("</form>");
        return html.toString();
    }

    private String verOpcionEliminar() {
        StringBuilder html = new StringBuilder();
        html.append("<form class=\"formulario\" action=\"ManejaOpcionesAdministradorServlet\" method=\"post\">");
        html.append(muestraElemento(obtenerConsolas(), "Eliminar"));
        html.append(muestraElemento(obtenerJuegos(), "Eliminar"));
        html.append("</form>");
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
        String opcionProducto = request.getParameter("opcionProducto");
        String[] tipoIdOpcion = opcionProducto.split("-");
        String tipoProducto = tipoIdOpcion[0];
        opcionProducto = tipoIdOpcion[1];
        String idProducto = tipoIdOpcion[2];
        if ("Modificar".equals(opcionProducto)) {
            if ("consola".equals(tipoProducto)) {
                Consola consola = obtenerConsolaSeleccionada(Integer.parseInt(idProducto));
                procesarDatosAFormulario(request, response, consola);
            } else {
                Juego juego = obtenerJuegoSeleccionado(Integer.parseInt(idProducto));
                procesarDatosAFormulario(request, response, juego);
            }
        } else if ("Eliminar".equals(opcionProducto)) {
            if ("consola".equals(tipoProducto)) {
                eliminarConsola(response, Integer.parseInt(idProducto));
            } else {
                eliminarJuego(response, Integer.parseInt(idProducto));
            }
        }
    }

    private void eliminarConsola(HttpServletResponse response, int id) throws IOException {
        ConsolaDAO consolaDao = new ConsolaDAO();
        boolean eliminado = consolaDao.eliminarConsola(id);
        if (!eliminado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    private void eliminarJuego(HttpServletResponse response, int id) throws IOException {
        JuegoDAO juegoDao = new JuegoDAO();
        boolean eliminado = juegoDao.eliminarJuego(id);
        if (!eliminado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    private Consola obtenerConsolaSeleccionada(int id) {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolaPorId(id);
    }

    private Juego obtenerJuegoSeleccionado(int id) {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegoPorId(id);
    }

    private void procesarDatosAFormulario(HttpServletRequest request, HttpServletResponse response, Consola consola) throws ServletException, IOException {
        request.setAttribute("tipoProducto", "consola");
        request.setAttribute("id", consola.getId());
        request.setAttribute("opcionesNombre", Consola.generaOpcionMultiple(consola.getNombre()));
        request.setAttribute("potenciaCpu", consola.getPotenciaCpu());
        request.setAttribute("potenciaGpu", consola.getPotenciaGpu());
        request.setAttribute("compania", consola.getCompania());
        request.setAttribute("precio", consola.getPrecio());
        request.setAttribute("unidadesDisponibles", consola.getUnidadesDisponibles());
        request.getRequestDispatcher("/WEB-INF/jsp/formularioModificarConsola.jsp").forward(request, response);
    }

    private void procesarDatosAFormulario(HttpServletRequest request, HttpServletResponse response, Juego juego) throws ServletException, IOException {
        request.setAttribute("tipoProducto", "juego");
        request.setAttribute("id", juego.getId());
        request.setAttribute("nombre", juego.getNombre());
        request.setAttribute("opcionesPlataforma", Juego.generaOpcionMultiple(juego.getPlataforma()));
        request.setAttribute("companiaDesarrolladora", juego.getCompaniaDesarrolladora());
        request.setAttribute("genero", juego.getGenero());
        request.setAttribute("precio", juego.getPrecio());
        request.setAttribute("puntuacionMetacritic", juego.getPuntuacionMetacritic());
        request.setAttribute("unidadesDisponibles", juego.getUnidadesDisponibles());
        request.getRequestDispatcher("/WEB-INF/jsp/formularioModificarJuego.jsp").forward(request, response);
    }

    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return null;
        }
        return session;
    }

    private static String muestraElemento(ArrayList<? extends Producto> lista, String opcionAdmin) {
        StringBuilder html = new StringBuilder();
        for (Producto producto : lista) {
            html.append(producto.mostrarParaAdmin(opcionAdmin));
        }
        return html.toString();
    }

    private ArrayList<Consola> obtenerConsolas() {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolas();
    }

    private ArrayList<Juego> obtenerJuegos() {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegos();
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
                + "    <main>");
        return html;
    }

    private static String contenidoCatalogoFooter() {
        return "</main>\n"
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
