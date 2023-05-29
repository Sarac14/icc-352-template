package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.ContentType;
import io.javalin.rendering.JavalinRenderer;
import org.example.Colecciones.Usuario;
import org.example.Controladores.ControladorArticulo;
import org.example.Controladores.ControladorLogin;
import org.example.Controladores.ControladorUsuario;
import org.example.Controladores.PlatillaControlador;


public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config ->{
            //configurando los documentos estaticos.
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.precompress=false;
                staticFileConfig.aliasCheck=null;
            });

        });
        app.start(7070);

        /*new ControladorArticulo(app).aplicarRutas();
        new ControladorComentario(app).aplicarRutas();
        new ControladorEtiqueta(app).aplicarRutas();
        new ControladorUsuario(app).aplicarRutas();*/
        //new PlatillaControlador(app).aplicarRutas();

        new ControladorLogin(app).aplicarRutas();
        new ControladorUsuario(app).aplicarRutas();
        new ControladorArticulo(app).aplicarRutas();


    }



}