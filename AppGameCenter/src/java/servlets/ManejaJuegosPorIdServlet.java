/*
 * Este servlet maneja la visualización de los juegos de acuerdo a la consola seleccionada.
 * Primero, muestra una lista de consolas y luego, dependiendo de la consola seleccionada, muestra los juegos disponibles para esa consola.
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
     * Este método es llamado tanto para solicitudes GET como POST.
     * Aunque no tiene implementación en este servlet, normalmente este método manejaría solicitudes genéricas.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementación vacía, ya que este método no es utilizado en este servlet específico.
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Haz clic en el + para editar el código.">
    
    /**
     * Maneja las solicitudes HTTP GET.
     * Se utiliza para mostrar las consolas disponibles en el catálogo.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta como HTML
        response.setContentType("text/html;charset=UTF-8");
        
        // Obtener la sesión del usuario (verifica si está autenticado)
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return; // Si no está autenticado, termina la ejecución.
        }

        try (PrintWriter out = response.getWriter()) {
            // Generar el encabezado del catálogo con el rol del usuario
            StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"));
            
            // Crear el formulario para mostrar las consolas
            html.append("<form class=\"formulario\" action=\"ManejaJuegosPorIdServlet\" method=\"post\">");
            
            // Mostrar las consolas disponibles
            html.append(muestraElemento(obtenerConsolas()));
            
            // Cerrar el formulario
            html.append("</form>");
            
            // Agregar el pie de página
            html.append(contenidoCatalogoFooter());
            
            // Escribir el contenido HTML generado en la respuesta
            out.println(html);
        }
    }

    /**
     * Maneja las solicitudes HTTP POST.
     * Se utiliza para mostrar los juegos disponibles para la consola seleccionada por el usuario.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Establecer el tipo de contenido de la respuesta como HTML
        response.setContentType("text/html;charset=UTF-8");
        
        // Obtener el ID de la consola seleccionada desde el formulario
        String idConsola = request.getParameter("enviado");
        
        // Obtener la sesión del usuario (verifica si está autenticado)
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null) {
            return; // Si no está autenticado, termina la ejecución.
        }

        try (PrintWriter out = response.getWriter()) {
            // Generar el encabezado del catálogo con el rol del usuario
            StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"));
            
            // Crear el formulario para añadir juegos al carrito
            html.append("<form class=\"formulario\" action=\"ManejaCarritoServlet\" method=\"post\">");
            
            // Mostrar los juegos para la consola seleccionada
            html.append(muestraElemento(obtenerJuegos(Integer.parseInt(idConsola))));
            
            // Cerrar el formulario
            html.append("</form>");
            
            // Agregar el pie de página
            html.append(contenidoCatalogoFooter());
            
            // Escribir el contenido HTML generado en la respuesta
            out.println(html);
        }
    }

    /**
     * Verifica si el usuario tiene una sesión activa.
     * Si no tiene sesión activa, redirige al usuario a la página de inicio.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @return la sesión del usuario, o null si no está autenticado
     * @throws IOException si ocurre un error de entrada/salida
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        
        // Si la sesión es inválida (el usuario no está autenticado)
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html"); // Redirige al usuario a la página de inicio
            return null; // Devuelve null si no está autenticado
        }
        
        return session; // Devuelve la sesión si está autenticado
    }

    /**
     * Genera el contenido HTML para mostrar productos en el catálogo (consolas o juegos).
     * Dependiendo del tipo de producto (Consola o Juego), se muestran diferentes detalles.
     *
     * @param lista lista de productos (pueden ser consolas o juegos)
     * @return el HTML generado con los productos
     */
    private static String muestraElemento(ArrayList<? extends Producto> lista) {
        StringBuilder html = new StringBuilder();
        
        // Iterar sobre la lista de productos
        for (Producto producto : lista) {
            // Si el producto es una consola
            if (producto instanceof Consola) {
                Consola consola = (Consola) producto;
                html.append(consola.muestraBtnConId()); // Muestra la consola con su botón correspondiente
            } 
            // Si el producto es un juego
            else if (producto instanceof Juego) {
                Juego juego = (Juego) producto;
                html.append(juego.mostrarInformacionPorConsola()); // Muestra el juego con los detalles de la consola
            }
        }
        return html.toString(); // Devuelve el HTML generado
    }

    /**
     * Obtiene la lista de juegos asociados a una consola específica.
     *
     * @param idConsola el ID de la consola
     * @return lista de juegos para la consola seleccionada
     */
    private ArrayList<Juego> obtenerJuegos(int idConsola) {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegosPorId(idConsola); // Llama al DAO para obtener los juegos de la consola
    }

    /**
     * Muestra el contenido HTML para las consolas disponibles.
     * (Este método podría estar duplicado innecesariamente con `muestraElemento` en este caso.)
     *
     * @param consolas lista de consolas disponibles
     * @return HTML con las consolas y botones para seleccionarlas
     */
    private static String muestraConsola(ArrayList<Consola> consolas) {
        StringBuilder html = new StringBuilder();
        for (Consola consola : consolas) {
            html.append(consola.muestraBtnConId()); // Muestra un botón para cada consola
        }
        return html.toString(); // Devuelve el HTML generado para las consolas
    }

    /**
     * Obtiene la lista de consolas disponibles en el catálogo.
     *
     * @return lista de consolas
     */
    private ArrayList<Consola> obtenerConsolas() {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolas(); // Llama al DAO para obtener la lista de consolas
    }

    /**
     * Genera el encabezado del catálogo (incluye los botones de navegación y la información del usuario).
     *
     * @param rolUsuario el rol del usuario (ej. administrador o usuario normal)
     * @return el contenido HTML del encabezado del catálogo
     */
    public static StringBuilder contenidoCatalogoHeader(String rolUsuario) {
        // Si el usuario es administrador, agrega un botón extra para acceder a las opciones de administración
        String contenidoAdminHtml = ("administrador".equals(rolUsuario) ? "<button class=\"opcionUsuario\" name=\"enviado\" type=\"submit\" value=\"opcionesAdmin\">Opciones de administrador</button>\n" : "");
        
        // Construcción del encabezado en HTML
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Game Center</title>\n"
                + "    <link href=\"styleCatalogo.css\" rel=\"stylesheet\">\n"
                + "</head>\n"
                + "<body>\n"
                + " <header>\n"
                + "        <div id = \"container-logo-usuarios\">\n"
                + "            <h1 class=\"titulo\">GAME CENTER</h1>"
                + " <nav aria-label=\"ManejaUsuarios\">\n"
                + "                <form action=\"ManejaOpcionesUsuarioServlet\" method = \"get\">\n"
                + "                    <button class=\"opcionUsuario\" type=\"button\">Nombre Usuario</button>\n"
                + "                    <button class=\"opcionUsuario\" name=\"enviado\" type=\"submit\" value=\"verCarrito\">Carrito</button>\n"
                + contenidoAdminHtml // Si es administrador, muestra el botón de opciones
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

    /**
     * Genera el pie de página del catálogo.
     *
     * @return el contenido HTML del pie de página
     */
    private static String contenidoCatalogoFooter() {
        return "    </main>\n"
                + "    <footer>\n"
                + "            <p>&copy; 2025 Game Center. Todos los derechos reservados.</p>\n"
                + "    </footer>\n"
                + "</body>\n"
                + "</html>";
    }

    /**
     * Retorna una descripción breve del servlet.
     *
     * @return una cadena con la descripción corta del servlet
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
