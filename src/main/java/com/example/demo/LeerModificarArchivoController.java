package com.example.demo;

import com.example.demo.GestorArchivos;
import com.example.demo.UsuarioDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeerModificarArchivoController implements Initializable {

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonGuardar;

    @FXML
    private Label nombreArchivo;

    @FXML
    private TextArea textAreaArchivo;

    @FXML
    public void cargarArchivo() {
        nombreArchivo.setText(GestorArchivos.getNombreArchivoSeleccionado());
        textAreaArchivo.setText(GestorArchivos.descodificarArchivo(UsuarioDB.recuperarArchivo(GestorArchivos.getNombreArchivoSeleccionado())));
    }

    public void guardarArchivo() {
        UsuarioDB.actualizarArchivo(GestorArchivos.codificarContenido(textAreaArchivo.getText()));
        try {
            HelloApplication.mostrarVentana("MenuArchivos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelar() {
        try {
            GestorArchivos.setNombreArchivoSeleccionado(null);
            HelloApplication.mostrarVentana("MenuArchivos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarArchivo();
    }

}
