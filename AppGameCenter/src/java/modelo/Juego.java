package modelo;

import dao.ConsolaDAO;

public class Juego extends Producto {

    private String plataforma;
    private String companiaDesarrolladora;
    private String genero;
    private double puntuacionMetacritic;
    private int consolaId;

    public Juego() {
        super();
    }

    public Juego(int id) {
        super(id);
    }

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

    public static String generaOpcionMultiple() {
        return "<select id=\"plataforma\" name=\"plataforma\">"
                + ConsolaDAO.generaOpcionMultiple()
                + "        </select>\n";
    }

    public static String generaOpcionMultiple(String consola) {
        return "<select id=\"plataforma\" name=\"plataforma\">"
                + ConsolaDAO.generaOpcionMultiple(consola)
                + "        </select>\n";
    }

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

    @Override
    public void comprarProducto() {
        if (unidadesDisponibles > 0) {
            unidadesDisponibles--;
            System.out.println("Compra realizada de " + nombre + ". Unidades disponibles ahora: " + unidadesDisponibles);
        } else {
            System.out.println("No hay unidades disponibles de " + nombre);
        }
    }

    public static String generaContenidoFormulario() {
        StringBuilder html = new StringBuilder();
        html.append(" <div class=\"input-group\">\n"
                + "            <label for=\"nombre\">Nombre:</label>\n"
                + "            <input type=\"text\" id=\"nombre\" name=\"nombre\" placeholder=\"Ej: Halo Infinite, FIFA 21\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"plataforma\">Plataforma:</label>\n"
                + generaOpcionMultiple()
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
        return html.toString();
    }
}
