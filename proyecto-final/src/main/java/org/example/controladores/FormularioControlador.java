package org.example.controladores;

import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.example.entidades.Agente;
import org.example.entidades.Formulario;
import org.example.servicios.ServicioAgente;
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

                get("/crearForm", ctx -> {
                    //
                    Agente agente  = ctx.sessionAttribute("agente");
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Nuevo Formulario");
                    modelo.put("accion", "/crud-form/crearForm");
                    modelo.put("nombreAgente", agente.getNombre());
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/formulario.html", modelo);
                });

                /**
                 * manejador para la creación del estudiante, una vez creado
                 * pasa nuevamente al listado.
                 */
                post("/crearForm", ctx -> {
                    //obteniendo la información enviada.
                    String sector = ctx.formParam("sector");
                    String nombre = ctx.formParam("nombre");
                    String nivelEscolar = ctx.formParam("nivelEscolar");

                    Agente agente = ctx.sessionAttribute("agente");


                    String latitud = ctx.formParam("latitud");
                    String longitud = ctx.formParam("longitud");

                    //
                    Formulario tmp = new Formulario(nombre, sector, nivelEscolar, agente.getUsuario(), longitud, latitud);
                    //realizar algún tipo de validación...
                    formService.crearForm(tmp); //puedo validar, existe un error enviar a otro vista.
                    ctx.redirect("/crud-form/listarForm");
                });

                get("/visualizarForm/{id}", ctx -> {
                    Formulario form = formService.getFormPorId(ctx.pathParam("id"));
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Visaulizar Formulario "+form.getId());
                    modelo.put("formulario", form);
                    modelo.put("visualizar", true); //para controlar en el formulario si es visualizar
                    modelo.put("accion", "/crud-form/");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/formulario.html", modelo);
                });

                before("/editarForm/{id}", ctx -> {
                    Formulario formulario = formService.getFormPorId(ctx.pathParam("id"));
                    String agente = formulario.getAgente();
                    Agente user = ctx.sessionAttribute("agente");
                    if (!user.getRol().equalsIgnoreCase("Admin")) {
                        if (!agente.equals(user.getUsuario())) {
                            ctx.contentType("text/html");
                            ctx.html("<script>alert('Solo un Admin o el usuario que realizo la encuesta pueda editarla'); window.location.href='/crud-form/listar';</script>");
                        }
                    }
                });

                get("/editarForm/{id}", ctx -> {
                    Formulario form = formService.getFormPorId(ctx.pathParam("id"));
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Editar Formulario "+form.getId());
                    modelo.put("formulario", form);
                    modelo.put("accion", "/crud-form/editarForm");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/formulario.html", modelo);
                });

                /**
                 * Proceso para editar un estudiante.
                 */
                post("/editarForm", ctx -> {
                    Agente agente = ctx.sessionAttribute("agente");
                    //obteniendo la información enviada.
                    String sector = ctx.formParam("sector");
                    String nombre = ctx.formParam("nombre");
                    String nivelEscolar = ctx.formParam("nivelEscolar");
                    String userAgente = agente.getUsuario();
                    String latitud = ctx.formParam("latitud");
                    String longitud = ctx.formParam("longitud");
                    String id = ctx.formParam("_id");
                    //
                    Formulario tmp = new Formulario(id,nombre, sector, nivelEscolar, userAgente, longitud, latitud);
                    //realizar algún tipo de validación...
                    formService.acrutalizarForm(tmp);
                    ctx.redirect("/crud-form/listarForm");
                });

                /**
                 * Puede ser implementando por el metodo post, por simplicidad utilizo el get. ;-D
                 */
                get("/eliminar/{id}", ctx -> {
                    formService.elimanandoForm(ctx.pathParam("id"));
                    ctx.redirect("/crud-form/listarForm");
                });

            });
        });
    }


}

