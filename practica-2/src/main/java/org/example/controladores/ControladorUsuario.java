package org.example.controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Usuario;
import org.example.servicios.ServicioUsuario;
import org.example.util.BaseControlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorUsuario  extends BaseControlador {

    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
        public ControladorUsuario(Javalin app){super (app);}

        public void aplicarRutas() {
            app.routes(()->{
                path("/usuario/", () -> {

                    app.before("/usuario", ctx -> {
                        if(servicio_usuario.getUsuarioLogeado() != null) {
                            if (servicio_usuario.getUsuarioLogeado().isAdmin() == false) {
                                System.out.println("Usted no tiene los permisos necesarios");
                                ctx.redirect("/");

                            }
                        }else{
                            System.out.println("Necesita iniciar sesion");
                            ctx.redirect("/");
                        }
                    });


                    app.get("/usuario", ctx ->{
                        List<Usuario> lista = servicio_usuario.getListaUsuarios();
                        //Foto foto = ctx.sessionAttribute("foto");
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Vista Usuarios");
                        modelo.put("lista", lista);
                        //modelo.put("foto", foto);
                        ctx.render("publico/VistaUsuario.html", modelo);

                    });
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


                        if (servicio_usuario.getUsuarioPorUsuario(username) == null) {
                            ctx.redirect("/");
                            servicio_usuario.crearUsuario(new Usuario(username, nombre, password, false, true));

                            if (servicio_usuario.getUsuarioLogeado() == null) {
                                ctx.redirect("/login");
                            } else {
                                ctx.redirect("/usuario");
                            }
                        } else {
                            ctx.html("Este usuario ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
                        }
                    });

                    app.get("/editarUsuario/{usuario}", ctx -> {

                        Usuario usuario = servicio_usuario.getUsuarioPorUsuario(ctx.pathParam("usuario"));
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
                        servicio_usuario.actualizarUsuario(usuario);

                        // Redirigir a la página de visualización de los usuarios
                        ctx.redirect("/usuario");

                    });

                    app.get("/eliminarUsuario/{usuario}", ctx -> {
                        servicio_usuario.eliminar(ctx.pathParam("usuario"));
                        ctx.redirect("/usuario");
                    });


                });
            });

        }
}


