package org.example.Controladores;

import io.javalin.Javalin;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.example.Util.BaseControlador;


public class PlatillaControlador extends BaseControlador {

    public PlatillaControlador(Javalin app) {
        super(app);
        registrandoPlantillas();
    }

    @Override
    public void aplicarRutas() {

    }

    /**
     * Registrando los sistemas de plantillas utilizados.
     */
    private void registrandoPlantillas() {
        //Registrando los templates. es necesario incluir la libería io.javalin:javalin-rendering:5.3.2
        JavalinRenderer.register(new JavalinThymeleaf(), ".html");
    }
}
