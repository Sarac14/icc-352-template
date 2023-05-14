package edu.pucmm.pw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
        boolean urlValida = false;
        while (!urlValida) {
            try {
                System.out.print("Ingrese una URL: ");
                String urlValidacion = scanner.nextLine();
                URL url = new URL(urlValidacion);
                urlValida = true;

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
                    Elements img = doc.select("img");
                    int cantImg = img.size();
                    System.out.println("Cantidad de imagenes: " + cantImg);
                }

            } catch (MalformedURLException e) {
                System.out.println("URL no valida. Ingrese otra URL");
                urlValida = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}