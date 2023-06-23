package org.example.Controladores;

import io.javalin.Javalin;
import org.example.Util.BaseControlador;
import org.example.entidades.Articulo;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioUsuario;
import org.jasypt.util.text.BasicTextEncryptor;

import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorInicio extends BaseControlador {
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();
    private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();

    private static int pageNumber = 1;


    public ControladorInicio(Javalin app){super (app);}

    public void aplicarRutas() {

        app.get("/", ctx ->{

                ctx.redirect("/listar");
        });

        app.get("/listar", ctx ->{
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.findByUsername(username);

            List<Articulo> lista = servicio_art.consultaNativa(pageNumber);
            Map<String, Object> modelo = new HashMap<>();

            modelo.put("titulo", "Inicio");
            modelo.put("lista", lista);
            modelo.put("pageNumber", pageNumber);
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
                Cookie rememberMeCookie = new Cookie("rememberMe", "");
                rememberMeCookie.setMaxAge(0);
                ctx.res().addCookie(rememberMeCookie);
                ctx.sessionAttribute("username", null);
                ctx.redirect("/");
            }
        });

        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            boolean rememberMe = ctx.formParam("rememberMe") != null; // Verifica si la opción "recordar usuario" está marcada

            if (servicio_usuario.autenticarUsuario(username, password) != null) {
                ctx.sessionAttribute("username", username);

                // Verifica si se debe crear la cookie de recordar usuario
                if (rememberMe) {
                    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
                    textEncryptor.setPassword("claveSecreta");

                    String encryptedUsername = textEncryptor.encrypt(username);

                    // Crear la cookie y establecer sus propiedades
                    Cookie rememberMeCookie = new Cookie("rememberMe", encryptedUsername);
                    rememberMeCookie.setMaxAge(7 * 24 * 60 * 60); // Duración de una semana en segundos

                    // Agregar la cookie a la respuesta
                    ctx.res().addCookie(rememberMeCookie);
                } else {
                    // Si la opción "recordar usuario" no está marcada, elimina la cookie si existe
                    Cookie rememberMeCookie = new Cookie("rememberMe", "");
                    rememberMeCookie.setMaxAge(0); // Establecer tiempo de vida de la cookie a cero para eliminarla
                    ctx.res().addCookie(rememberMeCookie);
                }

                ctx.redirect("/");
            } else {
                ctx.html("Credenciales incorrectas. <a href='/login'>Volver a intentar</a>");
            }
        });

        app.before(ctx -> {
            // Verificar si la cookie de recordar usuario existe
            Map<String, String> cookieMap = ctx.cookieMap();
            String rememberMeCookieValue = cookieMap.get("rememberMe");
            if (rememberMeCookieValue != null) {
                Cookie rememberMeCookie = new Cookie("rememberMe", rememberMeCookieValue);
                String encryptedUsername = rememberMeCookie.getValue();
                BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
                textEncryptor.setPassword("claveSecreta");
                String decryptedUsername = textEncryptor.decrypt(encryptedUsername);

                // Establecer la sesión del usuario automáticamente
                ctx.sessionAttribute("username", decryptedUsername);
            }
        });

        app.get("/olderPost", ctx -> {
            pageNumber ++;
            ctx.redirect("/");
        });

        app.get("/volver", ctx -> {
            pageNumber --;
            ctx.redirect("/");
        });
    }


}
