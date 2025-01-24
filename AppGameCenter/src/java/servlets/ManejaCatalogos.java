/*
 * Este servlet maneja la visualización de los productos en el catálogo de un sitio de venta de videojuegos y consolas.
 * Permite a los usuarios ver consolas, juegos, o todos los productos, además de gestionar el carrito de compras.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import modelo.*;

/**
 *
 * @author Administrator
 */
public class ManejaCatalogos extends HttpServlet {

    /**
     * Método principal que procesa tanto solicitudes GET como POST
     * para mostrar el catálogo de productos.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Verificar si el usuario tiene sesión válida
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return;
        }
        
        // Obtener el parámetro enviado para decidir qué mostrar
        String enviado = request.getParameter("enviado");
        enviado = (enviado == null) ? "verTodo" : enviado;

        // Si el parámetro es "verPlataformas", redirige a la página de juegos por plataforma
        if (enviado.equals("verPlataformas")) {
            response.sendRedirect("ManejaJuegosPorIdServlet");
        }

        // Construir el contenido HTML para el catálogo
        StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"), (String) session.getAttribute("nombreUsuario"));

        // Mostrar consolas si se ha solicitado "verConsolas" o "verTodo"
        if (enviado.equals("verConsolas") || enviado.equals("verTodo")) {
            html.append(muestraElemento(obtenerConsolas(), enviado));
        }
        
        // Mostrar juegos si se ha solicitado "verJuegos" o "verTodo"
        if (enviado.equals("verJuegos") || enviado.equals("verTodo")) {
            html.append(muestraElemento(obtenerJuegos(), enviado));
        }

        // Enviar el contenido HTML al cliente
        try (PrintWriter out = response.getWriter()) {
            out.println(html.append(contenidoCatalogoFooter()).toString());
        }
    }

    /**
     * Obtiene la sesión del usuario si es válida. Si no tiene sesión, redirige al inicio.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @return la sesión válida o null si no está válida
     * @throws IOException si ocurre un error de entrada/salida
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html");
            return null;
        }
        return session;
    }

    /**
     * Método que muestra los productos en el catálogo dependiendo de la opción seleccionada.
     * Se ajusta si se seleccionó "verTodo" o una vista más detallada.
     *
     * @param lista lista de productos (puede ser consolas o juegos)
     * @param opcionCatalogo opción seleccionada por el usuario ("verTodo", etc.)
     * @return HTML generado para mostrar los productos
     */
    private static String muestraElemento(ArrayList<? extends Producto> lista, String opcionCatalogo) {
        StringBuilder html = new StringBuilder();
        for (Producto producto : lista) {
            // Muestra información parcial si es "verTodo" o información completa de cada producto
            String contenidoInfo = (opcionCatalogo.equals("verTodo") ? producto.mostrarInfoParcial() : producto.mostrarInformacion());
            html.append(contenidoInfo);
        }
        return html.toString();
    }

    /**
     * Obtiene la lista de consolas desde la base de datos usando el DAO de consolas.
     *
     * @return lista de consolas
     */
    private ArrayList<Consola> obtenerConsolas() {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolas();
    }

    /**
     * Obtiene la lista de juegos desde la base de datos usando el DAO de juegos.
     *
     * @return lista de juegos
     */
    private ArrayList<Juego> obtenerJuegos() {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegos();
    }

    /**
     * Genera el encabezado HTML del catálogo, incluyendo el rol del usuario y los botones de navegación.
     * Si el usuario es un administrador, se muestra un botón adicional con opciones de administración.
     *
     * @param rolUsuario rol del usuario (administrador o normal)
     * @param nombreUsuario nombre del usuario
     * @return el contenido del encabezado en HTML
     */
    public static StringBuilder contenidoCatalogoHeader(String rolUsuario, String nombreUsuario) {
        // Si el usuario es administrador, agrega el botón para opciones de administrador
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
                + "                    <button class=\"opcionUsuario\" type=\"button\">" + nombreUsuario + "</button>\n"
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
                + "    <main>\n"
                + "        <form class=\"formulario\" action=\"ManejaCarritoServlet\" method=\"get\">");
        return html;
    }

    /**
     * Genera el pie de página del catálogo, mostrando los derechos de autor.
     *
     * @return el contenido del pie de página en HTML
     */
    private static String contenidoCatalogoFooter() {
        return "     </form>\n"
                + "    </main>\n"
                + "    <footer>\n"
                + "            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>\n"
                + "    </footer>\n"
                + "</body>\n"
                + "</html>";
    }

    // Métodos doGet y doPost se encargan de procesar las solicitudes HTTP.
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
        return "Short description";
    }
}
