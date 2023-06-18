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

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class FotoControlador extends BaseControlador {

    private FotoServices fotoServices = FotoServices.getInstancia();
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();



    public FotoControlador(Javalin app){super (app);}
    public void aplicarRutas() {
        app.routes(() -> {
            path("/fotos", () -> {

                get("/", ctx -> {
                    ctx.redirect("/fotos/listar");
                });

                get("/listar", ctx -> {
                    List<Foto> fotos = fotoServices.findAll();

                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Ejemplo de funcionalidad Thymeleaf");
                    modelo.put("fotos", fotos);

                    //
                    ctx.render("/templates/listar.html", modelo);
                });

                //CREAR
                //ESTA EN CONTROLADORUSUARIO

               /* post("/procesarFoto", ctx -> {
                    //String user = ctx.sessionAttribute("username");
                    //Usuario usuario = servicio_usuario.findByUsername(user);

                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.content().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString);
                            fotoServices.crear(foto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /*if(usuario == null){
                                ctx.redirect("/login");
                        }else{
                                ctx.redirect("/usuario");
                            }
                        ctx.redirect("/fotos/listar");
                    });
                });*/

                get("/visualizar/{id}", ctx -> {
                    try {
                        Foto foto = fotoServices.find(ctx.pathParamAsClass("id", Long.class).get());
                        if(foto==null){
                            ctx.redirect("/usuario");
                            return;
                        }
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("foto", foto);
                        //
                        ctx.render("publico/visualizar.html", modelo);
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                        ctx.redirect("/usuario");
                    }
                });

                get("/eliminar/{id}", ctx -> {
                    try {
                        Foto foto = fotoServices.find(ctx.pathParamAsClass("id", Long.class).get());
                        if(foto!=null){
                            fotoServices.eliminar(foto.getId());
                        }
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                    ctx.redirect("/fotos/listar");
                });

            });
        });
    }
}
