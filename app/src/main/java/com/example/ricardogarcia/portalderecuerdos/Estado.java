package com.example.ricardogarcia.portalderecuerdos;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class Estado {
    private Usuario persona; //principal foranea
    private EstadoRecuerdo estado;

    public Usuario getPersona() {
        return persona;
    }

    public void setPersona(Usuario persona) {
        this.persona = persona;
    }

    public EstadoRecuerdo getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecuerdo estado) {
        this.estado = estado;
    }
}
