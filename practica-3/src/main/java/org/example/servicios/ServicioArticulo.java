package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.entidades.Articulo;
import org.example.entidades.Comentario;

import java.util.ArrayList;
import java.util.List;

public class ServicioArticulo extends GestionDb<Articulo> {

    private static  ServicioArticulo instancia;
    private ServicioArticulo() {
        super(Articulo.class);
    }

    public static ServicioArticulo getInstancia(){
        if(instancia==null){
            instancia = new ServicioArticulo();
        }
        return instancia;
    }

    public List<Articulo> findAllByNombre(String titulo){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select a from Articulo a where a.titulo like :titulo");
        query.setParameter("titulo", titulo+"%");
        List<Articulo> lista = query.getResultList();
        return lista;
    }


    public List<Articulo> consultaNativa(){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Articulo ", Articulo.class);
        //query.setParameter("nombre", apellido+"%");
        List<Articulo> lista = query.getResultList();
        return lista;
    }



    public void agregarComentario(Articulo articulo, Comentario comentario) {
        articulo.getListaComentario().add(comentario);
        comentario.setArticulo(articulo);
        editar(articulo);
    }

}



