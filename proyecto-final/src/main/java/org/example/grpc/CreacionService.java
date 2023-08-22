package org.example.grpc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import formulariorn.FormularioRn;
import formulariorn.ServicioCreacionGrpc;

import io.grpc.stub.StreamObserver;
import org.example.entidades.Agente;
import org.example.entidades.Formulario;
import org.example.servicios.ServicioAgente;
import org.example.servicios.ServicioForm;


public class CreacionService extends ServicioCreacionGrpc.ServicioCreacionImplBase {

    //@Override
    public void crearFormulario(FormularioRn.CrearFormularioRequest request, StreamObserver<FormularioRn.CrearFormularioResponse> responseObserver){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(request.getJson(), JsonObject.class);

        String name = jsonObject.get("name").getAsString();
        String level = jsonObject.get("level").getAsString();
        String sector = jsonObject.get("sector").getAsString();
        String usuario = jsonObject.get("usuario").getAsString();
        String imagenBase64 = jsonObject.get("imagenBase64").getAsString();

        Agente agente = new Agente("invitado","invitado","invitado","Agente");
        agente.setId("invitado");
        ServicioAgente.getInstancia().crearAgente(agente);

        Formulario nuevoFormulario = new Formulario(name,level,sector,usuario,imagenBase64);
        ServicioForm.getInstancia().crearForm(nuevoFormulario);

        FormularioRn.CrearFormularioResponse response = FormularioRn.CrearFormularioResponse.newBuilder()
                .setName(name)
                .setLevel(level)
                .setSector(sector)
                .setUsuario(usuario)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
