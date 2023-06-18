package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Util.BaseControlador;
import org.example.entidades.Foto;
import org.example.servicios.FotoServices;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class FotoControlador extends BaseControlador {

    private FotoServices fotoServices = FotoServices.getInstancia();


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

                post("/procesarFoto", ctx -> {
                    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                        try {
                            byte[] bytes = uploadedFile.content().readAllBytes();
                            String encodedString = Base64.getEncoder().encodeToString(bytes);
                            Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString);
                            fotoServices.crear(foto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ctx.redirect("/fotos/listar");
                    });
                });

                get("/visualizar/{id}", ctx -> {
                    try {
                        Foto foto = fotoServices.find(ctx.pathParamAsClass("id", Long.class).get());
                        if(foto==null){
                            ctx.redirect("/fotos/listar");
                            return;
                        }
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("foto", foto);
                        //
                        ctx.render("/templates/visualizar.html", modelo);
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                        ctx.redirect("/fotos/listar");
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
