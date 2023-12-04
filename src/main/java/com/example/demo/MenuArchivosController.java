package com.example.demo;

import com.example.demo.Archivo;
import com.example.demo.GestorArchivos;
import com.example.demo.HelloApplication;
import com.example.demo.UsuarioDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuArchivosController implements Initializable {

    @FXML
    private Button botonAgregar;

    @FXML
    private TableView<Archivo> tablaArchivos;

    @FXML
    private TableColumn<Archivo, String> nombreArchivo;

    @FXML
    private TableColumn<Archivo, String> fechaCreacion;

    @FXML
    private Button botonEliminar;

    @FXML
    private Button botonCerrarSesion;

    @FXML
    private Button botonAbrir;

    ObservableList<Archivo> lista = FXCollections.<Archivo>observableArrayList();

    public void cargarTabla() {
        lista = FXCollections.<Archivo>observableArrayList(Objects.requireNonNull(UsuarioDB.getArchivos()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        cargarTabla();
        nombreArchivo.setCellValueFactory(new PropertyValueFactory<Archivo, String>("nombreArchivo"));
        fechaCreacion.setCellValueFactory(new PropertyValueFactory<Archivo, String>("fechaCreacion"));

        tablaArchivos.setItems(lista);
    }

    @FXML
    public void crearArchivo() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo Archivo");
        dialog.setHeaderText(null);
        dialog.setContentText("Indica el nombre del archivo:");

        dialog.showAndWait().ifPresent(nombreArchivo -> {
            Archivo archivoNuevo = GestorArchivos.crearArchivo(nombreArchivo + ".txt");
            tablaArchivos.getItems().add(archivoNuevo);
        });
    }

    @FXML
    public void borrarArchivo() {
        Archivo archivo = tablaArchivos.getSelectionModel().getSelectedItem();
        if(archivo != null) {
            GestorArchivos.borrarArchivo(archivo.getNombreArchivo());
            tablaArchivos.getItems().remove(archivo);
        }
    }

    @FXML
    public void cerrarSesion() throws IOException {
        GestorArchivos.borraArchivoUsuario();
        HelloApplication.mostrarVentana("hello-view");
    }

    @FXML
    public void abrirArchivo() {

        if (tablaArchivos.getSelectionModel().getSelectedItem()!=null) {
            GestorArchivos.setNombreArchivoSeleccionado(tablaArchivos.getSelectionModel().getSelectedItem().nombreArchivo);
            try {
                HelloApplication.mostrarVentana("LeerModificarArchivo");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
