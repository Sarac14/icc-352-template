package org.example.Controladores;

import io.javalin.Javalin;
import org.example.entidades.Articulo;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioUsuario;
import org.example.Util.BaseControlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorInicio extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();

    public ControladorInicio(Javalin app){super (app);}

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
            List<Articulo> lista = servicio_art.consultaNativa();
            Map<String, Object> modelo = new HashMap<>();

            modelo.put("titulo", "Inicio");
            modelo.put("lista", lista);
            ctx.render("publico/index.html", modelo);

        });

        app.get("/login", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Inicio de Sesion");
            ctx.render("publico/login.html",modelo);
        });

        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            if (servicio_usuario.autenticarUsuario(username,password) != null) {
                ctx.sessionAttribute("username", username);
                ctx.redirect("/");
            } else {
                ctx.html("Credenciales incorrectas. <a href='/login'>Volver a intentar</a>");
            }
        });


    }


}
