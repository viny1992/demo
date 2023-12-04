package com.example.demo;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int id;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private String email;

    public Usuario(int id, String nombreUsuario, String nombre, String apellidos, String email){
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nId: " + id +
                "\nNombreUsuario: " + nombreUsuario +
                "\nNombre: " + nombre +
                "\nApellidos: " + apellidos +
                "\nEmail: " + email + "\n";
    }
}