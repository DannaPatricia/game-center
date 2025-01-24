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
 * Servlet encargado de mostrar y procesar el formulario de inserción de productos (consolas o juegos).
 */
public class FormularioInsertarProducto extends HttpServlet {

    /**
     * Método para procesar las solicitudes tanto de tipo GET como POST.
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Genera el contenido HTML básico de la página
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Formulario Insertar Producto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Formulario Insertar Producto en " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Verifica si la sesión es válida y si el usuario está autenticado con el rol adecuado.
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @return La sesión válida si existe, o null si la sesión es inválida.
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);  // Obtiene la sesión sin crear una nueva
        if (session == null || session.getAttribute("usuarioId") == null) {
            return null;  // Si la sesión es nula o no tiene usuarioId, la sesión no es válida
        }
        return session;  // Si la sesión es válida, se devuelve la sesión
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HTTP. Haz clic en el + para editar.">

    /**
     * Maneja las solicitudes GET para mostrar el formulario de inserción de productos.
     * Solo lo podrá acceder un usuario con rol "administrador".
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = obtenerSesionValida(request, response); // Verifica si la sesión es válida
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");  // Redirige si no es administrador
            return;
        }
        try (PrintWriter out = response.getWriter()) {
            StringBuilder html = contenidoHeader();  // Genera el encabezado HTML
            html.append(contenidoMain());  // Genera el cuerpo principal del formulario
            html.append(contenidoFooter());  // Agrega el pie de página
            out.println(html.toString());  // Imprime la respuesta HTML
        }
    }

    /**
     * Genera el contenido principal de la página de inserción de productos.
     * 
     * @return El código HTML correspondiente al formulario de inserción de productos.
     */
    private String contenidoMain() {
        StringBuilder html = new StringBuilder();
        html.append("<main>\n"
                + "            <section class=\"formulario-container\">\n"
                + "                <h1>Insertar producto</h1><br>\n"
                + "                <form class=\"formulario\" action=\"FormularioInsertarProducto\" method=\"post\">\n"
                + "                    <button type=\"submit\" class=\"btn btn-primary\" name = \"opcionEnviada\" value = \"consola\">Consola</button>\n"
                + "                    <button type=\"submit\" class=\"btn btn-primary\" name = \"opcionEnviada\" value = \"juego\">Juego</button>\n"
                + "                </form>  \n"
                + "                <button class=\"volver\" name=\"opcionUsuario\" type=\"button\"><a href=\"ManejaCatalogos\" class = \"enlaceVolver\">Volver</a></button>    \n"
                + "            </section>\n"
                + "        </main>\n"
        );
        return html.toString();  // Devuelve el contenido HTML del formulario
    }

    /**
     * Maneja las solicitudes POST para mostrar el formulario según la opción seleccionada (Consola o Juego).
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = obtenerSesionValida(request, response);  // Verifica si la sesión es válida
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("ManejaCatalogos");  // Redirige si no es administrador
            return;
        }
        String opcionEnviada = request.getParameter("opcionEnviada");  // Obtiene la opción seleccionada
        StringBuilder html = contenidoHeader();  // Genera el encabezado HTML
        html.append(contenidoMainProductos());  // Genera el formulario de productos

        // Dependiendo de la opción enviada, genera el formulario correspondiente
        if ("consola".equals(opcionEnviada)) {
            html.append(Consola.generaContenidoFormulario());  // Contenido para el formulario de Consola
        } else {
            html.append(Juego.generaContenidoFormulario());  // Contenido para el formulario de Juego
        }
        
        html.append("</form></section></main>");
        html.append(contenidoFooter());  // Agrega el pie de página
        try (PrintWriter out = response.getWriter()) {
            out.println(html.toString());  // Imprime la respuesta HTML
        }
    }

    /**
     * Genera el contenido HTML para el formulario de productos (Consolas o Juegos).
     * 
     * @return El código HTML correspondiente al formulario de productos.
     */
    public static String contenidoMainProductos() {
        return "<main><section class=\"formulario-container\">\n"
                + "<form class=\"formulario\" action=\"InsertaProdcutosServlet\" method=\"get\">";
    }

    /**
     * Genera el encabezado HTML común para todas las páginas.
     * 
     * @return El código HTML del encabezado de la página.
     */
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

    /**
     * Genera el pie de página HTML común para todas las páginas.
     * 
     * @return El código HTML del pie de página.
     */
    private static String contenidoFooter() {
        return "    <footer>\n"
                + "            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>\n"
                + "    </footer>\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * Devuelve una breve descripción del servlet.
     * 
     * @return Una cadena con la descripción del servlet.
     */
    @Override
    public String getServletInfo() {
        return "Formulario para insertar productos como consolas o juegos";
    }// </editor-fold>

}
