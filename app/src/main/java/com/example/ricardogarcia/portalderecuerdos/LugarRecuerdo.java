package com.example.ricardogarcia.portalderecuerdos;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class LugarRecuerdo {
    private Lugar lugar;
    private Usuario asociador;

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Usuario getAsociador() {
        return asociador;
    }

    public void setAsociador(Usuario asociador) {
        this.asociador = asociador;
    }
}
