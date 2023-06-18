package org.example.Controladores;

import io.javalin.Javalin;
import org.example.entidades.Articulo;
import org.example.entidades.Usuario;
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

        app.get("/", ctx ->{

                ctx.redirect("/listar");
        });

        app.get("/listar", ctx ->{
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.findByUsername(username);

            List<Articulo> lista = servicio_art.consultaNativa();
            Map<String, Object> modelo = new HashMap<>();

            modelo.put("titulo", "Inicio");
            modelo.put("lista", lista);
            if(usuario == null){
                modelo.put("accion", "LOG IN");
            }else{
                modelo.put("accion", "LOG OUT");
            }
            ctx.render("publico/index.html", modelo);

        });

        app.get("/login", ctx -> {
            String user = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.findByUsername(user);

            if(usuario == null) {
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Inicio de Sesion");
                ctx.render("publico/login.html", modelo);
            }else{
                ctx.sessionAttribute("username", null);
                ctx.redirect("/");
            }
        });

        app.post("/login", ctx -> {

            String username = ctx.formParam("username");
            String password = ctx.formParam("password");

            if (servicio_usuario.autenticarUsuario(username, password) != null) {
                ctx.sessionAttribute("username", username);
                ctx.redirect("/");
            } else {
                ctx.html("Credenciales incorrectas. <a href='/login'>Volver a intentar</a>");
            }

        });


    }


}
