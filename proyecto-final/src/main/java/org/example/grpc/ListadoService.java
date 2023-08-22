package org.example.grpc;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import formulariorn.FormularioRn;
import formulariorn.ServicioCreacionGrpc;

import formulariorn.ServicioListadoGrpc;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.example.entidades.Agente;
import org.example.entidades.Formulario;
import org.example.servicios.ServicioAgente;
import org.example.servicios.ServicioForm;

import java.util.ArrayList;
import java.util.List;

public class ListadoService extends ServicioListadoGrpc.ServicioListadoImplBase {
    @Override
    public void listarFormularios (FormularioRn.ListarFormsRequest request, StreamObserver<FormularioRn.ListarFormsResponse> responseObserver){
        try{
            String usernameAgente = request.getUsernameAgente();
            Agente agente = ServicioAgente.getInstancia().getAgentePorUsuario(usernameAgente);
            System.out.println(" El usuario es: " + usernameAgente);

            List<Formulario> listaFormularios = ServicioForm.getInstancia().listarFormularioPorUsuario(usernameAgente);

            List<FormularioRn.Formulario> listadoDeFormularios = new ArrayList<>();

            if (!listaFormularios.isEmpty()){
                listadoDeFormularios = listaFormularios.stream()
                        .map(form ->{
                            FormularioRn.Formulario.Builder builder = FormularioRn.Formulario.newBuilder()
                                    .setName(form.getName())
                                    .setLevel(form.getLevel())
                                    .setSector(form.getSector())
                                    .setUsuario(form.getUsuario());
                return builder.build();
                        }).toList();
            }
            Gson gson = new Gson();
            String json = gson.toJson(listadoDeFormularios);

            FormularioRn.ListarFormsResponse response = FormularioRn.ListarFormsResponse.newBuilder()
                    .setJson(json)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }catch (StatusRuntimeException e){
            System.out.println("Error al llamar al servidor: " + e.getStatus());
        }
    }
}
