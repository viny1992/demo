package com.example.demo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static com.example.demo.GestionArchivos.descodificarArchivo;
import static com.example.demo.GestorArchivos.borraArchivoUsuario;
import static com.example.demo.GestorArchivos.codificarArchivo;
import static com.example.demo.UsuarioDB.*;

public  class IniciarSesion {
    public static void opcionesInicio(){

        int selec;
        do {
            System.out.println("1- Iniciar Sesion");
            System.out.println("2- Registrarse");
            System.out.println("3- salir");
            Scanner sc= new Scanner(System.in);
            selec= sc.nextInt();
            switch (selec) {
                case 1:
                    //if (inicioSesion()) {//si inicioSesion es true, es decir, existe ese usuario hace que te registres
                    //    return;
                    //}
                case 2:
                    registro();
                    return;
                case 3:
                    close();
                    selec=4;
                    break;

                default:
                    System.out.println("Opción no valida");
                    break;

            }
        }while (selec!=4);


    }


    /**
     * muestra las posibles opciones y llama a los metodos
     */
    public static void opciones(){
        int selec;
        do {
            Scanner sc= new Scanner(System.in);
            System.out.println("1- Darse de baja");
            System.out.println("2- Mostrar datos");
            System.out.println("3- Crear Archivo");
            System.out.println("4- Ver Archivos");
            System.out.println("5- Leer Archivos");
            System.out.println("6- Modificar Archivos");
            System.out.println("7- salir");
            System.out.println("8- Salir y cerrar sisión");
            selec= sc.nextInt();
            switch (selec){
                case 1:
                    darseBaja();
                    return;
                case 2:
                    mostrarDatos();
                    break;

                case 3:
                    crearArchivo("ojete");
                    break;

                case 4:
                    verArchivosGuardados();
                    break;

                case 5:
                    leerUnArchivo();
                    break;

                case 6:
                    modificarArchivo();
                    break;


                case 7:
                    close();
                    selec=9;
                    break;


                default:
                    System.out.println("Opción no valida");
                    break;

            }

        }while (selec!=9);

    }
    private static void leerUnArchivo() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Indique el nombre del archivo: ");
        String nombreArchivo=sc.nextLine();
        recuperarArchivo(nombreArchivo);
    }

    private static void verArchivosGuardados() {
        System.out.println("Esto son sus archivos");
        System.out.println(Objects.requireNonNull(getArchivos()));



    }

    /**
     * hace el inicio de sesión
     */

    public static boolean inicioSesion(String nombreUser, String password) {
        connection();

        return comprobarUsuario(nombreUser, password);
    }

    /**
     * Registrar usuario
     */
    public static void registro(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Al usted no tener un cuenta con nosotros, deberá crear una");
        System.out.println("Necesitaré algunos datos");
        System.out.println("Nombre usuario");
        String nombreUsuario= sc.nextLine().trim();
        System.out.println("Su contraseña");
        String contrasena= sc.nextLine();
        System.out.println("Su nombre");
        String nombre= sc.nextLine().trim();
        System.out.println("Su apellido");
        String apellidos= sc.nextLine().trim();
        System.out.println("Su email");
        String email= sc.nextLine().trim();
        ingresarUsuario(nombreUsuario,contrasena,nombre,apellidos,email);
        System.out.println("cuenta creado correctamente");

    }

    private static void crearArchivo(String nombreArchivo) {
        Scanner sc=new Scanner(System.in);
        String rutaCarpeta="Archivos";
        File carpeta=new File(rutaCarpeta);
        if (!carpeta.exists()){
            carpeta.mkdirs();
        }
        File archivo=new File(carpeta,nombreArchivo);

        try(FileWriter fileWriter = new FileWriter(archivo)) {
            fileWriter.write(" ");
            if (UsuarioDB.guardarEnBDD(codificarArchivo(carpeta,nombreArchivo), nombreArchivo)){
                System.out.println("Archivo creado seculentamente, que barbaridad");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Elimina un usuario
     */
    public static void darseBaja(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Para darse de baja debe faciliatar el identificador(ID)");
        System.out.println("En el caso de que lo desconozca, escriba 'Datos'");
        String selec=sc.nextLine();
        if (selec.equals("Datos")){
            mostrarDatos();
            System.out.println("Ahora, escriba su ID");
            String ID= sc.nextLine().trim();
            eliminarUsuario(ID);
            borraArchivoUsuario();

        }else{
            eliminarUsuario(selec);
            borraArchivoUsuario();
        }


    }

    /**
     * muestra los datos del usuario
     */
    public static void mostrarDatos(){//cambiar
        if (UsuarioDB.getUsuarioApp() != null) {
            System.out.println((getDatos()));
        } else {
            Scanner sc= new Scanner(System.in);
            System.out.println("Necesitamos su nombre de usuario");
            String codUsuario= sc.nextLine();
            System.out.println("Su contraseña");
            String nombreUsuario= sc.nextLine();
            System.out.println((getDatos(codUsuario,nombreUsuario)));
        }

    }
    public static void modificarArchivo(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Indique el archivo que quiere modificar");
        String nombreArchivo=sc.nextLine().trim();
        descodificarArchivo(nombreArchivo);
    }
}
