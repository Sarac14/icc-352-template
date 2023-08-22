package org.example.entidades;

public class Formulario {
    private String id;
    private String name;
    private String level;
    private String sector;
    private String usuario;
    private String longitud;
    private String latitud;

    private String imagenBase64;


    public Formulario(String name, String level, String sector, String usuario, String longitud, String latitud, String imagenBase64) {
        this.name = name;
        this.level = level;
        this.sector = sector;
        this.usuario = usuario;
        this.longitud = longitud;
        this.latitud = latitud;
        this.imagenBase64 = imagenBase64;
    }

    public Formulario() {
    }

    public Formulario(String nombre, String sector, String nivelEscolar, String agente, String longitud, String latitud) {
        this.name = nombre;
        this.level = nivelEscolar;
        this.sector = sector;
        this.usuario = agente;
        this.longitud = longitud;
        this.latitud = latitud;

    }

    public Formulario(String id, String nombre, String nivelEscolar, String sector, String agente, String longitud, String latitud, String foto) {
        this.id = id;
        this.name = nombre;
        this.level = nivelEscolar;
        this.sector = sector;
        this.usuario = agente;
        this.longitud = longitud;
        this.latitud = latitud;
        this.imagenBase64 = foto;

    }

    public Formulario(String nombre, String nivelEscolar, String sector, String agente, String foto) {
        this.name = nombre;
        this.level = nivelEscolar;
        this.sector = sector;
        this.usuario = agente;
        this.imagenBase64 = foto;
    }


    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }
    public String getImagenBase64() {
        return imagenBase64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

}
