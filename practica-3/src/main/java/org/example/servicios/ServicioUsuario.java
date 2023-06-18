package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.example.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuario extends GestionDb<Usuario>{
    private static ServicioUsuario instancia;

    public ServicioUsuario() {
        super(Usuario.class);
    }

    public static ServicioUsuario getInstancia(){
        if(instancia==null){
            instancia = new ServicioUsuario();
        }
        return instancia;
    }

    public List<Usuario> findAllByNombre(String username){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Usuario e where e.username like :username");
        query.setParameter("username", username+"%");
        List<Usuario> lista = query.getResultList();
        return lista;
    }

    public List<Usuario> consultaNativa(){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Usuario ", Usuario.class);
        //query.setParameter("nombre", apellido+"%");
        List<Usuario> lista = query.getResultList();
        return lista;
    }

    public Usuario autenticarUsuario(String username, String password) {
        EntityManager em = getEntityManager();
            Query query = em.createNativeQuery("SELECT u.* FROM Usuario u WHERE u.username = :username AND u.password = :password", Usuario.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<Usuario> usuarios = query.getResultList();

        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }

}
