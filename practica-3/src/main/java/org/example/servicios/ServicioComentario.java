package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.entidades.Comentario;
import org.example.entidades.Articulo;

import java.util.ArrayList;
import java.util.List;

public class ServicioComentario extends GestionDb<Comentario>{

    private static ServicioComentario instancia;
    private static ServicioArticulo servicio_articulo = ServicioArticulo.getInstancia();// NO USA ORM
    private static List<Comentario> listaComentarios = new ArrayList<>();//NO USA ORM

    public ServicioComentario() {
        super(Comentario.class);
    }

    public static ServicioComentario getInstancia(){
        if(instancia==null){
            instancia = new ServicioComentario();
        }
        return instancia;
    }


    /*public List<Comentario> consultaNativa(Articulo art){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Comentario where Comentario.articulo.id = art.id", Comentario.class);
        query.setParameter("art", art+"%");
        List<Comentario> lista = query.getResultList();
        return lista;
    }*/

    /*public List<Comentario> consultaNativa(Articulo art) {
        EntityManager em = getEntityManager();
       Query query = em.createNativeQuery("select c from Comentario c where c.articulo = :articulo", Comentario.class);
        //Query query = em.createQuery("SELECT c FROM Comentario c WHERE c.articulo = :articulo", Comentario.class);
        query.setParameter("articulo", art);
        List<Comentario> lista = query.getResultList();
        return lista;
    }*/
    public List<Comentario> consultaNativa(Articulo art) {
        EntityManager em = getEntityManager();
        //Query query = em.createNativeQuery("SELECT c.* FROM Comentario c WHERE c.articulo_id = :articuloId", Comentario.class);
        Query query = em.createNativeQuery("SELECT c.* FROM Comentario c INNER JOIN Articulo a ON c.articulo_id = a.id WHERE a.id = :articuloId", Comentario.class);

        query.setParameter("articuloId", art.getId());
        List<Comentario> lista = query.getResultList();
        return lista;
    }




    //------------------NO USAN ORM-------------------------------------
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
    public static Comentario getComentarioPorID(long id) {
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
