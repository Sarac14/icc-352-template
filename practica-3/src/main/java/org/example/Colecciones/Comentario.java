package org.example.Colecciones;

public class Comentario {
    long id;
    String comentario;
    Usuario autor;
    Articulos articulo;

    public Comentario() {
    }

    public Comentario(long id, String comentario, Usuario autor, Articulos articulo) {
        this.id = id;
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
    }

    public String getComentario() {
        return comentario;
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}

