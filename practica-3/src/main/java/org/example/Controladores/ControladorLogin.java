package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Colecciones.Articulos;
import org.example.Colecciones.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioUsuario;
import org.example.Util.BaseControlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorLogin extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();

    public ControladorLogin(Javalin app){super (app);}

    public void aplicarRutas() {

        app.before("/", ctx -> {
            Map<String, Object> modelo = new HashMap<>();

            /*if(servicio_usuario.getUsuarioLogeado() == null){
                modelo.put("accion", "LOG IN");
            }else{
                modelo.put("accion", "LOG OUT");
            }*/

            ctx.render("publico/index.html", modelo);

        });

        app.get("/", ctx ->{

                ctx.redirect("/listar");
        });

        app.get("/listar", ctx ->{
            List<Articulos> lista = servicio_art.getListaArticulos();
            Map<String, Object> modelo = new HashMap<>();

            modelo.put("titulo", "Inicio");
            modelo.put("lista", lista);
            //modelo.put("articulo", articulo);
            ctx.render("publico/index.html", modelo);

        });


        app.get("/login", ctx -> {
            ctx.redirect("/login.html");
        });

        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            if (servicio_usuario.autenticarUsuario(username,password) != null) {
                Usuario usuario = servicio_usuario.getUsuarioPorUsuario(username);
                servicio_usuario.setUsuarioLogeado(usuario);
                ctx.redirect("/");
            } else {
                ctx.html("Credenciales incorrectas. <a href='/login'>Volver a intentar</a>");
            }
        });


    }


}
