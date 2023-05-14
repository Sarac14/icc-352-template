package edu.pucmm.pw;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una URL: ");
        try{
            //Obtenemos y validamos la URL
            String urlValidacion =  scanner.nextLine();
            URL url = new URL(urlValidacion);


            HttpClient cliente = HttpClient.newHttpClient();
            //Solicitud a pagina web
            HttpRequest solicitud = HttpRequest.newBuilder(URI.create(String.valueOf(url))).build();//ENTRE COMILLAS VA LA URL QUE QUEREMOS ACCEDER

            HttpResponse respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            //System.out.println(respuesta.body());

        } catch (MalformedURLException e){
        System.out.println("URL no valida. Ingrese otra URL");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}