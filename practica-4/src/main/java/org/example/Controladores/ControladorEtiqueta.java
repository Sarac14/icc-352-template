package org.example.Controladores;


import io.javalin.Javalin;
import org.example.Util.BaseControlador;
import org.example.entidades.Articulo;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioArticulo;
import org.example.servicios.ServicioUsuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorEtiqueta extends BaseControlador {

    public static ServicioArticulo servicio_art = ServicioArticulo.getInstancia();
    public static ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();

    public ControladorEtiqueta(Javalin app){super (app);}

    @Override
    public void aplicarRutas() {

     app.get("/vistaArticulo/etiqueta/{etiqueta}", ctx -> {

         String username = ctx.sessionAttribute("username");
         Usuario usuario = servicio_usuario.findByUsername(username);
         String eti = ctx.pathParam("etiqueta");

         List<Articulo> lista = servicio_art.getArticulosbyEtiqueta(eti);

         Map<String, Object> modelo = new HashMap<>();

         modelo.put("titulo", "Articulos Etiquetas");
         modelo.put("lista", lista);
         modelo.put("pageNumber", 0);
         if(usuario == null){
             modelo.put("accion", "LOG IN");
         }else{
             modelo.put("accion", "LOG OUT");
         }
         ctx.render("publico/index.html", modelo);

     });

    }
}
