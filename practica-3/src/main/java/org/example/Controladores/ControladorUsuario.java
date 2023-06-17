package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Usuario;
import org.example.Servicios.ServicioUsuario;
import org.example.Util.BaseControlador;

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
                        if(servicio_usuario.getUsuarioLogeado().isAdmin() == false){
                            System.out.println("Usted no tiene los permisos necesarios");
                            ctx.redirect("/");

                        }
                    });

                    app.get("/usuario", ctx ->{
                        List<Usuario> lista = servicio_usuario.getListaUsuarios();
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Vista Usuarios");
                        modelo.put("lista", lista);
                        ctx.render("publico/VistaUsuario.html", modelo);

                    });
                    app.get("/nuevoUsuario", ctx ->{

                        ctx.redirect("/RegistrarUsuario.html");
                    });

                    app.post("/nuevoUsuario", ctx -> {
                        String username = ctx.formParam("username");
                        String password = ctx.formParam("password");
                        String Fname = ctx.formParam("Fname");
                        String Lname = ctx.formParam("Lname");
                        String nombre = Fname+ ' ' + Lname;


                        if (servicio_usuario.buscarUsuarioPorUsername(username) == null) {
                            ctx.redirect("/");
                            servicio_usuario.crearUsuario(new Usuario(username, nombre, password, false, true));
                            ctx.html("Usuario creado. <a href='/'>Ir a inicio</a>");
                        } else {
                            ctx.html("Este usuario ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
                        }
                    });


                });
            });

        }


}


