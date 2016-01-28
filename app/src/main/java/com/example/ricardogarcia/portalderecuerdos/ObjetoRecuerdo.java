package com.example.ricardogarcia.portalderecuerdos;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class ObjetoRecuerdo {

    private Objeto objeto;
    private Usuario asociador;

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public Usuario getAsociador() {
        return asociador;
    }

    public void setAsociador(Usuario asociador) {
        this.asociador = asociador;
    }
}
