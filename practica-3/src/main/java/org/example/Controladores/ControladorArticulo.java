package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Articulos;
import org.example.Colecciones.Comentario;
import org.example.Colecciones.UsuarioColeccion;
import org.example.entidades.Articulo;
import org.example.entidades.Etiqueta;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioComentario;
import org.example.servicios.ServicioEtiqueta;
import org.example.servicios.ServicioUsuario;
import org.example.Util.BaseControlador;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.*;

public class ControladorArticulo extends BaseControlador {

    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioEtiqueta servicioEti = ServicioEtiqueta.getInstancia();
    private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();

    public ControladorArticulo(Javalin app){super (app);}
    private static long ultimoId = 1;
    private static long comentarioId = 0;

    public void aplicarRutas() {

        app.before("/crearArticulo", ctx -> {
            String username = ctx.sessionAttribute("username");
            if(servicio_usuario.findByUsername(username).getUsername() == null){
                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/");
            }
        });

        app.before("/comentario", ctx -> {
            if(servicio_usuario.getUsuarioLogeado().getUsuario() == null){
                Articulos articulo = servicio_art.getArtActual();

                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/verArticulo/"+articulo.getId());

            }
        });

        app.get("/crearArticulo", ctx ->{
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "¡Publica un articulo!");
            modelo.put("accion", "/publicar");

            ctx.render("publico/NuevoArticulo.html", modelo);
        });

        app.get("/editar/{id}", ctx -> {
            Articulo articulo = servicio_art.find(ctx.pathParamAsClass("id", long.class).get());

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Editar "+articulo.getId());
            modelo.put("articulo", articulo);
            modelo.put("accion", "/editar");
            ctx.render("publico/NuevoArticulo.html", modelo);
        });

        /*app.post("/editar", ctx -> {
            //obteniendo la información enviada.
            long nuevoId = ultimoId;
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");
            UsuarioColeccion autor = servicio_usuario.getUsuarioLogeado();
            LocalDate fecha =  LocalDate.now();

            String[] etiquetasArray = etiquetas.split(", ");
            List<String> listaEtiquetas = Arrays.asList(etiquetasArray);

            Articulos tmp = new Articulos(nuevoId, titulo, cuerpo, autor, fecha, listaEtiquetas);

             if(servicio_art.autenticarArticulo(nuevoId,titulo,cuerpo)){
                 servicio_art.crearArticulo(new Articulos(nuevoId, titulo, cuerpo, autor, fecha, listaEtiquetas));
             }else {
                 System.out.println("Campos necesarios");
                 ctx.redirect("/editar");
             }
             servicio_art.actualizarArticulo(tmp);
             ctx.redirect("/verArticulo/"+tmp.getId());
        });*/

        app.post("/editar", ctx -> {
            //long id = ctx.pathParamAsClass("id", Long.class).get();
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");

            // Obtener el artículo existente por su ID
            Articulo articuloExistente = ctx.sessionAttribute("artActual");


            // Actualizar las propiedades del artículo
            articuloExistente.setTitulo(titulo);
            articuloExistente.setCuerpo(cuerpo);
            // Actualizar las etiquetas

            String[] etiquetasArray = etiquetas.split(", ");
            //List<Etiqueta> listaEtiquetas = Arrays.asList(etiquetasArray);
            Set<Etiqueta> listaEtiquetas = new HashSet<>();
            for (String etiquetaStr : etiquetasArray) {
                Etiqueta etiqueta = new Etiqueta(etiquetaStr); // Suponiendo que hay un constructor en la clase Etiqueta
                servicioEti.crear(etiqueta);
                listaEtiquetas.add(etiqueta);
            }
            articuloExistente.setListaEtiqueta(listaEtiquetas);


            // Llamar al método de servicio para editar el artículo
            servicio_art.editar(articuloExistente);


            // Redirigir a la página de visualización del artículo
            ctx.redirect("/verArticulo/" + articuloExistente.getId());

        });


        app.post("/publicar", ctx -> {
            /*long nuevoId = ultimoId + 1;
            ultimoId = nuevoId;*/
            String titulo = ctx.formParam("titulo");
            String cuerpo = ctx.formParam("cuerpo");
            String etiquetas = ctx.formParam("etiquetas");

            String username = ctx.sessionAttribute("username");

            Usuario autor = servicio_usuario.findByUsername(username);
            LocalDate fecha =  LocalDate.now();

            String[] etiquetasArray = etiquetas.split(", ");
            //List<Etiqueta> listaEtiquetas = Arrays.asList(etiquetasArray);
            Set<Etiqueta> listaEtiquetas = new HashSet<>();
            for (String etiquetaStr : etiquetasArray) {
                Etiqueta etiqueta = new Etiqueta(etiquetaStr); // Suponiendo que hay un constructor en la clase Etiqueta
                servicioEti.crear(etiqueta);
                listaEtiquetas.add(etiqueta);
            }

            Articulo tmp = new Articulo(titulo,cuerpo,autor,fecha, listaEtiquetas);

            if (servicio_art.getArticuloPorID(tmp.getId()) == null) {
                if(servicio_art.autenticarArticulo(tmp.getId(), titulo,cuerpo)){
                    servicio_art.crear(tmp);
                }else {
                    System.out.println("Campos necesarios");
                    ctx.redirect("/publicar");
                }
                ctx.redirect("/");
            } else {
                ctx.html("Este articulo ya existe. <a href='/nuevoUsuario'>Volver a intentar</a>");
            }
        });

        app.get("/verArticulo/{id}", ctx ->{
            Articulo articulo = servicio_art.find(ctx.pathParamAsClass("id", long.class).get());
            //servicio_art.setArtActual(articulo);
            ctx.sessionAttribute("artActual", articulo);

            //List<Comentario> listaComen = servicio_art.getArtActual().getListaComentarios();
            List<Comentario> listaComen = servicioComentario.consultaNativa(articulo);

            //List<Usuario> lista = servicio_art.get();
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Vista Formulario"+articulo.getId());
            modelo.put("articulo", articulo);
            modelo.put("listaComen", listaComen);
            modelo.put("accion", "/verArticulo");

            ctx.render("publico/VistaArticulo.html", modelo);
        });

        app.post("/comentario", ctx ->{
            String comentario = ctx.formParam("comentario");
            UsuarioColeccion autor = servicio_usuario.getUsuarioLogeado();
            Articulos articulo = servicio_art.getArtActual();

            long comenId = comentarioId + 1;
            comentarioId = comenId;

            Comentario nuevoComentario = new Comentario(comenId,comentario,autor,articulo);
            servicio_art.agregarComentario(articulo, nuevoComentario);
            ctx.redirect("/verArticulo/"+articulo.getId());

        });

        /*app.get("/eliminar/{id}", ctx -> {
            servicio_art.borrarComentario(ctx.pathParamAsClass("id", long.class).get(), servicio_art.getArtActual());
            ctx.redirect("/verArticulo/"+servicio_art.getArtActual().getId());
        });*/



    }
}
