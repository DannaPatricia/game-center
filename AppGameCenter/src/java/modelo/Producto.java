package modelo;

// Clase abstracta Producto, que define las propiedades y métodos comunes para todos los productos en el sistema.
// Las clases que hereden de Producto, como Juego, Consola, etc., deberán implementar los métodos abstractos.
public abstract class Producto {

    // Atributos comunes para todos los productos
    protected int id;                        // Identificador único del producto
    protected String nombre;                 // Nombre del producto
    protected double precio;                 // Precio del producto
    protected int unidadesDisponibles;       // Unidades disponibles del producto
    protected int cantidadComprar;           // Cantidad de unidades que el usuario desea comprar

    // Constructor que recibe los parámetros necesarios para inicializar un Producto
    public Producto(int id, String nombre, double precio, int unidadesDisponibles) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    // Constructor vacío, útil para inicialización sin parámetros
    public Producto() {
    }

    // Constructor que solo recibe el id del producto, útil para recuperar un producto por su ID.
    public Producto(int id) {
        this.id = id;
    }

    // Método abstracto que debe ser implementado por las clases que hereden de Producto.
    // Este método deberá retornar una representación en String de la información completa del producto.
    public abstract String mostrarInformacion();

    // Método abstracto para mostrar la información del producto en el panel de administración.
    // También debe ser implementado por las clases que hereden de Producto.
    public abstract String mostrarParaAdmin(String opcionAdmin);

    // Método abstracto para procesar la compra de un producto, decrementando las unidades disponibles.
    // Debe ser implementado por las clases que hereden de Producto.
    public abstract void comprarProducto();

    // Método abstracto para mostrar una vista más simplificada del producto.
    public abstract String mostrarInfoParcial();

    // Getter y setter para la cantidad de unidades que el usuario quiere comprar
    public int getCantidadComprar() {
        return cantidadComprar;
    }

    public void setCantidadComprar(int cantidadComprar) {
        this.cantidadComprar = cantidadComprar;
    }

    // Métodos getter y setter para los atributos de Producto

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }
}
