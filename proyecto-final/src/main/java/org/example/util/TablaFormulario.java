package org.example.util;

public enum TablaFormulario {
    formulario("formulario");
    private String valor;

    TablaFormulario(String valor){
        this.valor =  valor;
    }

    public String getValor() {
        return valor;
    }
}
