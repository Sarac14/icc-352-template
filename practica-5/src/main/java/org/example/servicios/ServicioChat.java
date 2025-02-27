package org.example.servicios;

import org.example.clases.chat;

import java.util.ArrayList;
import java.util.List;

public class ServicioChat {
    private static ServicioChat instancia;
    private List<chat> listaChat = new ArrayList<>();

    public ServicioChat() {
    }

    public static ServicioChat getInstancia(){
        if(instancia==null){
            instancia = new ServicioChat();
        }
        return instancia;
    }

    public List<chat> getListaChat(){
        return listaChat;
    }

    public chat getChatPorId(int id){
        return listaChat.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public chat crearChat(chat chat){
        if(getChatPorId(chat.getId())!=null){
            return null; //generar una excepcion...
        }
        listaChat.add(chat);
        return chat;
    }

    public String buscarUsuarioPorId(String idChat) {
        // Iterar sobre la lista de chats y buscar el chat con el ID correspondiente
        int id = Integer.parseInt(idChat);
        for (chat chat : listaChat) {
            if (chat.getId() == id) {
                return chat.getUsuario();
            }
        }
        // Si no se encuentra el chat, se puede retornar null o algún valor por defecto
        return null;
    }

}
