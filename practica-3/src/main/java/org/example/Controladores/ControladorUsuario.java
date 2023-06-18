package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Colecciones.UsuarioColeccion;
import org.example.entidades.Articulo;
import org.example.entidades.Etiqueta;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioUsuario;
import org.example.Util.BaseControlador;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorUsuario  extends BaseControlador {

    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
        public ControladorUsuario(Javalin app){super (app);}

        public void aplicarRutas() {
            app.routes(()->{
                path("/usuario/", () -> {

                    app.before("/usuario", ctx -> {
                        Usuario usuario = servicio_usuario.findByUsername(ctx.sessionAttribute("username"));
                        if(usuario.isAdministrador() == false){
                            System.out.println("Usted no tiene los permisos necesarios");
                            ctx.redirect("/");
                        }
                    });

                    //READ
                    app.get("/usuario", ctx ->{
                        List<Usuario> lista = servicio_usuario.consultaNativa();

                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Vista Usuarios");
                        modelo.put("lista", lista);
                        ctx.render("publico/VistaUsuario.html", modelo);

                    });

                    //CREATE
                    app.get("/nuevoUsuario", ctx ->{

                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Registro Usuario");
                        modelo.put("accion", "/nuevoUsuario");
                        ctx.render("publico/RegistrarUsuario.html",modelo);
                    });

                    app.post("/nuevoUsuario", ctx -> {
                        String username = ctx.formParam("username");
                        String password = ctx.formParam("password");
                        String Fname = ctx.formParam("Fname");
                        String Lname = ctx.formParam("Lname");
                        String nombre = Fname+ ' ' + Lname;


                        if (servicio_usuario.findByUsername(username) == null) {

                            servicio_usuario.crear(new Usuario(username, nombre, password, false, true));
                            ctx.redirect("/usuario");
                            //ctx.html("Usuario creado. <a href='/'>Ir a inicio</a>");
                        } else {
                            ctx.html("Este usuario ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
                        }
                    });

                    //UPDATE
                    app.get("/editarUsuario/{id}", ctx -> {

                        Usuario usuario = servicio_usuario.find(ctx.pathParamAsClass("id", Long.class).get());
                        ctx.sessionAttribute("usuarioEditar", usuario);

                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Editar "+usuario.getUsername());
                        modelo.put("usuario", usuario);
                        modelo.put("accion", "/editarUsuario");
                        ctx.render("publico/RegistrarUsuario.html", modelo);
                    });

                    app.post("/editarUsuario", ctx -> {

                        Usuario usuario = ctx.sessionAttribute("usuarioEditar");

                        String username = ctx.formParam("username");
                        String password = ctx.formParam("password");
                        String Fname = ctx.formParam("Fname");
                        String Lname = ctx.formParam("Lname");
                        String nombre = Fname+ ' ' + Lname;


                        // Actualizar las propiedades del usuario
                        usuario.setUsername(username);
                        usuario.setPassword(password);
                        usuario.setNombre(nombre);

                        // Llamar al método de servicio para editar el artículo
                        servicio_usuario.editar(usuario);

                        // Redirigir a la página de visualización de los usuarios
                        ctx.redirect("/usuario");

                    });

                    //-----------------ELIMINAR NO FUNCIONA----------------
                    app.get("/eliminarUsuario/{id}", ctx -> {
                        servicio_usuario.eliminar(ctx.pathParamAsClass("id", long.class).get());
                        ctx.redirect("/usuario");
                    });


                });
            });

        }


}


