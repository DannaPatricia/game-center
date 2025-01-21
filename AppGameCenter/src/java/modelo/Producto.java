package modelo;

public abstract class Producto {

    protected int id;
    protected String nombre;
    protected double precio;
    protected int unidadesDisponibles;
    protected int cantidadComprar;

    public Producto(int id, String nombre, double precio, int unidadesDisponibles) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public Producto() {
    }

    public Producto(int id) {
        this.id = id;
    }

    public abstract String mostrarInformacion();

    public abstract String mostrarParaAdmin(String opcionAdmin);

    public abstract void comprarProducto();

    public abstract String mostrarInfoParcial();

    public int getCantidadComprar() {
        return cantidadComprar;
    }

    public void setCantidadComprar(int cantidadComprar) {
        this.cantidadComprar = cantidadComprar;
    }

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
