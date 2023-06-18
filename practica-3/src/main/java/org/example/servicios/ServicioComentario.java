package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.entidades.Comentario;
import org.example.entidades.Articulo;

import java.util.ArrayList;
import java.util.List;

public class ServicioComentario extends GestionDb<Comentario>{

    private static ServicioComentario instancia;

    public ServicioComentario() {
        super(Comentario.class);
    }

    public static ServicioComentario getInstancia(){
        if(instancia==null){
            instancia = new ServicioComentario();
        }
        return instancia;
    }

    public List<Comentario> consultaNativa(Articulo art) {
        EntityManager em = getEntityManager();
        //Query query = em.createNativeQuery("SELECT c.* FROM Comentario c WHERE c.articulo_id = :articuloId", Comentario.class);
        Query query = em.createNativeQuery("SELECT c.* FROM Comentario c INNER JOIN Articulo a ON c.articulo_id = a.id WHERE a.id = :articuloId", Comentario.class);

        query.setParameter("articuloId", art.getId());
        List<Comentario> lista = query.getResultList();
        return lista;
    }

}
