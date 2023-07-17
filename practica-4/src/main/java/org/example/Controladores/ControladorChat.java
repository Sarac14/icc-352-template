package org.example.Controladores;

import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import org.eclipse.jetty.websocket.api.Session;
import org.example.Util.BaseControlador;
import org.example.clases.chat;
import org.example.entidades.Usuario;
import org.example.servicios.ServicioChat;
import org.example.servicios.ServicioUsuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ControladorChat extends BaseControlador {

    public static List<Session> usuariosConectados = new ArrayList<>();
    public static Map<String, Map<String, Session>> usuariosPorChat = new ConcurrentHashMap<>();
    public static Map<String, StringBuilder> mensajesChat = new ConcurrentHashMap<>();
    ServicioChat chatService = ServicioChat.getInstancia();

    ServicioUsuario servicio_usuario = ServicioUsuario.getInstancia();

    public ControladorChat(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {

        app.post("/chat", ctx -> {
            String nombreUsuario = ctx.formParam("nombre");

            chat nuevoChat = new chat();
            nuevoChat.setUsuario(nombreUsuario);

            ctx.sessionAttribute("usuarioChat", nombreUsuario);
            ctx.sessionAttribute("IdChat", nuevoChat.getId());

            chatService.crearChat(nuevoChat);

            ctx.redirect("chatbox.html?idChat=" + nuevoChat.getId());
        });

        app.get("/responder", ctx -> {
            String idChat = ctx.queryParam("idChat");
            ctx.redirect("/chatbox.html?idChat=" + idChat);

        });

        app.get("/chat", ctx -> {
            ctx.redirect("chatbox.html");
        });

        app.before("/listaChats", ctx -> {
            String username = ctx.sessionAttribute("username");
            Usuario usuario = servicio_usuario.findByUsername(username);
            if (usuario == null) {
                System.out.println("Necesita ser ADMIN o autor para responder mensajes");
                ctx.redirect("/");
            }
        });

        app.get("/listaChats", ctx -> {
            List<chat> lista = chatService.getListaChat();
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("lista", lista);
            ctx.render("publico/ListaDeChats.html", modelo);
        });

        app.ws("/mensajeServidor/{idChat}", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexión Iniciada - " + ctx.getSessionId());
                usuariosConectados.add(ctx.session);

                String chatId = ctx.pathParam("idChat");
                usuariosPorChat.computeIfAbsent(chatId, key -> new ConcurrentHashMap<>()).put(ctx.getSessionId(), ctx.session);
                //System.out.println(mensajesChat.get(chatId).toString());

                StringBuilder mensaje = mensajesChat.get(chatId);
                if (mensaje != null) {
                    System.out.println("HAY mensajes previos \n");
                    String mensajesPrevios = mensaje.toString();
                    if (!mensajesPrevios.isEmpty()) {
                        String[] mensajesArray = mensajesPrevios.split("\n");
                        StringBuilder mensajesConCorchetes = new StringBuilder();
                        for (String msg : mensajesArray) {
                            //mensajesConCorchetes.append(msg).append(",\n");

                            ctx.session.getRemote().sendString(msg.toString());
                        }
                        System.out.println(mensajesConCorchetes);

                    }
                } else {
                    System.out.println("no hay mensajes previos");
                    mensaje = new StringBuilder();
                    mensajesChat.put(chatId, mensaje);
                }


            });

            ws.onMessage(ctx -> {
                String chatId = ctx.pathParam("idChat");
                chat newChat = chatService.getChatPorId(Integer.parseInt(chatId));
                System.out.println("Mensaje Recibido de " + ctx.getSessionId() + " ====== ");
                System.out.println("Mensaje: " + ctx.message());
                String mensaje = ctx.message();
                newChat.setSolicitud(mensaje);
                String username = ctx.sessionAttribute("username");
                Usuario usuario = servicio_usuario.findByUsername(username);
                enviarMensajeAClientesConectados(mensaje, chatId, usuario);
                insertarMensajesEnMensajesSala(chatId, mensaje);
            });

            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario " + ctx.getSessionId() + " ====== ");
                System.out.println("Mensaje: " + ctx.data().length);
                String chatId = ctx.pathParam("idChat");

                usuariosPorChat.get(chatId).remove(ctx.getSessionId());
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - " + ctx.getSessionId());
                usuariosConectados.remove(ctx.session);

                String chatId = ctx.pathParam("idChat");
                usuariosPorChat.get(chatId).remove(ctx.getSessionId());
            });

            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });
    }

    public static void insertarMensajesEnMensajesSala(String chatId, String mensaje) {
        StringBuilder mensajesSala = mensajesChat.computeIfAbsent(chatId, k -> new StringBuilder());

        // Obtener los mensajes separados por corchetes
        String[] mensajesSeparados = mensaje.split("\\]\\s*\\[");
        for (String mensajeSeparado : mensajesSeparados) {
            // Eliminar los corchetes y espacios en blanco del mensaje
            String mensajeLimpio = mensajeSeparado.replaceAll("[\\[\\]\\s]", "").trim();
            if (!mensajeLimpio.isEmpty()) {
                // Enviar cada mensaje por separado
                mensajesSala.append(mensajeSeparado);

                mensajesSala.append('\n');
            }
        }
    }


    public static void enviarMensajeAClientesConectados(String mensaje, String chatId, Usuario usuario) {
        String user = ServicioChat.getInstancia().buscarUsuarioPorId(chatId);
        if (usuario != null) {
            for (Session sesionConectada : usuariosPorChat.get(chatId).values()) {
                try {
                    String mensajeConNombre = "<strong>" + usuario.getNombre() + ": </strong>" + mensaje;
                    sesionConectada.getRemote().sendString(mensajeConNombre);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (usuario == null){
            for (Session sesionConectada : usuariosPorChat.get(chatId).values()) {
                try {
                    String mensajeConNombre = "<strong>" + user + ": </strong>" + mensaje;
                    sesionConectada.getRemote().sendString(mensajeConNombre);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
