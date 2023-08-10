package org.example.entidades;

import org.example.servicios.ServicioAgente;

public class Formulario {
    private String id;
    private String nombre;
    private String nivelEscolar;
    private String sector;
    private String agente;
    private String longitud;
    private String latitud;


    public Formulario() {
    }

    public Formulario(String nombre, String sector, String nivelEscolar, String agente, String longitud, String latitud) {
        this.nombre = nombre;
        this.nivelEscolar = nivelEscolar;
        this.sector = sector;
        this.agente = agente;
        this.longitud = longitud;
        this.latitud = latitud;

    }

    public Formulario(String id, String nombre, String nivelEscolar, String sector, String agente, String longitud, String latitud) {
        this.id = id;
        this.nombre = nombre;
        this.nivelEscolar = nivelEscolar;
        this.sector = sector;
        this.agente = agente;
        this.longitud = longitud;
        this.latitud = latitud;


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

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
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
