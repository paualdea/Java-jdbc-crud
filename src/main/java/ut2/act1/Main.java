package ut2.act1;

import java.util.Scanner;

public class Main {
    final static int TIEMPO_ESPERA = 1250;

    public static void main(String[] args) {
        int opcion = 0;
        boolean salir = false;
        Scanner sc = new Scanner(System.in);
        // Creamos-importamos la BD
        Bd bd = new Bd();

        // Creamos un menú para poder interactuar con la DB
        while (!salir) {
            // Limpiamos la pantalla y mostramos el menú
            limpiarPantalla();
            System.out.print("\t\t.:GESTIÓN CLIENTES BD:.\n1. Añadir cliente\n2. Consultar clientes\n3. Actualizar ciudad\n4. Eliminar cliente\n5. Salir\n\nOpción: ");

            // Creamos una estructura de control try-catch-finally para comprobar que la entrada sea correcta
            try {
                opcion = sc.nextInt();
            } catch (Exception e) {
                System.err.println("Sólo se permiten opciones 1-5");
                sc.nextLine();
            }

            // Creamos un switch para evaluar todas las opciones del menú
            switch(opcion) {
                case 1:
                    limpiarPantalla();

                    // Pedimos los datos del nuevo cliente
                    sc.nextLine();
                    System.out.print("\t\t.:CREAR CLIENTE:.\nNombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ciudad: ");
                    String ciudad = sc.nextLine();

                    // Ejecutamos la función de creación de cliente (que comunica con la BD)
                    bd.crearCliente(nombre, ciudad);
                    espera(0);
                    break;
                case 2:
                    // Llamamos a la función que lista todos los usuarios
                    bd.listaUsuarios();
                    espera(3750);
                    break;
                case 3:
                    // Reutilizamos la función para listar los usuarios
                    bd.listaUsuarios();

                    // Lanzamos la función de actualización de ciudad
                    bd.actualizarCiudad();
                    break;
                case 4:
                    // Reutilizamos la función para listar los usuarios
                    bd.listaUsuarios();

                    // Ejecutamos la función de eliminación de cliente
                    bd.eliminarCliente();
                    break;
                case 5:
                    // Salimos del bucle while
                    salir = true;
                    break;
                default:
                    System.err.println("Opción no permitida");
                    break;
            }
        }
    }

    /**
     * Esta función limpia la pantalla dependiendo del sistema operativo que tengas
     */
    public static void limpiarPantalla() {
        try {
            // Obtenemos el sistema operativo desde el que se ejecuta el programa
            String so = System.getProperty("os.name").toLowerCase();

            // Si es windows lanzamos el comando cls para borrar la pantalla
            if (so.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            // Si es Linux o Mac, lanzamos una secuencia de caracteres ANSI que limpia y borra la pantalla
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.err.println("Error al limpiar la pantalla.\n" + e.getMessage());
        }
    }

    /**
     * Esta función ejecuta un bloque de código que para la ejecución de espera TIEMPO_ESPERA segundos
     */
    public static void espera(int tiempo) {
        if (tiempo > 0) {
            try {
                Thread.sleep(tiempo);

            } catch (InterruptedException e) {
                System.err.println("No se ha podido hacer la pausa de " + tiempo);
            }
        } else {
            try {
                Thread.sleep(TIEMPO_ESPERA);

            } catch (InterruptedException e) {
                System.err.println("No se ha podido hacer la pausa de " + TIEMPO_ESPERA);
            }
        }
    }
}
