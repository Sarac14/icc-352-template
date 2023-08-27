package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.controladores.*;

public class Main {
    public static void main(String[] args) {
            System.out.println("CRUD Javalin MongoDB");

            //Creando la instancia del servidor y configurando.
            Javalin app = Javalin.create(config ->{
                //configurando los documentos estaticos.
                config.staticFiles.add(staticFileConfig -> {
                    staticFileConfig.hostedPath = "/";
                    staticFileConfig.directory = "/publico";
                    staticFileConfig.location = Location.CLASSPATH;
                    staticFileConfig.precompress=false;
                    staticFileConfig.aliasCheck=null;
                });


                //Habilitando el CORS. Ver: https://javalin.io/plugins/cors#getting-started para más opciones.
                config.plugins.enableCors(corsContainer -> {
                    corsContainer.add(corsPluginConfig -> {
                        corsPluginConfig.anyHost();
                    });
                });

                //habilitando el plugins de las rutas definidas.
                config.plugins.enableRouteOverview("/rutas");

            });

            //Iniciando la aplicación
            app.start(getPuertoDimanico());
            app.after("/appcache.appcache", ctx -> {
                System.out.println("Llamando el cache....");
                ctx.contentType("text/cache-manifest");
            });

            //incluyendo los controladores.
        new ControladorInicio(app).aplicarRutas();
            new ApiControlador(app).aplicarRutas();
            new AgenteControlador(app).aplicarRutas();
            new FormularioControlador(app).aplicarRutas();
            new RestControlador(app).aplicarRutas();


        }

        /**
         *
         * @return
         */
        static int getPuertoDimanico() {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (processBuilder.environment().get("PORT") != null) {
                return Integer.parseInt(processBuilder.environment().get("PORT"));
            }
            return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
        }



}