package edu.pucmm.pw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        try {
            System.out.print("Ingrese una URL: ");
            String urlValidacion = scanner.nextLine();
            URL url = new URL(urlValidacion);
            //urlValida = true;

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder(URI.create(String.valueOf(url))).build();
            HttpResponse<Void> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.discarding());

            String tipoRecurso = respuesta.headers().firstValue("Content-Type").orElse("Tipo del recurso no encontrado");
            System.out.println("Tipo de recurso: " + tipoRecurso);

            if(tipoRecurso.contains("text/html")){
                //1
                Document doc = Jsoup.connect(String.valueOf(url)).get();
                String html = doc.html();
                int cantLineas = html.split("\n").length;
                System.out.println("Cantidad de lineas: " + cantLineas);
               //2
                Elements parrafo = doc.select("p");
                int cantParr = parrafo.size();
                System.out.println("Cantidad de parrafos: " + cantParr);
                //3
                int cantImg = 0;
                for(Element p : parrafo){
                    cantImg += parrafo.select("img").size();
                }
                System.out.println("Cantidad de imagenes dentro de los parrafos: " + cantImg);

                //4
                Elements form = doc.select("form");
                System.out.println("Cantidad de formularios: " + form.size());

                int cantPost = 0, cantGet = 0;
                for(Element f : form){
                    String metodo = f.attr("method");
                    if(metodo.equalsIgnoreCase("POST")){
                        cantPost++;
                    }
                    if(metodo.equalsIgnoreCase("GET")){
                        cantGet++;
                    }
                }
                System.out.println("Cantidad de formularios por metodo POST: " + cantPost);
                System.out.println("Cantidad de formularios por metodo GET: " + cantGet);


                //5
                for(Element f : form){
                    System.out.println("Campos input");
                    Elements input = f.select("input");
                    for(Element i : input){
                        System.out.println("Tipo: " + i.attr("type"));
                    }
                }

                //6
                for(Element f : form){
                    String metodo = form.attr("method");
                    if(metodo.equalsIgnoreCase("POST")){
                        Document respServer = Jsoup.connect(String.valueOf(url))
                                .data("asignatura","practica1")
                                .header("matricula-id","10144230")
                                .post();
                        System.out.println("Respuesta de la peticion POST: " + respServer.body().text());
                    }

                }
            }


        } catch (MalformedURLException e) {
            System.out.println("URL no valida. Ingrese otra URL");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}