package org.example.servicios;

import org.example.Colecciones.Comentario;

import java.util.ArrayList;
import java.util.List;

public class ServicioComentario {

    private static ServicioComentario instancia;
    private static ServicioArticulo servicio_articulo = ServicioArticulo.getInstancia();
    private List<Comentario> listaComentarios = new ArrayList<>();

    private ServicioComentario(){}

    public static ServicioComentario getInstancia(){
        if(instancia==null){
            instancia = new ServicioComentario();
        }
        return instancia;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    //CREAR
    public Comentario crearComentario(Comentario comen) {
        Comentario tmp = getComentarioPorID(comen.getId());
        if(tmp!=null) {
            System.out.println("ERROR");
            return null;
        }
        System.out.println("Su comentario ha sido publicado");
        listaComentarios.add(comen);
        /*for(int i=0; i<comen.getListaArticulos().size(); i++){
            serviciosProducto.cambiarCantidad(comen.getListaProductos().get(i).getId(), ventaProductos.getListaProductos().get(i).getCantidad()*-1);
        }*/
        //AGREGAR COMENTARIO
        return comen;
    }

    //listar
    public Comentario getComentarioPorID(long id) {
        return listaComentarios.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    //borrar
    public boolean borrarComentario(long id) {
        Comentario tmp = getComentarioPorID(id);
        if(tmp==null) {
            System.out.println("ERROR");
            return false;
        }
        System.out.println("Comentario eliminado");
        listaComentarios.remove(tmp);
        return true;
    }

    //TENER EN CUENTA QUE EXISTEN COMENTARIOS PARA CADA ARTICULO





}
