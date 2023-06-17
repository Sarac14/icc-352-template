package org.example.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Articulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private long id;
    private String titulo;
    private String cuerpo;
    @OneToOne(mappedBy = "articulo")
    private Usuario autor;
    Date fecha;
    @OneToMany
    //private List<Comentario> ListaComentario;
    private Set<Comentario> ListaComentario;
    @OneToMany(mappedBy = "artculo")
    //private List<Etiqueta> ListaEtiqueta;
    private Set<Etiqueta> ListaEtiqueta;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }


    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Set<Comentario> getListaComentario() {
        return ListaComentario;
    }

    public void setListaComentario(Set<Comentario> listaComentario) {
        ListaComentario = listaComentario;
    }

    public Set<Etiqueta> getListaEtiqueta() {
        return ListaEtiqueta;
    }

    public void setListaEtiqueta(Set<Etiqueta> listaEtiqueta) {
        ListaEtiqueta = listaEtiqueta;
    }

}