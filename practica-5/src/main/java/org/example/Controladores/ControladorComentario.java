package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Util.BaseControlador;
import org.example.entidades.Articulo;
import org.example.entidades.Comentario;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioComentario;
import org.example.servicios.ServicioUsuario;

public class ControladorComentario extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();

    private static ServicioComentario servicioComentario = ServicioComentario.getInstancia();


    public ControladorComentario(Javalin app) {
        super(app);
    }

    public void aplicarRutas() {
        app.before("/comentario", ctx -> {
            String username = ctx.sessionAttribute("username");

            if (servicio_usuario.findByUsername(username).getUsername() == null) {
                Articulo articulo = ctx.sessionAttribute("artActual");
                System.out.println("Necesita iniciar sesion");
                ctx.redirect("/verArticulo/" + articulo.getId());
            }
        });


        app.post("/comentario", ctx -> {
            String username = ctx.sessionAttribute("username");
            String comentario = ctx.formParam("comentario");
            Usuario autor = servicio_usuario.findByUsername(username);
            Articulo articulo = ctx.sessionAttribute("artActual");


            Comentario nuevoComentario = new Comentario(comentario, autor, articulo);
            servicioComentario.crear(nuevoComentario);

            articulo.getListaComentario().add(nuevoComentario); // Agregar el nuevo comentario a la lista de comentarios del artículo

            servicio_art.editar(articulo);

            ctx.redirect("/verArticulo/" + articulo.getId());

        });

        app.before("/eliminar/{id}", ctx -> {
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.findByUsername(username);
            if (usuario.isAutor() == false) {
                System.out.println("Necesita ser ADMIN o autor para eliminar un comentario");
                Articulo articulo = ctx.sessionAttribute("artActual");
                ctx.redirect("/verArticulo/" + articulo.getId());
            }
        });


        app.get("/eliminar/{id}", ctx -> {
            servicioComentario.eliminar(ctx.pathParamAsClass("id", long.class).get());
            Articulo articulo = ctx.sessionAttribute("artActual");
            ctx.redirect("/verArticulo/" + articulo.getId());
        });

    }
}
