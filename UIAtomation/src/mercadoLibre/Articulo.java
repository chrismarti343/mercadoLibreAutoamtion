package mercadoLibre;

public class Articulo {

    private String nombre;
    private String precio;
    private String link;

    public Articulo(String nombre, String precio, String link) {
        this.nombre = nombre;
        this.precio = precio;
        this.link = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
