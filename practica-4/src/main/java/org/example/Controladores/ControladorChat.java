package org.example.Controladores;

import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import io.javalin.http.Context;
import org.eclipse.jetty.websocket.api.Session;
import org.example.Util.BaseControlador;
import org.example.clases.chat;
import org.example.entidades.Articulo;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioChat;
import org.example.servicios.ServicioUsuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static j2html.TagCreator.p;

public class ControladorChat extends BaseControlador {

    public static List<Session> usuariosConectados = new ArrayList<>();
    public static List<SseClient> listaSseUsuario = new ArrayList<>();

    public static Map<String, List<Session>> usuariosPorChat = new HashMap<>();
    ServicioChat chatService = ServicioChat.getInstancia();
    ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();

    int idChat = 0;


    public ControladorChat(Javalin app){super (app);}

    @Override
    public void aplicarRutas() {

        app.post("/chat", ctx -> {
            idChat++;
            int nuevoIdChat = idChat;
            String nombreUsuario = ctx.formParam("nombre");

            chat nuevoChat = new chat();
            nuevoChat.setId(nuevoIdChat);

            nuevoChat.setUsuario(nombreUsuario);

            ctx.sessionAttribute("usuarioChat", nombreUsuario);
            ctx.sessionAttribute("IdChat", nuevoIdChat);


            // String user = ctx.sessionAttribute("usuarioChat");

            chatService.crearChat(nuevoChat);



            ctx.redirect("/chat");
        });

        app.get("/responder", ctx -> {

            String agente = ctx.sessionAttribute("username");
            ctx.sessionAttribute("usuarioChat", agente);


            ctx.redirect("/chat");
        });


        app.get("/chat", ctx -> {

            ctx.redirect("chatbox.html");
        });

        app.before("/listaChats", ctx -> {
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.findByUsername(username);
            if (usuario.isAutor() == false) {
                System.out.println("Necesita ser ADMIN o autor para responder mensajes");
                ctx.redirect("/" );
            }
        });

        app.get("/listaChats", ctx ->{
            List<chat> lista = chatService.getListaChat();
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("lista", lista);
            ctx.render("publico/ListaDeChats.html", modelo);

        });

        app.get("/enviarMensaje", ctx -> {
            String mensaje = ctx.queryParam("mensaje");

            String user = ctx.sessionAttribute("usuarioChat");

            enviarMensajeAClientesConectados(mensaje, "rojo",user);
            ctx.result("Enviando mensaje: " + mensaje);
        });

        app.wsBefore("/mensajeServidor", wsHandler -> {
            System.out.println("Filtro para WS antes de la llamada ws");
            //ejecutar cualquier evento antes...
        });

        app.ws("/mensajeServidor", ws -> {

            ws.onConnect(ctx -> {
                System.out.println("Conexión Iniciada - " + ctx.getSessionId());
                usuariosConectados.add(ctx.session);
                String chatId = ctx.queryParam("chatId");
                if (chatId != null) {
                    usuariosPorChat.computeIfAbsent(chatId, key -> new ArrayList<>()).add(ctx.session);
                }
            });

            ws.onMessage(ctx -> {
                //Puedo leer los header, parametros entre otros.
                ctx.headerMap();
                ctx.pathParamMap();
                ctx.queryParamMap();
                //
                System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.message());
                System.out.println("================================");

                String mensaje = ctx.message();

                if(ctx.sessionAttribute("IdChat") != null) {
                    chat chat = chatService.getChatPorId(ctx.sessionAttribute("IdChat"));
                    chat.setSolicitud(mensaje);
                }

               // if (chatId != null) {
                String user = ctx.sessionAttribute("usuarioChat");

                enviarMensajeAClientesConectados( ctx.message(), "azul",user);
               // }
            });


           ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.data().length);
                System.out.println("================================");
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - " + ctx.getSessionId());
                usuariosConectados.remove(ctx.session);
                usuariosPorChat.values().forEach(sessions -> sessions.remove(ctx.session));
            });

            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });

        app.wsAfter("/mensajeServidor", wsHandler -> {
            System.out.println("Filtro para WS despues de la llamada al WS");
            //ejecutar cualquier evento antes...
        });
        app.sse("/evento-servidor", sseClient -> {
            System.out.println("Agregando cliente para evento del servidor: ");
            sseClient.keepAlive();
            sseClient.sendEvent("conectado","Conectando ");
            listaSseUsuario.add(sseClient);
            sseClient.onClose(() -> {
                listaSseUsuario.remove(sseClient);
            });
        });

        new Thread(() -> {
            while(true){
                List<SseClient> listaTmp = new CopyOnWriteArrayList<>(listaSseUsuario);
                listaTmp.forEach(sseClient -> {
                    System.out.println("Enviando informacion...");
                    sseClient.sendEvent("ping", ""+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    public static void enviarMensajeAClientesConectados(String mensaje, String color, String nombreUsuario) {
        for(Session sesionConectada : usuariosConectados) {
            try {
                String mensajeConNombre = "<strong>" + nombreUsuario + ":</strong> " + mensaje;
                sesionConectada.getRemote().sendString(mensajeConNombre);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
