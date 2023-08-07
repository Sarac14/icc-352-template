package org.example.controladores;


import org.example.entidades.Agente;
import org.example.servicios.ServicioAgente;
import org.example.util.BaseControlador;
import io.javalin.Javalin;
import org.example.util.NoExisteAgenteException;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ApiControlador extends BaseControlador {

    private ServicioAgente agenteService = ServicioAgente.getInstancia();

    public ApiControlador(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/api", () -> {
                /**
                 * Ejemplo de una API REST, implementando el CRUD
                 * ir a
                 */
                path("/estudiante", () -> {
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });

                    get("/", ctx -> {
                        ctx.json(agenteService.listarAgente());
                    });

                    get("/{matricula}", ctx -> {
                        ctx.json(agenteService.getAgentePorUsuario(ctx.pathParam("usuario")));
                    });

                    post("/", ctx -> {
                        //parseando la informacion del POJO debe venir en formato json.
                        Agente tmp = ctx.bodyAsClass(Agente.class);
                        //creando.
                        ctx.json(agenteService.crearAgente(tmp));
                    });

                    put("/", ctx -> {
                        //parseando la informacion del POJO.
                        Agente tmp = ctx.bodyAsClass(Agente.class);
                        //creando.
                        ctx.json(agenteService.actualizarAgente(tmp));

                    });

                    delete("/{matricula}", ctx -> {
                        //creando.
                        ctx.json(agenteService.eliminandoAgente(ctx.pathParam("usuario")));
                    });
                });
            });
        });

        app.exception(NoExisteAgenteException.class, (exception, ctx) -> {
            ctx.status(404);
            ctx.json(""+exception.getLocalizedMessage());
        });
    }
}
