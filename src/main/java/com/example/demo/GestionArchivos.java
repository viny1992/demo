package com.example.demo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Base64;
import static com.example.demo.UsuarioDB.*;

public class GestionArchivos {
    private static File carpetaConfig;

    public File getCarpetaConfig() {
        return carpetaConfig;
    }

    public static void setCarpetaConfig() {
        carpetaConfig = new File(Constantes.RUTA_CARPETA_CONFIG);
        if (!carpetaConfig.exists()) {
            carpetaConfig.mkdirs();
            carpetaConfig.delete();
        }
    }

    public static boolean guardarUsuario() {
        File archivoUsuario = new File(carpetaConfig, Constantes.NOMBRE_ARCHIVO_USUARIO);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoUsuario.getPath()))) {
            oos.writeObject(UsuarioDB.getUsuarioApp());
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    public static boolean convertirConfEnUsuario() {
        File archivoUsuario = new File(carpetaConfig, Constantes.NOMBRE_ARCHIVO_USUARIO);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoUsuario.getPath()))) {
            UsuarioDB.setUsuarioApp((Usuario) ois.readObject());
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public static String codificarArchivo() throws IOException {

        String ruta = "C:/Users/verti/Downloads/MusicaSinInterfaz/hola.txt";
        File f = new File(ruta);
        //System.out.println(new String(Files.readAllBytes(Paths.get(ruta))));
        return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(ruta)));
    }

    public static boolean guardarEnBDD(String archivoBLOB, String nombreArchivo) throws SQLException {
        connection();
        String sql = "INSERT INTO ARCHIVOS (NOMBREUSUARIO, NOMBREARCHIVO, CONTENIDO) VALUES (?,?,?)";
        try(PreparedStatement stm = UsuarioDB.connection.prepareStatement(sql)) {
            stm.setString(1, UsuarioDB.getUsuarioApp().getNombreUsuario());
            stm.setString(2, nombreArchivo);
            stm.setBytes(3, archivoBLOB.getBytes());
            stm.executeUpdate();
        }

        return true;
    }

    public static String recuperarArchivo() {
        Connection connection = null;
        try {
            connection = DriverManager. getConnection("jdbc:mysql://localhost:3306/gestionArchivos", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "SELECT CONTENIDO FROM ARCHIVOS WHERE nombreArchivo = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, "hola.txt");
            try (ResultSet rs = stm.executeQuery()){
                if (rs.next()) {
                    byte[] contenidoBytes = rs.getBytes("CONTENIDO");
                    return new String(contenidoBytes);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
        return "";
    }

    public static String descodificarArchivo(String archivoCodificado) {
        return new String(Base64.getDecoder().decode(archivoCodificado.getBytes()));
    }
}
