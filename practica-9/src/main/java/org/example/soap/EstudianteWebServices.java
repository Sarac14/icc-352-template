package org.example.soap;

import org.example.encapsulaciones.Estudiante;
import org.example.servicios.FakeServices;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.io.Serializable;
import java.util.List;

/**
 * Clase para implementar un servicio web basado en SOAP
 */
@WebService
public class EstudianteWebServices implements Serializable {

    private FakeServices fakeServices = FakeServices.getInstancia();

    @WebMethod
    public String holaMundo(String hola){
        System.out.println("Ejecuntado en el servidor.");
        return "Hola Mundo "+hola+", :-D";
    }

    @WebMethod
    public String otroMetodo(String hola){
        System.out.println("Ejecuntado en el servidor.");
        return "Hola Mundo "+hola+", :-D";
    }

    @WebMethod
    public List<Estudiante> getListaEstudiante(){
        return fakeServices.listarEstudiante();
    }

    @WebMethod
    public Estudiante getEstudiante(int matricula){
        return fakeServices.getEstudiantePorMatricula(matricula);
    }

    @WebMethod
    public Estudiante crearEstudiante(Estudiante estudiante){
        System.out.println("Recibiendo: "+estudiante.toString());
        return fakeServices.crearEstudiante(estudiante);
    }

    @WebMethod
    public Estudiante actualizarEstudiante(Estudiante estudiante){
        return fakeServices.actualizarEstudiante(estudiante);
    }

}
