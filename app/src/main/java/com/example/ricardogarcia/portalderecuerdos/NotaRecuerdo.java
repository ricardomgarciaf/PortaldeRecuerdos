package com.example.ricardogarcia.portalderecuerdos;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class NotaRecuerdo {

    private Nota nota;
    private Usuario asociador;

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Usuario getAsociador() {
        return asociador;
    }

    public void setAsociador(Usuario asociador) {
        this.asociador = asociador;
    }
}
