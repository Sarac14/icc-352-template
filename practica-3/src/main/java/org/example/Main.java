package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import org.example.servicios.BootStrapServices;

public class Main {
    private static String modoConexion = "";

    public static void main(String[] args) {

        if(args.length >= 1){
            modoConexion = args[0];
            //System.out.println("Modo de Operacion: "+modoConexion);
        }

        if(modoConexion.isEmpty()) {
            BootStrapServices.getInstancia().init();
        }

        Javalin app = Javalin.create(config ->{

            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/publico";
                staticFileConfig.location = Location.CLASSPATH;
            }); //desde la carpeta de resources

            config.plugins.register(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            config.plugins.enableCors(corsContainer -> {
                corsContainer.add(corsPluginConfig -> {
                    corsPluginConfig.anyHost();
                });
            });
        }).start(getHerokuAssignedPort());

    }
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    public static String getModoConexion(){
        return modoConexion;
    }



}