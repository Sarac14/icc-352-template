package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Util.BaseControlador;
import org.example.entidades.Foto;
import org.example.entidades.Usuario;
import org.example.servicios.FotoServices;
import org.example.servicios.ServicioUsuario;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorUsuario  extends BaseControlador {

    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private FotoServices fotoServices = FotoServices.getInstancia();

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
                        //Foto foto = ctx.sessionAttribute("foto");
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("titulo", "Vista Usuarios");
                        modelo.put("lista", lista);
                        //modelo.put("foto", foto);
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
                        String user = ctx.sessionAttribute("username");
                        Usuario usuario = servicio_usuario.findByUsername(user);

                        String username = ctx.formParam("username");
                        String password = ctx.formParam("password");
                        String Fname = ctx.formParam("Fname");
                        String Lname = ctx.formParam("Lname");
                        String nombre = Fname+ ' ' + Lname;


                        if (servicio_usuario.findByUsername(username) == null) {
                            Usuario nuevoUsuario = new Usuario(username, nombre, password, false, true);



                            //---------------------------------------LO DE LA FOTO-------------------------------------------------------------

                            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                                try {
                                    byte[] bytes = uploadedFile.content().readAllBytes();
                                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                                    Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString);
                                    fotoServices.crear(foto);
                                    nuevoUsuario.setFoto(foto);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //ctx.sessionAttribute("foto", foto);

                                //ctx.redirect("/fotos/listar");
                            });
                            //-----------------------------------------------------------------------------------------------------------------

                            servicio_usuario.crear(nuevoUsuario);

                            if (usuario == null) {
                                ctx.redirect("/login");
                            } else {
                                ctx.redirect("/usuario");
                            }
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


