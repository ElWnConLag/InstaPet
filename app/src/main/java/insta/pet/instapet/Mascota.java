package insta.pet.instapet;

public class Mascota {
    private String nombre;
    private String raza;
    private String sexo;
    private String tamaño;
    private String imagenUrl; // Agrega un campo para la URL de la imagen

    // Constructor
    public Mascota(String nombre, String raza, String sexo, String tamaño) {
        this.nombre = nombre;
        this.raza = raza;
        this.sexo = sexo;
        this.tamaño = tamaño;
    }

    // Métodos getter y setter para la URL de la imagen
    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    // Otros métodos getter y setter para los campos restantes
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }
}