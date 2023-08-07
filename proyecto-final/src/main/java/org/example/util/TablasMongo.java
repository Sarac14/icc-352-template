package org.example.util;

public enum TablasMongo {
    agente("agente");

    private String valor;

    TablasMongo(String valor){
        this.valor =  valor;
    }

    public String getValor() {
        return valor;
    }
}
