package com.example.demo;

import com.example.demo.HelloApplication;
import com.example.demo.IniciarSesion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.demo.UsuarioDB.connection;

public class HelloController {

    @FXML
    private TextField campoNombreUsuario;
    @FXML
    private TextField campoPassword;

    @FXML
    private Label labelError;

    @FXML
    public void inicioSesion() throws IOException {
        if(IniciarSesion.inicioSesion(campoNombreUsuario.getText(), campoPassword.getText())) {
            HelloApplication.mostrarVentana("MenuArchivos");
        } else {
            labelError.setVisible(true);
        }
    }

    public void registro() throws IOException {
        HelloApplication.mostrarVentana("registro");
    }
}