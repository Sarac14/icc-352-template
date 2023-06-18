package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import org.example.Controladores.*;
import org.example.entidades.Usuario;
import org.example.servicios.BootStrapServices;
import org.example.servicios.ServicioUsuario;

public class Main {
    private static String modoConexion = "";
    private static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();


    public static void main(String[] args) {

        if(args.length >= 1){
            modoConexion = args[0];
            //System.out.println("Modo de Operacion: "+modoConexion);
        }

        if(modoConexion.isEmpty()) {
            BootStrapServices.getInstancia().init();
        }


        ServicioUsuario.getInstancia().crear(new Usuario("admin", "admin", "admin",true, true));
        ServicioUsuario.getInstancia().crear(new Usuario("sara", "sara", "sara",false, true));

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

        //servicio_usuario.CreacionDeUsuarios();
        new ControladorInicio(app).aplicarRutas();
        new ControladorUsuario(app).aplicarRutas();
        new ControladorArticulo(app).aplicarRutas();
        new ControladorComentario(app).aplicarRutas();
        new FotoControlador(app).aplicarRutas();

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