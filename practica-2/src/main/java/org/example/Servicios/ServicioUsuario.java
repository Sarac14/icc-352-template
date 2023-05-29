package org.example.Servicios;

import io.javalin.Javalin;
import org.example.Colecciones.Usuario;
import org.example.Util.BaseControlador;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuario {
    private static ServicioUsuario instancia;
    private static List<Usuario> listaUsuarios = new ArrayList<>();
    private Usuario usuarioLogeado;


    private ServicioUsuario(){
        listaUsuarios.add(new Usuario("admin", "admin", "admin", true, true));
        listaUsuarios.add(new Usuario("sara", "sara", "sara", false, true));
    }

    public static ServicioUsuario getInstancia(){
        if(instancia==null){
            instancia = new ServicioUsuario();
        }
        return instancia;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public Usuario autenticarUsuario(String usuario, String password) {
        Usuario tmp = getUsuarioPorUsuario(usuario);
        if(tmp != null) {
            if(tmp.getPassword().equals(password)){
                setUsuarioLogeado(tmp);
                return tmp;
            }
        }

        return null;
    }

    // CREAR
    public Usuario crearUsuario(Usuario usuario) {
        Usuario tmp = getUsuarioPorUsuario(usuario.getUsuario());
        if(tmp!=null) {
            System.out.println("Este usuario ya existe");
            return null;
        }
        System.out.println("Usuario registrado correctamente");
        listaUsuarios.add(usuario);
        return usuario;
    }


    // ENLISTAR
    public Usuario getUsuarioPorUsuario(String usuario){
        return listaUsuarios.stream().filter(u -> u.getUsuario().equalsIgnoreCase(usuario)).findFirst().orElse(null);
    }
    public Usuario getUsuarioPorNombre(String nombre){
        return listaUsuarios.stream().filter(u -> u.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    public static Usuario buscarUsuarioPorUsername(String username) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario; // Se encontró el usuario
            }
        }
        return null; // No se encontró el usuario
    }

    public static Usuario usuarioLogeado (String username) {
        Usuario tmp = buscarUsuarioPorUsername(username);
        return tmp;
    }
}
