package com.example.ricardogarcia.portalderecuerdos;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by ricardogarcia on 1/27/16.
 */
public class Recuerdo{
    private String id;
    private String nombre;
    private String horaCreacion;
    private String horaRecuerdo;
    private Usuario creador; //foranea
    private List<Estado> estados;
    private LugarRecuerdo lugar;
    private List<ObjetoRecuerdo> objetos;
    private List<NotaRecuerdo> notas;
    private List<PersonaRecuerdo> personas;

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

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public String getHoraRecuerdo() {
        return horaRecuerdo;
    }

    public void setHoraRecuerdo(String horaRecuerdo) {
        this.horaRecuerdo = horaRecuerdo;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public LugarRecuerdo getLugar() {
        return lugar;
    }

    public void setLugar(LugarRecuerdo lugar) {
        this.lugar = lugar;
    }

    public List<ObjetoRecuerdo> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<ObjetoRecuerdo> objetos) {
        this.objetos = objetos;
    }

    public List<NotaRecuerdo> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaRecuerdo> notas) {
        this.notas = notas;
    }

    public List<PersonaRecuerdo> getPersonas() {
        return personas;
    }

    public void setPersonas(List<PersonaRecuerdo> personas) {
        this.personas = personas;
    }


}
