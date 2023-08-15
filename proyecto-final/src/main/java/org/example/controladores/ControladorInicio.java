package org.example.controladores;

import io.javalin.Javalin;
import jakarta.servlet.http.Cookie;
import org.example.entidades.Agente;
import org.example.servicios.ServicioAgente;
import org.example.util.BaseControlador;
import org.jasypt.util.text.BasicTextEncryptor;



import java.util.HashMap;
import java.util.Map;

public class ControladorInicio extends BaseControlador {
    private static ServicioAgente agenteService = ServicioAgente.getInstancia();
    //private static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();

    //private static int pageNumber = 1;


    public ControladorInicio(Javalin app){super (app);}

    public void aplicarRutas() {

        app.get("/", ctx ->{

                ctx.redirect("/inicio");
        });

        app.get("/inicio", ctx ->{
            Agente agente = ctx.sessionAttribute("agente");

            Map<String, Object> modelo = new HashMap<>();

            modelo.put("titulo", "Inicio");
            //modelo.put("lista", lista);
            //modelo.put("pageNumber", pageNumber);
            if(agente == null){
                modelo.put("accion", "Log In");
            }else{
                modelo.put("accion", "Log Out");
            }
            ctx.render("publico/index1.html", modelo);

        });

        app.get("/login", ctx -> {
            Agente agente = ctx.sessionAttribute("agente");
            //Agente agente = agenteService.findByUsername(user);

            if(agente == null) {
                Map<String, Object> modelo = new HashMap<>();
                modelo.put("titulo", "Inicio de Sesion");
                ctx.render("publico/login.html", modelo);
            }else{
                Cookie rememberMeCookie = new Cookie("rememberMe", "");
                rememberMeCookie.setMaxAge(0);
                ctx.res().addCookie(rememberMeCookie);
                ctx.sessionAttribute("agente", null);
                ctx.redirect("/");
            }
        });

        app.post("/login", ctx -> {
            String username = ctx.formParam("username");
            String password = ctx.formParam("password");
            Agente agente = agenteService.getAgentePorUsuario(username);
            boolean rememberMe = ctx.formParam("rememberMe") != null; // Verifica si la opción "recordar usuario" está marcada

            if (agenteService.autenticarUsuario(username, password) != null) {
                ctx.sessionAttribute("agente", agente);

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
                //ctx.html("Credenciales incorrectas. <a href='/login'>Volver a intentar</a>");
                ctx.contentType("text/html");
                ctx.html("<script>alert('Credenciales incorrectas.'); window.location.href='/login';</script>");
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
                //ctx.sessionAttribute("username", decryptedUsername);
                Agente agente = agenteService.getAgentePorUsuario(decryptedUsername);
                ctx.sessionAttribute("agente", agente);

            }
        });
    }


}
