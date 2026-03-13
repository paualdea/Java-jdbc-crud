package ut2.act1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase sirve para crear o comprobar la existencia de la base de datos de la aplicación.

 * Se usará una BD SQLite debido a que es ligera, no requiere un servicio, ideal para
 * pruebas o proyectos a pequeña escala.
 */
public class Bd {
    final String url = "jbdc:sqlite:empresaDB.db";

    public Bd() {
        // Creamos la sentencia para crear la tabla de CLIENTES si no existe, especificando todos los campos y sus parametros
        String sentencia = "CREATE TABLE IF NOT EXISTS CLIENTES (id_cliente INT PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(100),ciudad VARCHAR(50));";

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
}
