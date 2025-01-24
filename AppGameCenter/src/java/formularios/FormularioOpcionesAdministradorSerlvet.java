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
 * Servlet que gestiona las opciones disponibles para un administrador en el sistema.
 * Permite mostrar un formulario con opciones para insertar, modificar o eliminar productos.
 */
public class FormularioOpcionesAdministradorSerlvet extends HttpServlet {

    /**
     * Método para procesar las solicitudes tanto de tipo GET como POST.
     * Valida que el usuario sea un administrador antes de mostrar las opciones.
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Verifica si la sesión es válida y si el usuario es un administrador
        HttpSession session = obtenerSesionValida(request, response);
        if (session == null || !"administrador".equals(session.getAttribute("rol"))) {
            response.sendRedirect("index.html");  // Si no es administrador, redirige a la página de inicio
            return;
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println(generaFormulario());  // Genera y envía el formulario HTML para mostrar las opciones
        }
    }

    /**
     * Genera el contenido HTML del formulario de opciones para el administrador.
     * Incluye botones para insertar, modificar y eliminar productos.
     * 
     * @return El código HTML que representa el formulario de opciones.
     */
    private String generaFormulario() {
        StringBuilder html = new StringBuilder();
        
        // Empieza a construir el formulario HTML
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
        
        return html.toString();  // Devuelve el código HTML completo
    }

    /**
     * Obtiene la sesión del usuario y valida que esté autenticado.
     * Si la sesión no es válida, redirige al usuario a la página de inicio.
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @return La sesión del usuario si es válida, o null si no lo es.
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private HttpSession obtenerSesionValida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);  // Obtiene la sesión sin crear una nueva
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendRedirect("index.html");  // Si no hay sesión válida, redirige al inicio
            return null;
        }
        return session;  // Si la sesión es válida, la retorna
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HTTP. Haz clic en el + para editar.">

    /**
     * Maneja las solicitudes GET y delega el procesamiento en el método processRequest.
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);  // Llama al método de procesamiento de solicitudes
    }

    /**
     * Maneja las solicitudes POST y delega el procesamiento en el método processRequest.
     * 
     * @param request Servlet request
     * @param response Servlet response
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);  // Llama al método de procesamiento de solicitudes
    }

    /**
     * Devuelve una breve descripción del servlet.
     * 
     * @return Una cadena con la descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Formulario para opciones de administración del catálogo de productos";
    }// </editor-fold>

}
