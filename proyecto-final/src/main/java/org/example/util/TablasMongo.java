package org.example.util;

public enum TablasMongo {
    agente("agente"), formulario("formulario");

    private String valor;

    TablasMongo(String valor){
        this.valor =  valor;
    }

    public String getValor() {
        return valor;
    }
}
