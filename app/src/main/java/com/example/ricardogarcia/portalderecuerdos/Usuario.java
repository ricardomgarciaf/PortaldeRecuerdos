package com.example.ricardogarcia.portalderecuerdos;

import java.util.List;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class Usuario {

    private String id;
    private String nombre;
    private String password;
    private List<Usuario> amigos;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }
}
