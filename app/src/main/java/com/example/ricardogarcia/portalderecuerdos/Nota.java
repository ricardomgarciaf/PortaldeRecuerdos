package com.example.ricardogarcia.portalderecuerdos;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class Nota {

    private String nombre;
    private Usuario creador;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }
}
