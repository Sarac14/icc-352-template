package org.example.Colecciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Articulos {
    long id;
    String titulo;
    String cuerpo;
    UsuarioColeccion autor;
    LocalDate fecha;
    List<Comentario> listaComentarios;
    List<String> listaEtiquetas;

    public Articulos(){
    }

    public Articulos(long id, String titulo, String cuerpo, UsuarioColeccion autor, LocalDate fecha, List<Comentario> listaComentarios, List<String> listaEtiquetas ){
        this.id = id;
        this.autor = autor;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.titulo = titulo;
        this.listaComentarios = listaComentarios;
        this.listaEtiquetas = listaEtiquetas;
    }

    public Articulos(long id, String titulo, String cuerpo, UsuarioColeccion autor, LocalDate fecha, List<String> listaEtiquetas){
        this.id = id;
        this.autor = autor;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.titulo = titulo;
        this.listaEtiquetas = listaEtiquetas;
        this.listaComentarios = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public List<String> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<String> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public UsuarioColeccion getAutor() {
        return autor;
    }

    public void setAutor(UsuarioColeccion autor) {
        this.autor = autor;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void actualizar(Articulos tmp) {
        cuerpo = tmp.getCuerpo();
        fecha = tmp.getFecha();
        titulo = tmp.getTitulo();
    }
}
