package org.example.entidades;

import org.example.servicios.ServicioAgente;

public class Formulario {
    private String id;

    private String nombre;
    private String sector;
    private String nivelEscolar;
    private String userAgente;
    private Agente agente;
    private String longitud;
    private String latitud;

    public Formulario() {
    }

    public Formulario(String id, String nombre, String sector, String nivelEscolar, String userAgente, String longitud, String latitud) {
        this.id = id;
        this.nombre = nombre;
        this.sector = sector;
        this.nivelEscolar = nivelEscolar;
        this.userAgente = userAgente;
        this.longitud = longitud;
        this.latitud = latitud;
        this.agente = ServicioAgente.getInstancia().getAgentePorUsuario(this.userAgente);
    }

    public Formulario(String nombre, String sector, String nivelEscolar, String userAgente, String longitud, String latitud) {
        this.nombre = nombre;
        this.sector = sector;
        this.nivelEscolar = nivelEscolar;
        this.userAgente = userAgente;
        this.longitud = longitud;
        this.latitud = latitud;
        this.agente = ServicioAgente.getInstancia().getAgentePorUsuario(this.userAgente);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public String getUserAgente() {
        return userAgente;
    }

    public void setUserAgente(String userAgente) {
        this.userAgente = userAgente;
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

    public Agente getAgente() {
        return agente;
    }


    @Override
    public String toString() {
        return "Formulario{" +
                "sector=" + sector +
                ", nombre='" + nombre + '\'' +
                ", nivelEscolar='" + nivelEscolar + '\'' +
                ", agente='" + userAgente + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
