package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Articulos;
import org.example.Colecciones.Usuario;
import org.example.Servicios.ServicioArticulo;
import org.example.Servicios.ServicioUsuario;
import org.example.Util.BaseControlador;

import java.time.LocalDate;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorArticulo extends BaseControlador {

    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();

    public ControladorArticulo(Javalin app){super (app);}
    private static long ultimoId = 0;

    public void aplicarRutas() {
        app.routes(()->{
            path("/articulo/", () -> {

                app.get("/crearArticulo", ctx ->{

                    ctx.redirect("/NuevoArticulo.html");
                });
                /*app.get("/publicar", ctx ->{

                    ctx.redirect("/RegistrarUsuario.html");
                });*/

                app.post("/publicar", ctx -> {
                    long nuevoId = ultimoId + 10;
                    ultimoId = nuevoId;
                    String titulo = ctx.formParam("titulo");
                    String cuerpo = ctx.formParam("cuerpo");
                    Usuario autor = servicio_usuario.getUsuarioLogeado();
                    LocalDate fecha =  LocalDate.now();;


                    if (servicio_art.getArticuloPorID(nuevoId) == null) {

                        servicio_art.crearArticulo(new Articulos(nuevoId, titulo, cuerpo, autor, fecha));
                        ctx.redirect("/");
                    } else {
                        ctx.html("Este articulo ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
                    }
                });


            });
        });

    }
}
