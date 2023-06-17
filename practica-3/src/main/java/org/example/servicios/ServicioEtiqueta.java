package org.example.servicios;

import org.example.entidades.Articulo;
import org.example.entidades.Etiqueta;

public class ServicioEtiqueta extends GestionDb<Etiqueta> {
    private static  ServicioEtiqueta instancia;
    private ServicioEtiqueta() {
        super(Etiqueta.class);
    }

    public static ServicioEtiqueta getInstancia(){
        if(instancia==null){
            instancia = new ServicioEtiqueta();
        }
        return instancia;
    }
}
