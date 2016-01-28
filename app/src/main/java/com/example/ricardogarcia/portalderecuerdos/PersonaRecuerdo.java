package com.example.ricardogarcia.portalderecuerdos;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class PersonaRecuerdo {

    private Usuario persona;
    private Usuario asociador;

    public Usuario getPersona() {
        return persona;
    }

    public void setPersona(Usuario persona) {
        this.persona = persona;
    }

    public Usuario getAsociador() {
        return asociador;
    }

    public void setAsociador(Usuario asociador) {
        this.asociador = asociador;
    }
}
