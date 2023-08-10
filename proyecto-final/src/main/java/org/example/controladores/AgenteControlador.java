package org.example.controladores;


import org.example.entidades.Agente;
import org.example.servicios.ServicioAgente;
import org.example.util.BaseControlador;
import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Representa las rutas para manejar las operaciones de petición - respuesta.
 */
public class AgenteControlador extends BaseControlador {

    ServicioAgente agenteService = ServicioAgente.getInstancia();

    public AgenteControlador(Javalin app) {
        super(app);
    }

    /**
     * Las clases que implementan el sistema de plantilla están agregadas en PlantillasControlador.
     * http://localhost:7000/crud-simple/listar
     */
    @Override
    public void aplicarRutas() {
        //Agregando la librería del render de Thymeleaf
        JavalinRenderer.register(new JavalinThymeleaf(), ".html");

        //
        app.routes(()->{

            /**
             * Ejemplo de como agrupar los endpoint utilizados.
             */
            path("/path/", () -> {
                before(ctx -> {
                    System.out.println("Entrando a la ruta path...");
                });
                get("/", ctx -> {
                    ctx.result("Ruta path /");
                });

                get("/compras", ctx -> {
                    ctx.result("Ruta /path/compras");
                });

                get("/otro", ctx -> {
                    ctx.result("Ruta /path/otro");
                });
            });
        });
        app.routes(() -> {
            path("/crud-simple/", () -> {


               /* get("/", ctx -> {
                    ctx.redirect("/crud-simple/listar");
                });*/
                before("/listar", ctx -> {
                    Agente agente = ctx.sessionAttribute("agente");
                    if(agente != null) {
                        if (agente.getRol().equalsIgnoreCase("Agente") ) {
                            ctx.contentType("text/html");
                            ctx.html("<script>alert('Usted no tiene los permisos necesarios'); </script>");
                            ctx.redirect("/");

                        }
                    }else{
                        ctx.contentType("text/html");
                        ctx.html("<script>alert('Usted no tiene los permisos necesarios'); </script>");
                        ctx.redirect("/");
                    }
                });

                get("/listar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Agente> lista = agenteService.listarAgente();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Estudiante");
                    modelo.put("lista", lista);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/listar.html", modelo);
                });

                get("/crear", ctx -> {
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Creación Estudiante");
                    modelo.put("accion", "/crud-simple/crear");
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                /**
                 * manejador para la creación del estudiante, una vez creado
                 * pasa nuevamente al listado.
                 */
                post("/crear", ctx -> {
                    //obteniendo la información enviada.
                    String usuario = ctx.formParam("usuario");
                    String nombre = ctx.formParam("nombre");
                    String password = ctx.formParam("password");
                    String rol = ctx.formParam("rol");
                    Agente agente = ctx.sessionAttribute("agente");
                    //
                    Agente tmp = new Agente(usuario, password, nombre,rol);
                    //realizar algún tipo de validación...
                    agenteService.crearAgente(tmp);
                    if (agente == null) {
                        ctx.redirect("/login");
                    } else {
                        ctx.redirect("/crud-simple/listar");
                    }
                    //ctx.redirect("/crud-simple/");
                });

                get("/visualizar/{usuario}", ctx -> {
                    Agente agente = agenteService.getAgentePorUsuario(ctx.pathParam("usuario"));
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Visaulizar Estudiante "+agente.getUsuario());
                    modelo.put("agente", agente);
                    modelo.put("visualizar", true); //para controlar en el formulario si es visualizar
                    modelo.put("accion", "/crud-simple/");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                get("/editar/{usuario}", ctx -> {
                    Agente agente = agenteService.getAgentePorUsuario(ctx.pathParam("usuario"));
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Editar Estudiante "+agente.getUsuario());
                    modelo.put("agente", agente);
                    modelo.put("accion", "/crud-simple/editar");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                /**
                 * Proceso para editar un estudiante.
                 */
                post("/editar", ctx -> {
                    //obteniendo la información enviada.
                    String usuario = ctx.formParam("usuario");
                    String nombre = ctx.formParam("nombre");
                    String password = ctx.formParam("password");
                    String rol = ctx.formParam("rol");
                    String id = ctx.formParam("_id");
                    //
                    Agente tmp = new Agente(usuario, password,nombre, rol,id);
                    //realizar algún tipo de validación...
                    agenteService.actualizarAgente(tmp); //puedo validar, existe un error enviar a otro vista.
                    ctx.redirect("/crud-simple/listar");
                });

                /**
                 * Puede ser implementando por el metodo post, por simplicidad utilizo el get. ;-D
                 */
                get("/eliminar/{usuario}", ctx -> {
                    agenteService.eliminandoAgente(ctx.pathParam("usuario"));
                    ctx.redirect("/crud-simple/listar");
                });

            });
        });
    }
}
