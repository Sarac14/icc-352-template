package org.example.servicios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.example.Colecciones.UsuarioColeccion;
import org.example.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuario extends GestionDb<Usuario>{
    private static ServicioUsuario instancia;
    private static List<UsuarioColeccion> listaUsuarios = new ArrayList<>();//NO USA ORM
    private UsuarioColeccion usuarioLogeado = null;//NO USA ORM

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
        Query query = em.createNativeQuery("select * from Usuario ", UsuarioColeccion.class);
        //query.setParameter("nombre", apellido+"%");
        List<Usuario> lista = query.getResultList();
        return lista;
    }

    public void  CreacionDeUsuarios(){
        UsuarioColeccion admin = new UsuarioColeccion("admin", "admin", "admin", true, true);
        UsuarioColeccion usu = new UsuarioColeccion("sara", "sara", "sara", false, true);
        instancia.crearUsuario(admin);
        instancia.crearUsuario(usu);

    }

    public Usuario autenticarUsuario(String username, String password) {
        Usuario usuario = findByUsername(username);
        if(usuario != null) {
            if (usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return  null;
    }

    //------------------FUNCIONES QUE NO USAN ORM--------------------------------
    public List<UsuarioColeccion> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioColeccion> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }


    public UsuarioColeccion getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(UsuarioColeccion usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    /*public UsuarioColeccion autenticarUsuario(String usuario, String password) {
        UsuarioColeccion tmp = getUsuarioPorUsuario(usuario);
        if(tmp != null) {
            if(tmp.getPassword().equals(password)){
                setUsuarioLogeado(tmp);
                return tmp;
            }
        }

        return null;
    }*/

    // CREAR
    public UsuarioColeccion crearUsuario(UsuarioColeccion usuario) {
        UsuarioColeccion tmp = getUsuarioPorUsuario(usuario.getUsuario());
        if(tmp!=null) {
            System.out.println("Este usuario ya existe");
            return null;
        }
        System.out.println("Usuario registrado correctamente");
        listaUsuarios.add(usuario);
        return usuario;
    }


    // ENLISTAR
    public UsuarioColeccion getUsuarioPorUsuario(String usuario){
        return listaUsuarios.stream().filter(u -> u.getUsuario().equalsIgnoreCase(usuario)).findFirst().orElse(null);
    }
    public UsuarioColeccion getUsuarioPorNombre(String nombre){
        return listaUsuarios.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public static UsuarioColeccion buscarUsuarioPorUsername(String username) {
        for (UsuarioColeccion usuario : listaUsuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario; // Se encontró el usuario
            }
        }
        return null; // No se encontró el usuario
    }

    public static UsuarioColeccion usuarioLogeado (String username) {
        UsuarioColeccion tmp = buscarUsuarioPorUsername(username);
        return tmp;
    }
}
