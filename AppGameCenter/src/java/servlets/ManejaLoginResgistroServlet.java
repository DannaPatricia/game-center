/*
 * Este servlet maneja tanto el login como el registro de un usuario en el sistema.
 * Permite a los usuarios iniciar sesión con sus credenciales o registrarse si aún no tienen una cuenta.
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
     * Procesa las solicitudes tanto para los métodos HTTP <code>GET</code> como <code>POST</code>.
     * Este método se mantiene vacío, ya que los métodos GET y POST tienen la lógica implementada por separado.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Haz clic en el + para editar el código.">

    /**
     * Maneja las solicitudes HTTP GET.
     * Este método maneja las solicitudes de login, validando el nombre de usuario y la clave proporcionados por el usuario.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            // Recupera los parámetros de nombre de usuario y clave desde la solicitud
            String nombreUsuario = request.getParameter("nombreUsuario");
            String clave = request.getParameter("clave");
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Comprueba si el usuario existe y sus credenciales son correctas
            Usuario usuario = usuarioDAO.comprobarUsuario(nombreUsuario, clave);
            if (usuario != null) {
                // Si el usuario es válido, inicia la sesión
                iniciarSesion(request, response, usuario);
            } else {
                // Si las credenciales no son correctas, redirige al inicio
                response.sendRedirect("index.html");
            }
        }
    }

    /**
     * Maneja las solicitudes HTTP POST.
     * Este método maneja las solicitudes de registro de usuario, validando los datos proporcionados por el usuario.
     *
     * @param request solicitud del servlet
     * @param response respuesta del servlet
     * @throws ServletException si ocurre un error específico del servlet
     * @throws IOException si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera los parámetros proporcionados en el formulario de registro
        String nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        String apellidos = request.getParameter("apellidos") != null ? request.getParameter("apellidos") : "";
        String email = request.getParameter("email") != null && esEmailValido(request.getParameter("email")) ? request.getParameter("email") : "";
        String nombreUsuario = request.getParameter("nombreUsuario") != null ? request.getParameter("nombreUsuario") : "";
        String clave = request.getParameter("clave") != null ? request.getParameter("clave") : "";

        // Verifica si el correo electrónico es válido y si todos los campos son correctos
        if (esEmailValido(email) && compruebaValores(nombre, apellidos, email, nombreUsuario, clave)) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            // Registra al usuario si los datos son válidos
            Usuario usuarioRegistrado = usuarioDAO.registrarUsuario(nombre, apellidos, nombreUsuario, clave, email);
            try (PrintWriter out = response.getWriter()) {
                if (usuarioRegistrado != null) {
                    // Si el registro es exitoso, inicia la sesión para el usuario registrado
                    iniciarSesion(request, response, usuarioRegistrado);
                } else {
                    // Si el registro falla, redirige al inicio
                    response.sendRedirect("index.html");
                }
            }
        } else {
            // Si hay errores, procesa los errores y redirige al formulario de registro
            procesarErrores(request, email, nombre, apellidos, nombreUsuario, clave);
            request.getRequestDispatcher("formularioRegistro.jsp").forward(request, response);
        }
    }

    /**
     * Inicia la sesión para un usuario específico.
     * Establece los atributos de la sesión y la redirige al catálogo de productos.
     *
     * @param request solicitud HTTP
     * @param response respuesta HTTP
     * @param usuario el usuario que acaba de iniciar sesión
     * @throws IOException si ocurre un error de entrada/salida
     */
    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws IOException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800); // 30 minutos de sesión inactiva máxima
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("nombreUsuario", usuario.getNombreUsuario());
        session.setAttribute("rol", usuario.getRol());
        response.sendRedirect("ManejaCatalogos"); // Redirige al catálogo
    }

    /**
     * Procesa los errores de validación y establece los mensajes de error.
     *
     * @param request solicitud HTTP
     * @param email el correo electrónico del usuario
     * @param nombre el nombre del usuario
     * @param apellidos los apellidos del usuario
     * @param nombreUsuario el nombre de usuario
     * @param clave la clave del usuario
     */
    private void procesarErrores(HttpServletRequest request, String email, String nombre, String apellidos, String nombreUsuario, String clave) {
        if (!esEmailValido(email)) {
            request.setAttribute("errorEmail", "El correo electrónico no es válido");
        }
        if (!compruebaValores(nombre, apellidos, email, nombreUsuario, clave)) {
            request.setAttribute("errorMessage", "Debe completar todos los campos");
        }
        // Establece los valores de los campos en caso de error
        request.setAttribute("nombre", nombre);
        request.setAttribute("apellidos", apellidos);
        request.setAttribute("email", email);
        request.setAttribute("nombreUsuario", nombreUsuario);
    }

    /**
     * Verifica que todos los campos necesarios no estén vacíos.
     *
     * @param nombre el nombre del usuario
     * @param apellidos los apellidos del usuario
     * @param email el correo electrónico
     * @param nombreUsuario el nombre de usuario
     * @param clave la clave
     * @return true si todos los campos están completos, false si alguno está vacío
     */
    private boolean compruebaValores(String nombre, String apellidos, String email, String nombreUsuario, String clave) {
        String[] valores = {nombre, apellidos, email, nombreUsuario, clave};
        for (String valor : valores) {
            if (valor == null || valor.isEmpty()) {
                return false; // Si algún campo está vacío, retorna false
            }
        }
        return true; // Si todos los campos están completos, retorna true
    }

    /**
     * Verifica si el correo electrónico es válido mediante una expresión regular.
     *
     * @param email el correo electrónico a verificar
     * @return true si el correo electrónico tiene un formato válido, false en caso contrario
     */
    private boolean esEmailValido(String email) {
        if (email != null && !email.isEmpty()) {
            // Expresión regular básica para validar el formato de correo electrónico
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            // Compila la expresión regular
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches(); // Si el correo coincide con el patrón, es válido
        }
        return false; // Si el correo es nulo o vacío, no es válido
    }

    /**
     * Devuelve una breve descripción del servlet.
     *
     * @return una cadena con la descripción del servlet
     */
    @Override
    public String getServletInfo() {
        return "Short description"; // Descripción corta del servlet
    }// </editor-fold>

}
