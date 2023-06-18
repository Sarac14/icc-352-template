package org.example.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private long id;
    private String username;

    private String Nombre;
    private String password;
    private boolean administrador;
    private boolean autor;

    public Usuario(String username, String nombre, String password, boolean administrador, boolean autor) {
        this.username = username;
        this.Nombre = nombre;
        this.password = password;
        this.administrador = administrador;
        this.autor = autor;
    }
    public Usuario() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
