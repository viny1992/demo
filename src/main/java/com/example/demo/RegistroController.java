package com.example.demo;

import com.example.demo.HelloApplication;
import com.example.demo.UsuarioDB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistroController {

    @FXML
    private TextField apellidoText;

    @FXML
    private Button crearButton;

    @FXML
    private Label labelError;

    @FXML
    private TextField emailText;

    @FXML
    private TextField nombreText;

    @FXML
    private TextField campoContraseña;

    @FXML
    private TextField usuarioText;

    @FXML
    public void registrarUsuario() {
        if (usuarioText.getText().isEmpty() || nombreText.getText().isEmpty() ||campoContraseña.getText().isEmpty()
        || emailText.getText().isEmpty() || apellidoText.getText().isEmpty()) {
            labelError.setVisible(true);
        } else {
            if (UsuarioDB.ingresarUsuario(usuarioText.getText(), campoContraseña.getText(), nombreText.getText(), apellidoText.getText(), emailText.getText())){
                try {
                    HelloApplication.mostrarVentana("MenuArchivos");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    @FXML
    public void inicioSesion() throws IOException {
        HelloApplication.mostrarVentana("hello-view");
    }

}
