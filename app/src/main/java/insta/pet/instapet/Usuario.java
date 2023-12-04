package insta.pet.instapet;

public class Usuario {
    private String correo;

    public Usuario() {
        // Constructor vacío requerido para Firebase
    }

    public Usuario(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

