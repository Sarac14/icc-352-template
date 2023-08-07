package org.example.entidades;

import java.util.Objects;

public class Agente {

        private String password;
        private String nombre;
        private String usuario;
        private String id;
        private String rol;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Agente() {
        }

        public Agente(String usuario, String password, String nombre, String rol) {
            this.password = password;
            this.nombre = nombre;
            this.usuario = usuario;
            this.rol = rol;

        }

        public Agente(String usuario, String password, String nombre, String rol, String id) {
            this.password = password;
            this.nombre = nombre;
            this.usuario = usuario;
            this.id = id;
            this.rol = rol;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public void mezclar(Agente e){
            password = e.getPassword();
            nombre = e.getNombre();
            usuario = e.getUsuario();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Agente that = (Agente) o;
            return usuario == that.usuario;
        }

        @Override
        public int hashCode() {
            return Objects.hash(usuario);
        }

        @Override
        public String toString() {
            return "Agente{" +
                    "password=" + password +
                    ", nombre='" + nombre + '\'' +
                    ", usuario='" + usuario + '\'' +
                    ", rol='" + rol + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

}
