package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.example.entidades.Etiqueta;

public class ServicioEtiqueta extends GestionDb<Etiqueta> {
    private static  ServicioEtiqueta instancia;
    private ServicioEtiqueta() {
        super(Etiqueta.class);
    }

    public static ServicioEtiqueta getInstancia(){
        if(instancia==null){
            instancia = new ServicioEtiqueta();
        }
        return instancia;
    }

    public Etiqueta findByNombre(String etiqueta) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT e FROM Etiqueta e WHERE e.etiqueta = :etiqueta");
            query.setParameter("etiqueta", etiqueta);
            return (Etiqueta) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
