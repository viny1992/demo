package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.demo.UsuarioDB.connection;

public class HelloApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        connection();
        FXMLLoader fxmlLoader;
        if (GestorArchivos.convertirConfEnUsuario()) {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MenuArchivos.fxml"));
            System.out.println("Hola, " + UsuarioDB.getUsuarioApp().getNombreUsuario());
        } else {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        }
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bienvenid@!");
        stage.setScene(scene);
        stage.show();
    }

    public static void mostrarVentana(String fxml) throws IOException {
        scene.setRoot(cargarFXML(fxml));
    }

    private static Parent cargarFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}