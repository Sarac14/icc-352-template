package org.example.controladores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.example.entidades.Agente;
import org.example.entidades.Formulario;
import org.example.entidades.Foto;
import org.example.servicios.ServicioForm;
import org.example.util.BaseControlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class FormularioControlador extends BaseControlador {
    ServicioForm formService = ServicioForm.getInstancia();
    public FormularioControlador(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        //Agregando la librería del render de Thymeleaf
        JavalinRenderer.register(new JavalinThymeleaf(), ".html");

        app.routes(() -> {
            path("/crud-form/", () -> {


                /*get("/", ctx -> {
                    ctx.redirect("/crud-form/listar");
                });*/
                before("/listarForm", ctx -> {
                    Agente agente = ctx.sessionAttribute("agente");
                    if(agente == null) {
                        ctx.contentType("text/html");
                        ctx.html("<script>alert('Primero inice sesion'); </script>");
                        ctx.redirect("/");
                    }
                });


                get("/listarForm", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Formulario> lista = formService.listarFormulario();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Formularios");
                    modelo.put("lista", lista);
                    //modelo.put("usuarioAgente", usuarioAgente);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/listarForm.html", modelo);
                });

                app.get("/obtener-usuario", ctx -> {
                    Agente agente = ctx.sessionAttribute("agente");
                    String usuario = agente.getUsuario();
                    ctx.result(usuario);
                });

                app.get("/obtener-formulario/{id}", ctx -> {
                    String formularioId = ctx.queryParam("id");

                    if (formularioId != null && !formularioId.isEmpty()) {
                        Formulario formulario = ServicioForm.getInstancia().getFormPorId(formularioId);

                        if (formulario != null) {
                            ctx.json(formulario);
                        } else {
                            ctx.status(404);
                            ctx.result("Formulario no encontrado");
                        }
                    } else {
                        ctx.status(400);
                        ctx.result("ID del formulario no proporcionado");
                    }
                });

                get("/visualizar/{id}", ctx -> {
                    try {
                        String formularioId = ctx.queryParam("id");
                        Formulario formulario = ServicioForm.getInstancia().getFormPorId(formularioId);
                        if(formulario==null){
                            ctx.redirect("/listarForm");
                            return;
                        }
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("formulario", formulario);
                        //
                        ctx.render("publico/visualizar.html", modelo);
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                        ctx.redirect("/listarForm");
                    }
                });

                get("/visualizarFoto/{id}", ctx -> {
                    try {
                        String foto = ctx.pathParam("id");
                        if(foto==null){
                            ctx.redirect("/crud-form/listarForm");
                            return;
                        }
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("foto", foto);
                        //
                        ctx.render("publico/visualizar.html", modelo);
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                        ctx.redirect("/crud-form/listarForm");
                    }
                });

            });
        });

        //--------------------------WEB SOCKETS--------------------------------
        app.ws("/sync", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexión establecida");
            });


            ws.onMessage(ctx -> {
                String message = ctx.message();
                ObjectMapper mapper = new ObjectMapper();
                List<Formulario> registros = mapper.readValue(message, new TypeReference<List<Formulario>>(){});

                for (Formulario formulario : registros) {
                    formService.crearForm(formulario);
                }
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión cerrada");
            });

            ws.onError(ctx -> {
                System.out.println("Ocurrió un error");
            });
        });

    }


}

