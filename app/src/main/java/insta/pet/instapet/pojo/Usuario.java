package insta.pet.instapet.pojo;

public class Usuario {

    //Variables
    private String correo;
    private String followers;
    private String following;
    private String imagenPerfil;
    private String nacionalidad;
    private String nombre;

    //constructor vacio
    public Usuario() {
    }

    //constructor con datos
    public Usuario(String correo, String followers, String following, String imagenPerfil, String nacionalidad, String nombre) {
        this.correo = correo;
        this.followers = followers;
        this.following = following;
        this.imagenPerfil = imagenPerfil;
        this.nacionalidad = nacionalidad;
        this.nombre = nombre;
    }

    //getters y setters

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
