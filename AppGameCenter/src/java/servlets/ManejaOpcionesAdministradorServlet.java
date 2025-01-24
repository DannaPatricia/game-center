/*
 * Servlet para manejar las opciones de administración del catálogo de productos.
 * Permite a los administradores modificar o eliminar consolas y juegos.
 * Se valida que el usuario sea un administrador y tenga una sesión activa.
 */
package servlets;

import dao.*; // Importación de las clases DAO (Data Access Object) para interactuar con la base de datos.
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import modelo.*; // Importación de los modelos de productos (Consola, Juego).

/**
 * Servlet que maneja las opciones de administración para modificar o eliminar
 * productos en el catálogo (consolas y juegos).
 * 
 * @author Administrator
 */
public class ManejaOpcionesAdministradorServlet extends HttpServlet {

    /**
     * Procesa las solicitudes para los métodos HTTP GET y POST.
     * Este método no se utiliza directamente, ya que la funcionalidad se maneja
     * en los métodos doGet y doPost.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
     * Maneja las solicitudes HTTP GET.
     * Se encarga de mostrar las opciones de modificación o eliminación de productos
     * para los administradores.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            // Si la sesión no es válida o el usuario no es un administrador, redirige al catálogo.
            response.sendRedirect("ManejaCatalogos");
            return;
        }
        
        String opcionUsuario = request.getParameter("opcionUsuario");
        StringBuilder html = contenidoCatalogoHeader((String) session.getAttribute("rol"));
        
        // Dependiendo de la opción seleccionada por el usuario, mostramos un formulario u otro.
        if ("modificar".equals(opcionUsuario)) {
            html.append(verOpcionModificar()); // Mostrar formulario para modificar productos.
        } else if ("eliminar".equals(opcionUsuario)) {
            html.append(verOpcionEliminar()); // Mostrar formulario para eliminar productos.
        } else {
            // Si no se seleccionó modificar ni eliminar, redirige al formulario de inserción de productos.
            request.getRequestDispatcher("FormularioInsertarProducto").forward(request, response);
        }
        
        html.append(contenidoCatalogoFooter()); // Añadir el pie de página.
        
        try (PrintWriter out = response.getWriter()) {
            out.println(html.toString());
        }
    }

    /**
     * Genera el formulario de modificación de productos (consolas y juegos).
     *
     * @return el código HTML del formulario de modificación
     */
    private String verOpcionModificar() {
        StringBuilder html = new StringBuilder();
        html.append("<form class=\"formulario\" action=\"ManejaOpcionesAdministradorServlet\" method=\"post\">");
        html.append(muestraElemento(obtenerConsolas(), "Modificar"));
        html.append(muestraElemento(obtenerJuegos(), "Modificar"));
        html.append("</form>");
        return html.toString();
    }

    /**
     * Genera el formulario de eliminación de productos (consolas y juegos).
     *
     * @return el código HTML del formulario de eliminación
     */
    private String verOpcionEliminar() {
        StringBuilder html = new StringBuilder();
        html.append("<form class=\"formulario\" action=\"ManejaOpcionesAdministradorServlet\" method=\"post\">");
        html.append(muestraElemento(obtenerConsolas(), "Eliminar"));
        html.append(muestraElemento(obtenerJuegos(), "Eliminar"));
        html.append("</form>");
        return html.toString();
    }

    /**
     * Maneja las solicitudes HTTP POST.
     * Se encarga de procesar las opciones de modificación o eliminación de productos.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            // Si la sesión no es válida o el usuario no es un administrador, redirige al catálogo.
            response.sendRedirect("ManejaCatalogos");
            return;
        }

        String opcionProducto = request.getParameter("opcionProducto");
        String[] tipoIdOpcion = opcionProducto.split("-");
        String tipoProducto = tipoIdOpcion[0];
        opcionProducto = tipoIdOpcion[1];
        String idProducto = tipoIdOpcion[2];
        
        // Dependiendo de la opción seleccionada (Modificar o Eliminar), realizamos la acción correspondiente.
        if ("Modificar".equals(opcionProducto)) {
            if ("consola".equals(tipoProducto)) {
                Consola consola = obtenerConsolaSeleccionada(Integer.parseInt(idProducto));
                procesarDatosAFormulario(request, response, consola); // Llamada para modificar consola.
            } else {
                Juego juego = obtenerJuegoSeleccionado(Integer.parseInt(idProducto));
                procesarDatosAFormulario(request, response, juego); // Llamada para modificar juego.
            }
        } else if ("Eliminar".equals(opcionProducto)) {
            if ("consola".equals(tipoProducto)) {
                eliminarConsola(response, Integer.parseInt(idProducto)); // Eliminar consola.
            } else {
                eliminarJuego(response, Integer.parseInt(idProducto)); // Eliminar juego.
            }
        }
    }

    /**
     * Elimina una consola del catálogo.
     *
     * @param response la respuesta del servlet
     * @param id el ID de la consola a eliminar
     * @throws IOException si ocurre un error de entrada/salida
     */
    private void eliminarConsola(HttpServletResponse response, int id) throws IOException {
        ConsolaDAO consolaDao = new ConsolaDAO();
        boolean eliminado = consolaDao.eliminarConsola(id);
        if (!eliminado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Elimina un juego del catálogo.
     *
     * @param response la respuesta del servlet
     * @param id el ID del juego a eliminar
     * @throws IOException si ocurre un error de entrada/salida
     */
    private void eliminarJuego(HttpServletResponse response, int id) throws IOException {
        JuegoDAO juegoDao = new JuegoDAO();
        boolean eliminado = juegoDao.eliminarJuego(id);
        if (!eliminado) {
            response.sendRedirect("index.html");
        } else {
            response.sendRedirect("ManejaCatalogos");
        }
    }

    /**
     * Obtiene los datos de una consola seleccionada.
     *
     * @param id el ID de la consola
     * @return la consola seleccionada
     */
    private Consola obtenerConsolaSeleccionada(int id) {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolaPorId(id);
    }

    /**
     * Obtiene los datos de un juego seleccionado.
     *
     * @param id el ID del juego
     * @return el juego seleccionado
     */
    private Juego obtenerJuegoSeleccionado(int id) {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegoPorId(id);
    }

    /**
     * Procesa los datos de una consola y los pasa al formulario de modificación.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @param consola la consola a modificar
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
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

    /**
     * Procesa los datos de un juego y los pasa al formulario de modificación.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @param juego el juego a modificar
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
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

    /**
     * Obtiene la sesión válida para el usuario.
     *
     * @param request la solicitud del servlet
     * @param response la respuesta del servlet
     * @return la sesión del usuario si es válida, o null si no lo es
     * @throws IOException si ocurre un error de entrada/salida
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            return null;
        }
        return session;
    }

    /**
     * Genera el HTML de los productos en el catálogo para administración (modificación o eliminación).
     *
     * @param lista la lista de productos (consolas o juegos)
     * @param opcionAdmin la opción de administrador (modificar o eliminar)
     * @return el HTML con los productos
     */
    private static String muestraElemento(ArrayList<? extends Producto> lista, String opcionAdmin) {
        StringBuilder html = new StringBuilder();
        for (Producto producto : lista) {
            html.append(producto.mostrarParaAdmin(opcionAdmin));
        }
        return html.toString();
    }

    /**
     * Obtiene todas las consolas del catálogo.
     *
     * @return la lista de consolas
     */
    private ArrayList<Consola> obtenerConsolas() {
        ConsolaDAO consolaDAO = new ConsolaDAO();
        return consolaDAO.obtenerConsolas();
    }

    /**
     * Obtiene todos los juegos del catálogo.
     *
     * @return la lista de juegos
     */
    private ArrayList<Juego> obtenerJuegos() {
        JuegoDAO juegoDAO = new JuegoDAO();
        return juegoDAO.obtenerJuegos();
    }

    /**
     * Genera el HTML para el encabezado del catálogo.
     *
     * @param rolUsuario el rol del usuario (administrador, cliente, etc.)
     * @return el HTML del encabezado
     */
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
                + "                    <button class=\"opcionUsuario\" name=\"enviado\" type=\"submit\" value=\"cerrarSesion\">Cerrar sesión</button>\n"
                + "                </form>\n"
                + "            </nav>"
                + "        </div>\n"
                + "        <nav aria-label=\"ManejaCatalogos\">\n"
                + "            <form id=\"formulario\" action=\"ManejaCatalogos\" method=\"post\">\n"
                + "                <div class=\"containerFormulario\">\n"
                + "                    <button class=\"opcion\" name=\"enviado\" type=\"submit\" value=\"verConsolas\">Ver nuestras consolas</button>\n"
                + "                    <button class=\"opcion\" name=\"enviado\" type=\"submit\" value=\"verJuegos\">Ver nuestros juegos</button>\n"
                + "                </div>\n"
                + "            </form>\n"
                + "        </nav>\n"
                + "</header>");
        return html;
    }

    /**
     * Genera el HTML para el pie de página del catálogo.
     *
     * @return el HTML del pie de página
     */
    public static String contenidoCatalogoFooter() {
        return "<footer>\n"
                + "    <p>&copy; 2025 GAME CENTER</p>\n"
                + "</footer>\n"
                + "</body>\n"
                + "</html>";
    }
}
