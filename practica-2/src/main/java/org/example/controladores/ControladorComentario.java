package org.example.controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Articulos;
import org.example.Colecciones.Comentario;
import org.example.Colecciones.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioComentario;
import org.example.servicios.ServicioUsuario;
import org.example.util.BaseControlador;

public class ControladorComentario extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();
    private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();
    private static long ultimoId = 0;
    public ControladorComentario(Javalin app) {
        super(app);
    }

    public void aplicarRutas(){
        app.before("/comentario", ctx -> {
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.getUsuarioLogeado();

            if (usuario == null) {
                Articulos articulo = ctx.sessionAttribute("artActual");
                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/verArticulo/" + articulo.getId());
            }
        });

        app.post("/comentario", ctx -> {
            long nuevoId = ultimoId + 10;
            ultimoId = nuevoId;
            String username = servicio_usuario.getUsuarioLogeado().getUsername();
            String comentario = ctx.formParam("comentario");
            Usuario autor = servicio_usuario.getUsuarioPorUsuario(username);
            Articulos articulo = ctx.sessionAttribute("artActual");


            Comentario nuevoComentario = new Comentario(nuevoId,comentario, autor, articulo);
            servicioComentario.crearComentario(nuevoComentario);

            articulo.getListaComentarios().add(nuevoComentario); // Agregar el nuevo comentario a la lista de comentarios del artículo

            servicio_art.actualizarArticulo(articulo);

            ctx.redirect("/verArticulo/" + articulo.getId());

        });


        app.before("/eliminar/{id}", ctx -> {
            Comentario comentario = servicioComentario.getComentariosPorId(ctx.pathParamAsClass("id", long.class).get());
            String autor = comentario.getAutor().getUsername();
            String username = servicio_usuario.getUsuarioLogeado().getUsername();
            if(!servicio_usuario.getUsuarioPorUsuario(username).isAdmin()) {
                if (!autor.equals(username)) {
                    System.out.println("Necesita ser ADMIN o autor para eliminar un comentario");
                    Articulos articulo = ctx.sessionAttribute("artActual");
                    ctx.redirect("/verArticulo/" + articulo.getId());
                }
            }
        });

        app.get("/eliminar/{id}", ctx -> {
            servicioComentario.borrarComentario(ctx.pathParamAsClass("id", long.class).get());
            Articulos articulo = ctx.sessionAttribute("artActual");
            ctx.redirect("/verArticulo/" + articulo.getId());
        });

    }

}
