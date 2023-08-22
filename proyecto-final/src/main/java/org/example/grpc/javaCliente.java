package org.example.grpc;

import com.google.gson.reflect.TypeToken;
import formulariorn.FormularioRn;
import formulariorn.ServicioListadoGrpc;
import formulariorn.ServicioCreacionGrpc;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import org.example.entidades.Agente;
import org.example.servicios.ServicioAgente;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Scanner;



public class javaCliente {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        ServicioListadoGrpc.ServicioListadoBlockingStub stubListado = ServicioListadoGrpc.newBlockingStub(channel);
        ServicioCreacionGrpc.ServicioCreacionBlockingStub stubCreacion = ServicioCreacionGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        String opcion = "";

        while (continuar) {

            System.out.println("\n\n------------------------------------------------------");
            System.out.println("SERVICIO GRPC");
            System.out.println("\tOpciones: ");
            System.out.println("1. Listado de los formularios publicados por un usuario");
            System.out.println("2. Creacion de Formulario");
            System.out.println("3. Salir");

            System.out.println("\nDigita la opcion: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> {
                    System.out.println("Listado de los formularios publicados por un usuario");
                    System.out.println("\nDigite el usuario al que quiere seleccionar: ");
                    String user = scanner.nextLine();
                    Agente usuario = buscarUsuarioPorUser(user);

                    // Obtener listado de URLs para un usuario
                    FormularioRn.ListarFormsRequest requestListado = FormularioRn.ListarFormsRequest.newBuilder().setUsernameAgente(usuario.getUsuario()).build();
                    FormularioRn.ListarFormsResponse responseListado = stubListado.listarFormularios(requestListado);

                    String usuario_request_listado = requestListado.getUsernameAgente();

                    System.out.println("Listado de Formularios para el usuario " + usuario_request_listado + ":");

                    // Creamos una instancia de Gson
                    Gson gson = new Gson();

                    String json = responseListado.getJson();

                    // Convertimos la cadena JSON a una lista de objetos Acortador
                    Type tipoListaForm = new TypeToken<List<FormularioRn.Formulario>>() {
                    }.getType();
                    List<FormularioRn.Formulario> formularioList = gson.fromJson(json, tipoListaForm);

                    for (FormularioRn.Formulario form : formularioList) {
                        System.out.println("----------------------------");
                        System.out.println("Nombre: " + form.getName());
                        System.out.println("Sector: " + form.getSector());
                        System.out.println("Nivel Escolar: " + form.getLevel());
                        System.out.println("Agente: " + form.getUsuario());
                        System.out.println("----------------------------\n");
                    }

                }
                case "2" -> {
                    System.out.println("\nCrear Formulario");

                    System.out.println("Nombre Completo: ");
                    String name = scanner.nextLine();

                    System.out.println("Sector: ");
                    String sector = scanner.nextLine();

                    System.out.println("Nivel academico:" + "\n" + "-Basico" + "\n" + "-Medio" + "\n" + "-Universitario"
                            + "\n" + "-Posgrado" + "\n" + "-Doctorado");
                    String level = scanner.nextLine();

                    // Crear solicitud para el servicio de creación de URLs
                    JsonObject jsonRequestCreacion = new JsonObject();
                    jsonRequestCreacion.addProperty("name", name);
                    jsonRequestCreacion.addProperty("level", level);
                    jsonRequestCreacion.addProperty("sector", sector);
                    jsonRequestCreacion.addProperty("usuario", "invitado");
                    jsonRequestCreacion.addProperty("imagenBase64", "");
                    jsonRequestCreacion.addProperty("longitud", "");
                    jsonRequestCreacion.addProperty("latitud", "");




                    // Obtener imagen previa de la URL original
               /* try {
                    URL url = new URL(urloriginal);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);

                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
                    jsonRequestCreacion.addProperty("imagen_previa", base64Image);

                } catch (IOException e) {
                    System.out.println("Error al obtener la imagen previa: " + e.getMessage());
                }*/

                    Gson gson2 = new Gson();
                    String json2 = gson2.toJson(jsonRequestCreacion);
                    FormularioRn.CrearFormularioRequest requestCreacion = FormularioRn.CrearFormularioRequest.newBuilder().setJson(json2).build();

                    // Realizar llamada al servicio de creación de URLs
                    FormularioRn.CrearFormularioResponse responseCreacion = stubCreacion.crearFormulario(requestCreacion);

               /* System.out.println("URL creada para el usuario " + responseCreacion.getUsuario() + ":");
                System.out.println("URL completa: " + responseCreacion.getUrlCompleta());
                System.out.println("URL acortada: " + responseCreacion.getUrlAcortada());
                System.out.println("Fecha de creación: " + responseCreacion.getFechaCreacion().toString());
                */
                    System.out.println("Formulario creado");

                    // Obtener imagen previa en base64
                    //String urlImagen = responseCreacion.getUrlAcortada();
                    // String imagenPreviaBase64 = obtenerImagenPreviaBase64(urlImagen);
                /*if (imagenPreviaBase64 != null) {
                    System.out.println("Imagen previa en base64: " + imagenPreviaBase64);
                } else {
                    System.out.println("No se pudo obtener la imagen previa.");
                }*/

                }
                case "3" -> {
                    continuar = false;
                    break;
                }
                default -> System.out.println("Opción inválida, selecciona una opción del 1 al 3");
            }

        }
    }

    private static Agente buscarUsuarioPorUser(String username) {
        Agente usuario = null;
        List<Agente> listaUsuarios = ServicioAgente.getInstancia().listarAgente();
        for (Agente user: listaUsuarios) {
            if(user.getUsuario().equalsIgnoreCase(username)){
                usuario = user;
            }
        }

        if (usuario != null) {
            System.out.println("El usuario encontrado es: " + usuario.getUsuario());
        } else {
            System.out.println("El usuario no se encontró en la base de datos.");
        }

        return usuario;
    }
}