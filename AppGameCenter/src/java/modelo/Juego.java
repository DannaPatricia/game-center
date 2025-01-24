package modelo;

import dao.ConsolaDAO;

// La clase Juego extiende de Producto, lo que indica que es un tipo especializado de Producto (un juego en este caso).
public class Juego extends Producto {

    // Atributos específicos de un juego
    private String plataforma;            // Plataforma en la que se juega el juego (PC, Xbox, PlayStation, etc.)
    private String companiaDesarrolladora; // Compañía que desarrolló el juego (Ej. Rockstar Games)
    private String genero;                // Género del juego (Ej. Shooter, Aventura, RPG, etc.)
    private double puntuacionMetacritic;  // Puntuación del juego en Metacritic
    private int consolaId;                // ID de la consola a la que está asociado este juego (relacionado con Consola)

    // Constructor vacío, llama al constructor de la clase base Producto.
    public Juego() {
        super();
    }

    // Constructor que solo inicializa el ID, llamando al constructor de la clase base.
    public Juego(int id) {
        super(id);
    }

    // Getters y setters para los atributos específicos del juego

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getConsolaId() {
        return consolaId;
    }

    public void setConsolaId(int consolaId) {
        this.consolaId = consolaId;
    }

    public String getCompaniaDesarrolladora() {
        return companiaDesarrolladora;
    }

    public void setCompaniaDesarrolladora(String companiaDesarrolladora) {
        this.companiaDesarrolladora = companiaDesarrolladora;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPuntuacionMetacritic() {
        return puntuacionMetacritic;
    }

    public void setPuntuacionMetacritic(double puntuacionMetacritic) {
        this.puntuacionMetacritic = puntuacionMetacritic;
    }

    // Método estático para generar un <select> con las plataformas disponibles, obtenidas desde ConsolaDAO
    public static String generaOpcionMultiple() {
        return "<select id=\"plataforma\" name=\"plataforma\">"
                + ConsolaDAO.generaOpcionMultiple()  // Llama al método de ConsolaDAO para obtener las opciones
                + "        </select>\n";
    }

    // Método estático para generar un <select> con las plataformas disponibles, con un parámetro de consola adicional
    public static String generaOpcionMultiple(String consola) {
        return "<select id=\"plataforma\" name=\"plataforma\">"
                + ConsolaDAO.generaOpcionMultiple(consola)  // Llama al método de ConsolaDAO para obtener las opciones con el parámetro 'consola'
                + "        </select>\n";
    }

    // Muestra la información del juego por consola, incluye detalles como el precio, la compañía y el género.
    public String mostrarInformacionPorConsola() {
        return "<div class=\"elementoContainer\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "<button class = \"añadir-carrito\" type=\"submit\" name =\"comprar\"  value=\"juego-" + this.id + "\">Comprar</button>"
                + "<p class = \"info-extra\">Compañía: " + this.companiaDesarrolladora + "</p>"
                + "<p class = \"info-extra\">Género: " + this.genero + "</p>"
                + "<p class = \"info-extra\">Puntuación Metacritic: " + this.puntuacionMetacritic + "</p>"
                + "<p class = \"info-extra\">Unidades disponibles: " + this.unidadesDisponibles + "</p>"
                + "</div>";
    }

    // Muestra una vista más simple del juego con solo nombre, precio y un botón para comprar
    @Override
    public String mostrarInfoParcial() {
        return "<div class=\"elementoContainer\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "<button class = \"añadir-carrito\" type=\"submit\" name =\"comprar\"  value=\"juego-" + this.id + "\">Comprar</button>"
                + "<p class = \"info-extra\">Unidade disponibles: " + this.unidadesDisponibles + "</p>"
                + "</div>";
    }

    // Muestra la información del juego en el panel de administración, permitiendo realizar acciones como modificar, eliminar, etc.
    @Override
    public String mostrarParaAdmin(String opcion) {
        return "<div class=\"contenedorAdmin\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<div class=\"textoAdmin\">"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "</div>"
                + "<button class = \"opcionAdmin\" type=\"submit\" name =\"opcionProducto\"  value=\"juego-" + opcion + "-" + this.id + "\">" + opcion + "</button>"
                + "</div>";
    }

    // Muestra toda la información detallada del juego, incluyendo la plataforma, la compañía desarrolladora, género y la puntuación en Metacritic.
    @Override
    public String mostrarInformacion() {
        return "<div class=\"elementoContainer\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "<button class = \"añadir-carrito\" type=\"submit\" name =\"comprar\"  value=\"juego-" + this.id + "\">Comprar</button>"
                + "<p class = \"info-extra\">Plataforma: " + this.plataforma + "</p>"
                + "<p class = \"info-extra\">Compañía: " + this.companiaDesarrolladora + "</p>"
                + "<p class = \"info-extra\">Género: " + this.genero + "</p>"
                + "<p class = \"info-extra\">Puntuación Metacritic: " + this.puntuacionMetacritic + "</p>"
                + "<p class = \"info-extra\">Unidades disponibles: " + this.unidadesDisponibles + "</p>"
                + "</div>";
    }

    // Simula la compra del juego, disminuyendo las unidades disponibles
    @Override
    public void comprarProducto() {
        if (unidadesDisponibles > 0) {
            unidadesDisponibles--;  // Disminuye las unidades disponibles
            System.out.println("Compra realizada de " + nombre + ". Unidades disponibles ahora: " + unidadesDisponibles);
        } else {
            System.out.println("No hay unidades disponibles de " + nombre);  // Si no hay unidades disponibles, muestra un mensaje.
        }
    }

    // Genera un formulario HTML para agregar un nuevo juego, con campos para ingresar el nombre, plataforma, compañía, género, puntuación, etc.
    public static String generaContenidoFormulario() {
        StringBuilder html = new StringBuilder();
        html.append(" <div class=\"input-group\">\n"
                + "            <label for=\"nombre\">Nombre:</label>\n"
                + "            <input type=\"text\" id=\"nombre\" name=\"nombre\" placeholder=\"Ej: Halo Infinite, FIFA 21\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"plataforma\">Plataforma:</label>\n"
                + generaOpcionMultiple()  // Incluye un desplegable de plataformas
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"companiaDesarrolladora\">Compañía Desarrolladora:</label>\n"
                + "            <input type=\"text\" id=\"companiaDesarrolladora\" name=\"companiaDesarrolladora\" placeholder=\"Ej: Rockstar Games, EA Sports\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"genero\">Género:</label>\n"
                + "            <input type=\"text\" id=\"genero\" name=\"genero\" placeholder=\"Ej: Shooter, Aventura, RPG\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"puntuacionMetacritic\">Puntuación Metacritic:</label>\n"
                + "            <input type=\"number\" id=\"puntuacionMetacritic\" name=\"puntuacionMetacritic\" step=\"0.01\" placeholder=\"Ej: 9.99\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"precio\">Precio (USD):</label>\n"
                + "            <input type=\"number\" id=\"precio\" name=\"precio\" step=\"0.01\" placeholder=\"Ej: 59.99\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"unidadesDisponibles\">Unidades Disponibles:</label>\n"
                + "            <input type=\"number\" id=\"unidadesDisponibles\" name=\"unidadesDisponibles\" placeholder=\"Ej: 100, 200\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <button type=\"submit\" class=\"btn btn-primary\" name = \"opcionEnviada\" value = \"juego\">Insertar</button>"
                + "        </div>");
        return html.toString();  // Devuelve el formulario completo como una cadena HTML
    }
}
