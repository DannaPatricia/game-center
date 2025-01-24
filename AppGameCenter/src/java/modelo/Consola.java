package modelo;

import dao.ConsolaDAO;

// La clase Consola extiende de Producto, lo que sugiere que es un tipo especializado de Producto.
public class Consola extends Producto {

    // Atributos específicos de la consola
    private double potenciaCpu;  // Potencia del procesador en GHz
    private double potenciaGpu;  // Potencia de la tarjeta gráfica en TFLOPS
    private String compania;     // Compañía fabricante de la consola (Sony, Microsoft, etc.)

    // Constructor vacío, llama al constructor de la clase base Producto.
    public Consola() {
        super();
    }

    // Constructor que solo inicializa el ID, llamando al constructor de la clase base.
    public Consola(int id) {
        super(id);
    }

    // Constructor que inicializa todos los atributos (incluidos los heredados de Producto)
    public Consola(int id, String nombre, double precio, int unidadesDisponibles, double potenciaCpu, double potenciaGpu, String compania) {
        super(id, nombre, precio, unidadesDisponibles);  // Inicializa los atributos de Producto
        this.potenciaCpu = potenciaCpu;
        this.potenciaGpu = potenciaGpu;
        this.compania = compania;
    }

    // Getters y setters para los atributos específicos de la consola

    public double getPotenciaCpu() {
        return potenciaCpu;
    }

    public void setPotenciaCpu(double potenciaCpu) {
        this.potenciaCpu = potenciaCpu;
    }

    public double getPotenciaGpu() {
        return potenciaGpu;
    }

    public void setPotenciaGpu(double potenciaGpu) {
        this.potenciaGpu = potenciaGpu;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    // Método estático que genera un elemento <select> con las opciones de consolas disponibles.
    public static String generaOpcionMultiple(String consola) {
        // Llama a un método de ConsolaDAO para generar las opciones basadas en las consolas disponibles en la base de datos
        return "<select id=\"nombre\" name=\"nombre\">"
                + ConsolaDAO.generaOpcionMultiple(consola)
                + "        </select>\n";
    }

    // Muestra un botón para ver detalles del producto, con el id del producto como valor del botón.
    public String muestraBtnConId() {
        return "<div class=\"elementoContainer\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<button class = \"añadir-carrito\" type = \"submit\" name = \"enviado\"  value = \"" + this.id + "\">Ver referencias</button>"
                + "</div>";
    }

    // Método que genera el HTML con la información básica de la consola para mostrarla al cliente.
    @Override
    public String mostrarInfoParcial() {
        return "<div class=\"elementoContainer\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "<button class = \"añadir-carrito\" type=\"submit\" name =\"comprar\"  value=\"consola-" + this.id + "\">Comprar</button>"
                + "<p class = \"info-extra\">Unidade disponibles: " + this.unidadesDisponibles + "</p>"
                + "</div>";
    }

    // Método utilizado para mostrar la consola en la interfaz de administración, permitiendo realizar acciones sobre ella (modificar, eliminar, etc.)
    @Override
    public String mostrarParaAdmin(String opcion) {
        return "<div class=\"contenedorAdmin\">"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<div class=\"textoAdmin\">"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "</div>"
                + "<button class = \"opcionAdmin\" type=\"submit\" name =\"opcionProducto\"  value=\"consola-" + opcion + "-" + this.id + "\">" + opcion + "</button>"
                + "</div>";
    }

    // Genera la información completa de la consola, con detalles como la potencia de la CPU, GPU, y el precio
    @Override
    public String mostrarInformacion() {
        return "<div class=\"elementoContainer\">"
                + "<input type=\"hidden\" name=\"tipoProducto\" value=\"consola\"/>"
                + "<input type=\"hidden\" name=\"idProducto\" value=\"" + this.id + "\" />"
                + "<img src=\"imagenesConsolas/xboxOne.jpg\" alt=\"xboxOne\">\n"
                + "<p class=\"nombreElemento\">" + this.nombre + "</p>"
                + "<p class=\"precio\">" + this.precio + "€</p>"
                + "<button class = \"añadir-carrito\" type=\"submit\" name =\"comprar\"  value=\"consola-" + this.id + "\">Comprar</button>"
                + "<p class = \"info-extra\">Potencia CPU: " + this.potenciaCpu + "</p>"
                + "<p class = \"info-extra\">Potencia GPU: " + this.potenciaGpu + "</p>"
                + "<p class = \"info-extra\">Compañia: " + this.compania + "</p>"
                + "<p class = \"info-extra\">Unidade disponibles: " + this.unidadesDisponibles + "</p>"
                + "</div>";
    }

    // Método para simular la compra de un producto, descontando unidades disponibles.
    @Override
    public void comprarProducto() {
        if (unidadesDisponibles > 0) {
            unidadesDisponibles--;  // Disminuye las unidades disponibles
            System.out.println("Compra realizada de " + nombre + ". Unidades disponibles ahora: " + unidadesDisponibles);
        } else {
            System.out.println("No hay unidades disponibles de " + nombre);  // Si no hay unidades disponibles, muestra un mensaje.
        }
    }

    // Genera el formulario HTML para agregar una nueva consola, con campos como nombre, potencia, precio, etc.
    public static String generaContenidoFormulario() {
        StringBuilder html = new StringBuilder();
        html.append("  <div class=\"input-group\">\n"
                + "            <label for=\"nombre\">Nombre:</label>\n"
                + "            <input type=\"text\" id=\"nombre\" name=\"nombre\" placeholder=\"Ej: PS5, Xbox Series X\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"potenciaCpu\">Potencia CPU (GHz):</label>\n"
                + "            <input type=\"number\" id=\"potenciaCpu\" name=\"potenciaCpu\" step=\"0.01\" placeholder=\"Ej: 3.50\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"potenciaGpu\">Potencia GPU (TFLOPS):</label>\n"
                + "            <input type=\"number\" id=\"potenciaGpu\" name=\"potenciaGpu\" step=\"0.01\" placeholder=\"Ej: 10.30\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"compania\">Compañía:</label>\n"
                + "            <input type=\"text\" id=\"compania\" name=\"compania\" placeholder=\"Ej: Sony, Microsoft, Nintendo\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"precio\">Precio (USD):</label>\n"
                + "            <input type=\"number\" id=\"precio\" name=\"precio\" step=\"0.01\" placeholder=\"Ej: 499.99\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <label for=\"unidadesDisponibles\">Unidades Disponibles:</label>\n"
                + "            <input type=\"number\" id=\"unidadesDisponibles\" name=\"unidadesDisponibles\" placeholder=\"Ej: 120\" required>\n"
                + "        </div>\n"
                + "        <div class=\"input-group\">\n"
                + "            <button type=\"submit\" class=\"btn btn-primary\" name = \"opcionEnviada\" value = \"consola\">Insertar</button>"
                + "        </div>");
        return html.toString();  // Devuelve el formulario HTML generado
    }
}
