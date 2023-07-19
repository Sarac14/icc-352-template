package org.example.clases;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class chat {
    private static AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private String usuario; //usuario que inicia el chat
    private String agente;//Admin o autor que responde
    private String solicitud;//Mensaje que saldra en la lista de chats

    public String getUsuario() {
        return usuario;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public void setId(int id) {
        this.id = id;
    }

    public chat() {
        this.id = idGenerator.incrementAndGet();
    }

    public int getId() {
        return id;
    }


    public chat(int id, String usuario, String agente) {
        this.id = id;
        this.usuario = usuario;
        this.agente = agente;
    }

}
