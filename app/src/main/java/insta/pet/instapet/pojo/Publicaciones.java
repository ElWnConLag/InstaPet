package insta.pet.instapet.pojo;

public class Publicaciones {
    private String descripcion;
    private String titulo;

    //contructor vacio
    public Publicaciones() {
    }

    //constructor con datos
    public Publicaciones(String descripcion, String titulo) {
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
