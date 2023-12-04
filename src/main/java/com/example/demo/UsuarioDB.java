package com.example.demo;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDB {
    public static Connection connection;

    private static Usuario usuarioApp;


    /**
     * Se conecta a la base de datos
     */
    public static boolean connection(){

        try {
            connection = DriverManager.getConnection(Constantes.RUTA_BBDD, Constantes.USUARIO_BBDD, Constantes.CONTRASEÑA_BBDD);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Comprueba si el usuario existe, en tal caso true
     */
    public static boolean comprobarUsuario(String usuario, String contrasena) {
        String sentenciaSQL = "SELECT * FROM usuarios WHERE nombreUsuario=? AND contrasena=?";

        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                ResultSet resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    usuarioApp = new Usuario(resultado.getInt("id"), resultado.getString("nombreUsuario"),
                            resultado.getString("nombre"), resultado.getString("apellidos"),
                            resultado.getString("email"));
                    GestorArchivos.guardarUsuario();
                    return true;
                } else {
                    System.out.println(resultado.toString());
                }
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }


    /**
     * crea un usuario
     */

    public static boolean ingresarUsuario(String nombreUsuario, String contrasena, String nombre, String apellidos, String email) {
        String sql = "insert into usuarios(nombreUsuario, contrasena, nombre, apellidos, email) values (?,?,?,?,?)";
        try {
            PreparedStatement sentencia = connection.prepareStatement(sql);
            sentencia.setString(1, nombreUsuario);
            sentencia.setString(2, contrasena);
            sentencia.setString(3, nombre);
            sentencia.setString(4, apellidos);
            sentencia.setString(5, email);


            sentencia.execute();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }


    /**
     * Pilla los datos del usuario
     */
    public static String getDatos(String nombreUsuario, String contrasena) {
        String sentenciaSQL = "SELECT * FROM  usuarios WHERE nombreUsuario=? AND contrasena=?";

        String datos = "";
        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setString(1, nombreUsuario);
                sentencia.setString(2, contrasena);

                ResultSet resultado = sentencia.executeQuery();
                if (resultado.next()) {
                    datos += resultado.getString("nombreUsuario") + "\n";
                    datos += resultado.getString("id") + "\n";
                    datos += resultado.getString("nombre") + "\n";
                    datos += resultado.getString("apellidos") + "\n";
                    datos += resultado.getString("email") + "\n";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return datos;
    }

    public static List<Archivo> getArchivos() {
        String sentenciaSQL = "SELECT * FROM  archivos WHERE nombreUsuario=?";
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        List<Archivo> datos = new ArrayList<>();
        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setString(1, usuarioApp.getNombreUsuario());

                ResultSet resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    Archivo archivo = new Archivo(resultado.getString("nombreArchivo"),
                            formatoFecha.format(resultado.getDate("fechaCreacion")));
                    datos.add(archivo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return datos;
    }
    /**
     * elimina al estudiante
     */
    public static boolean eliminarUsuario(String id){
        String sentenciaSQL = "delete from usuarios where id=?";
        if (connection != null) {
            try {
                PreparedStatement sentencia = connection.prepareStatement(sentenciaSQL);
                sentencia.setInt(1, Integer.parseInt(id));
                int resultado = sentencia.executeUpdate();
                if (resultado>0) {
                    return true;
                }
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Devuelve los datos del usuario guardados en conf
     * @return datos usuario
     */
    public static String getDatos() {
        return usuarioApp.toString();
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

    public static void actualizarArchivo(String contenidoCodificado) {
        connection();
        String sql = "UPDATE ARCHIVOS SET CONTENIDO = ? WHERE NOMBREARCHIVO = ?";
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setBytes(1, contenidoCodificado.getBytes());
            stm.setString(2, GestorArchivos.getNombreArchivoSeleccionado());

            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String recuperarArchivo(String nombreArchivo) {
        connection();
        String sql = "SELECT CONTENIDO FROM ARCHIVOS WHERE nombreArchivo = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, nombreArchivo);
            try (ResultSet rs = stm.executeQuery()){
                if (rs.next()) {
                    byte[] contenidoBytes = rs.getBytes("CONTENIDO");
                    return new String(contenidoBytes);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static void setUsuarioApp(Usuario usuarioApp) {
        UsuarioDB.usuarioApp = usuarioApp;
    }

    public static Usuario getUsuarioApp() {
        return usuarioApp;
    }


    /**
     * Termina la conexion
     */
    public static boolean close(){
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean borrarArchivo(String nombreArchivo) {
        connection();
        String sql = "DELETE FROM ARCHIVOS WHERE NOMBREUSUARIO = ? AND NOMBREARCHIVO = ?";
        try(PreparedStatement stm = UsuarioDB.connection.prepareStatement(sql)) {
            stm.setString(1, UsuarioDB.getUsuarioApp().getNombreUsuario());
            stm.setString(2, nombreArchivo);
            stm.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }
}
