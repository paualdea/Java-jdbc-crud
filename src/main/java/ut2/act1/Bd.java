package ut2.act1;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

/**
 * Esta clase sirve para crear o comprobar la existencia de la base de datos de la aplicación.

 * Se usará una BD SQLite debido a que es ligera, no requiere un servicio, ideal para
 * pruebas o proyectos a pequeña escala.
 */
public class Bd {
    final String url = "jdbc:sqlite:empresaDB.db";

    /**
     * Función constructora de la clase.

     * Sirve para crear - identificar la base de datos de la aplicación.
     */
    public Bd() {
        // Creamos la sentencia para crear la tabla de CLIENTES si no existe, especificando todos los campos y sus parametros
        String sentencia = "CREATE TABLE IF NOT EXISTS CLIENTES (id_cliente INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(100),ciudad VARCHAR(50));";

        // Implementamos un try-with-resources para no desperdiciar recursos
        try (Connection c = DriverManager.getConnection(url);
             Statement st = c.createStatement())
            {
                // Ejecutamos la creación de la tabla siguiendo la sentencia
                st.execute(sentencia);
            }
        // En caso de que falle la conexión a la BD, controlamos la excepción (SQLException)
        catch (SQLException e) {
            // Mandamos el error usando System.err para no ensuciar la salida
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    /**
     * Función que hace una inserción en la BD usando PreparedStatement

     * @param nombre
     * Recibe cómo parametro un String que determina el nombre del nuevo cliente
     * @param ciudad
     * Recibe cómo parametro un String que determina la ciudad del nuevo cliente
     */
    public void crearCliente (String nombre, String ciudad) {
        int resultado = 0;

        // Creamos la sentencia permitiendo bindear las variables sin concatenar (siguiendo la actividad)
        String sentencia = "INSERT INTO CLIENTES (nombre, ciudad) VALUES (?,?);";

        // Usamos un try-with-resources para no acumular recursos
        try (Connection c = DriverManager.getConnection(url);
            PreparedStatement ps = c.prepareStatement(sentencia))
        {
            ps.setString(1, nombre);
            ps.setString(2, ciudad);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }

        // Devolvemos un mensaje para que el usuario vea lo que ha ocurrido, incluido el numero de filas afectadas
        System.out.println("\nCliente " + nombre + " añadido.\n" + resultado + " filas afectadas.");
    }

    /**
     * Función para ejecutar un SELECT * sobre la tabla CLIENTES
     */
    public void listaUsuarios() {
        Main.limpiarPantalla();
        String sentencia = "SELECT * FROM CLIENTES;";
        int numeroUsuarios = 0;

        // Estructura try-with-resources
        try (Connection c = DriverManager.getConnection(url);
            PreparedStatement ps = c.prepareStatement(sentencia);
            ResultSet rs = ps.executeQuery())
        {
            System.out.println("\t\t.:LISTADO CLIENTES:.\n");

            // Bucle while para recorrer el ResultSet
            while (rs.next()) {
                System.out.println(rs.getInt(1) + ". " + rs.getString(2) + " de " + rs.getString(3));
                numeroUsuarios++;
            }
            System.out.println("\n" + numeroUsuarios + " usuarios");
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    /**
     * Esta función sirve para devolver un entero del número de clientes que tiene la tabla

     * @return numeroUsuarios
     * Devuelve cómo entero el número de usuarios de la tabla CLIENTES
     */
    public int numeroClientes () {
        int numeroUsuarios = 0;
        String sentencia = "SELECT COUNT(*) FROM CLIENTES";

        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sentencia);
             ResultSet rs = ps.executeQuery())
        {
            // Movemos el primer cursor y obtenemos el número de usuarios
            rs.next();
            numeroUsuarios = rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }

        return numeroUsuarios;
    }

    /**
     * Función para actualizar ciudad de un usuario en base a su ID
     */
    public void actualizarCiudad () {
        Scanner sc = new Scanner(System.in);
        int id = 0, n = numeroClientes();

        // Si el numero de usuarios es menor a 1, no hacemos nada
        if (n < 1) {
            System.out.println("\nNo hay usuarios en la tabla");
            Main.espera(2500);
        } else {
            try {
                System.out.print("ID usuario a actualizar: ");
                id = sc.nextInt();
            } catch (Exception e) {
                System.out.println("\nOpción incorrecta");
                Main.espera(0);
            }

            // En caso de que el ID recibido sea correcto, actualizamos la ciudad del usuario
            if (id > 0 && id <= n) {
                System.out.print("Nueva ciudad: ");
                String ciudad = sc.nextLine();

                // Actualizamos la sentencia
                String sentencia = "UPDATE CLIENTES SET ciudad = ? WHERE id_cliente = ?;";

                // Estructura try-with-resources
                try (Connection c = DriverManager.getConnection(url);
                     PreparedStatement ps = c.prepareStatement(sentencia)) {
                    // Bindeamos la nueva ciudad y el ID a la sentencia SQL
                    ps.setString(1, ciudad);
                    ps.setInt(2, id);

                    // Ejecutamos la sentencia
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error SQL: " + e.getMessage());
                }
            }
            // En caso de que el ID este fuera de rango, mostramos un mensaje de error
            else {
                System.out.println("\nEl ID de usuario es incorrecto");
                Main.espera(0);
            }
        }
    }

    /**
     * Función para eliminar un cliente en base a su ID
     */
    public void eliminarCliente () {
        int n = numeroClientes();

        // Si el numero de usuarios es menor a 1, no hacemos nada
        if (n < 1) {
            System.out.println("\nNo hay usuarios en la tabla");
            Main.espera(2500);
        } else {
            Scanner sc = new Scanner(System.in);
            int id = 0;

            try {
                System.out.print("ID usuario a eliminar: ");
                id = sc.nextInt();
            } catch (Exception e) {
                System.out.println("\nOpción incorrecta");
                Main.espera(0);
            }

            // En caso de que el ID recibido sea correcto, actualizamos la ciudad del usuario
            if (id > 0 && id <= n) {
                String sentencia = "DELETE FROM CLIENTES WHERE id_cliente = ?;";

                // Estructura try-with-resources
                try (Connection c = DriverManager.getConnection(url);
                     PreparedStatement ps = c.prepareStatement(sentencia)) {
                    // Bindeamos el ID a la sentencia SQL
                    ps.setInt(1, id);

                    // Ejecutamos la sentencia
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.err.println("Error SQL: " + e.getMessage());
                }
            } else {
                System.out.println("\nEl ID de usuario es incorrecto");
                Main.espera(0);
            }
        }
    }
}