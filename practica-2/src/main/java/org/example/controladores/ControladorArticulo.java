package org.example.controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Articulos;
import org.example.Colecciones.Comentario;
import org.example.Colecciones.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioComentario;
import org.example.servicios.ServicioUsuario;
import org.example.util.BaseControlador;

import java.time.LocalDate;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorArticulo extends BaseControlador {

    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();

    public ControladorArticulo(Javalin app){super (app);}
    private static long ultimoId = 0;

    public void aplicarRutas() {

        app.before("/crearArticulo", ctx -> {
            Usuario usuario = servicio_usuario.getUsuarioLogeado();
            if(usuario.getUsuario() == null){
                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/");
            }
        });

        app.get("/crearArticulo", ctx ->{
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "¡Publica un articulo!");
            modelo.put("accion", "/publicar");

            ctx.render("publico/NuevoArticulo.html", modelo);
        });
        /*app.get("/publicar", ctx ->{

            ctx.redirect("/RegistrarUsuario.html");
        });*/
        app.before("/editar/{id}", ctx -> {
            Articulos articulo = servicio_art.getArticuloPorID(ctx.pathParamAsClass("id", long.class).get());
            String autor = articulo.getAutor().getUsername();
            String username = servicio_usuario.getUsuarioLogeado().getUsername();
            if (!servicio_usuario.getUsuarioPorUsuario(username).isAdmin()) {
                if (!autor.equals(username)) {
                    System.out.println("Solo un admin o el autor del articulo lo puede editar");
                    ctx.redirect("/");
                }
            }
        });

        app.get("/editar/{id}", ctx -> {
            Articulos articulo = servicio_art.getArticuloPorID(ctx.pathParamAsClass("id", long.class).get());

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Editar "+articulo.getId());
            modelo.put("articulo", articulo);
            modelo.put("accion", "/editar");
            ctx.render("publico/NuevoArticulo.html", modelo);
        });

        app.post("/editar", ctx -> {
            //long id = ctx.pathParamAsClass("id", Long.class).get();
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");

            // Obtener el artículo existente por su ID
            Articulos articuloExistente = ctx.sessionAttribute("artActual");


            // Actualizar las propiedades del artículo
            articuloExistente.setTitulo(titulo);
            articuloExistente.setCuerpo(cuerpo);
            // Actualizar las etiquetas

            String[] etiquetasArray = etiquetas.split(", ");
            List<String> listaEtiquetas = new ArrayList<>();
            for (String etiquetaStr : etiquetasArray) {
                String etiqueta = etiquetaStr;
                listaEtiquetas.add(etiqueta);
            }
            articuloExistente.setListaEtiqueta(listaEtiquetas);


            // Llamar al método de servicio para editar el artículo
            servicio_art.actualizarArticulo(articuloExistente);


            // Redirigir a la página de visualización del artículo
            ctx.redirect("/verArticulo/" + articuloExistente.getId());

        });

        app.post("/publicar", ctx -> {
            long nuevoId = ultimoId + 10;
            ultimoId = nuevoId;
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");
            Usuario autor = servicio_usuario.getUsuarioLogeado();
            LocalDate fecha =  LocalDate.now();

            String[] etiquetasArray = etiquetas.split(", ");
            List<String> listaEtiquetas = new ArrayList<>();
            for (String etiquetaStr : etiquetasArray) {
                String etiqueta = etiquetaStr;
                listaEtiquetas.add(etiqueta);
            }

            if (servicio_art.getArticuloPorID(nuevoId) == null) {
                if(servicio_art.autenticarArticulo(nuevoId, titulo,cuerpo)) {
                    Articulos art = new Articulos(nuevoId, titulo, cuerpo, autor, fecha);
                    servicio_art.crearArticulo(art);
                    art.setListaEtiqueta(listaEtiquetas);

                }else{
                    System.out.println("Campos necesarios");
                    ctx.redirect("/publicar");
                }

                ctx.redirect("/");

            } else {
                ctx.html("Este articulo ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
            }
        });

        app.get("/verArticulo/{id}", ctx ->{
            Articulos articulo = servicio_art.getArticuloPorID(ctx.pathParamAsClass("id", long.class).get());
            ctx.sessionAttribute("artActual", articulo);

            List<Comentario> listaComen = servicioComentario.getComentariosPorArticulo(articulo);

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Vista Formulario"+articulo.getId());
            modelo.put("articulo", articulo);
            modelo.put("listaComen", listaComen);
            modelo.put("accion", "/verArticulo");

            ctx.render("publico/VistaArticulo.html", modelo);
        });


    }
}
