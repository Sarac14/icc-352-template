package org.example.Controladores;


import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.Util.BaseControlador;
import org.example.entidades.Articulo;
import org.example.servicios.GestionDb;

import java.util.ArrayList;
import java.util.List;

public class ControladorEtiqueta extends BaseControlador {
    public ControladorEtiqueta(Javalin app){super (app);}

    @Override
    public void aplicarRutas() {
     app.get("/vistaArticulo/{eti}",ctx -> {

     });

    }

    public void getArticulosbyEtiqueta(Context context){
        String etiqueta = context.pathParam("etiqueta");

        try{
            EntityManager em = GestionDb.getEntityManager();
            Query query = em.createQuery("SELECT A FROM Articulo A JOIN A.ListaEtiqueta E WHERE E.etiqueta like :Etiqueta");
            query.setParameter("etiqueta", etiqueta+"%");

            List<Articulo> Lista = query.getResultList();


        } catch (Exception e){
            System.out.println();
        }
    }
}
