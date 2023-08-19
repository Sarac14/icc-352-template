package org.example.util;

public enum TablasMongo {
    agente("agente"), formulario1("formulario1");

    private String valor;

    TablasMongo(String valor){
        this.valor =  valor;
    }

    public String getValor() {
        return valor;
    }
}
