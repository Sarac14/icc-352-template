package org.example.controladores;

import io.javalin.Javalin;
import org.example.entidades.Formulario;
import org.example.servicios.ServicioAgente;
import org.example.servicios.ServicioForm;
import org.example.servicios.ServicioJWT;
import org.example.util.BaseControlador;
import org.example.entidades.Agente;


import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;


public class RestControlador extends BaseControlador {

    ServicioAgente servicioAgente = ServicioAgente.getInstancia();
    ServicioForm servicioForm = ServicioForm.getInstancia();

    ServicioJWT servicioJWT = ServicioJWT.getInstancia();
    public RestControlador (Javalin app){super(app);}
    @Override
    public void aplicarRutas() {

        app.routes(() -> {
            path("/api", () -> {

                path("/formulario", () ->{
                   get("/{username}", ctx -> {
                       String username = ctx.pathParam("username");
                       Agente agente = servicioAgente.getAgentePorUsuario(username);

                       if (agente == null) {
                           ctx.status(404).result("Este agente no se encuentra en la base de datos");
                           return;
                       }

                       List<Formulario> formularios = servicioForm.listarFormularioPorUsuario(username);
                       ctx.json(formularios);
                   });


                   //------------------------------------------------------------------------------------------
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });

                    get("/", ctx -> {
                        ctx.json(servicioForm.listarFormulario());
                    });


                    post("/", ctx -> {
                        //parseando la informacion del POJO debe venir en formato json.
                        Formulario tmp = ctx.bodyAsClass(Formulario.class);
                       /* String imagenBase64 = tmp.getImagenBase64();
                        byte[] imagenBytes = Base64.getDecoder().decode(imagenBase64);
                        String encodedString = Base64.getEncoder().encodeToString(imagenBytes);

                        tmp.setImagenBase64(encodedString);*/
                        //creando.
                        ctx.json(servicioForm.crearForm(tmp));
                    });
                });
            });
        });
    }
}
