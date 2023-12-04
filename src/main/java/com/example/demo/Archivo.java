package com.example.demo;

import java.util.Date;

public class Archivo {
    int id;
    String nombreUsuario;
    String nombreArchivo;
    String contenido;
    String fechaCreacion;

    public Archivo(String nombreArchivo, String fechaCreacion) {
        this.nombreArchivo = nombreArchivo;
        this.fechaCreacion = fechaCreacion;
    }

    public Archivo(int id, String nombreUsuario, String nombreArchivo, String contenido, String fechaCreacion) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombreArchivo = nombreArchivo;
        this.contenido = contenido;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
