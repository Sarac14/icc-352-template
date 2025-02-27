package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.entidades.Articulo;
import org.example.entidades.Comentario;

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

    public List<Articulo> consultaNativa(int pageNumber) {
        int pageSize = 5;
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM Articulo  ORDER BY fecha DESC", Articulo.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Articulo> lista = query.getResultList();
        return lista;
    }

    public void agregarComentario(Articulo articulo, Comentario comentario) {
        articulo.getListaComentario().add(comentario);
        comentario.setArticulo(articulo);
        editar(articulo);
    }

    public boolean autenticarArticulo(long id, String titulo, String cuerpo) {
        if (titulo != null && !titulo.isEmpty() && cuerpo != null && !cuerpo.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<Articulo> getArticulosbyEtiqueta(String etiqueta){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT A FROM Articulo A JOIN A.ListaEtiqueta E WHERE E.etiqueta like :etiqueta");
        query.setParameter("etiqueta", etiqueta+"%");
        List<Articulo> Lista = query.getResultList();

        return Lista;
    }


}



