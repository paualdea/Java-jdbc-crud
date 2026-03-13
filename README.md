# Acceso a Datos: CRUD con JDBC y PreparedStatement

Este proyecto es una aplicación **Java** desarrollada como parte del **Enunciado 1: "Aplicación CRUD básica con PreparedStatement"** de la Unidad de Trabajo 2 (UT2) del módulo **Acceso a Datos**.

La aplicación permite la gestión de clientes en una base de datos relacional SQLite (`empresaDB.db`), con un menú que permite realizar operaciones de inserción, consulta, actualización y borrado. 
El objetivo de esta aplicación es el de aprender a implementar sistemas de datos persistentes mediante **JDBC**.

## Características Principales

* **Operaciones CRUD**: Implementación completa de las funciones *Create, Read, Update y Delete* sobre la tabla de clientes.
* **Uso de `PreparedStatement`**: Uso de la clase `PreparedStatement` para evitar ataques de inyección SQL y mejorar el rendimiento.
* **Optimización de Recursos**: Implementación de la estructura `try-with-resources` para asegurar el cierre automático de conexiones y no sobrecargar al sistema.
* **Control de Errores**: Control de excepciones usando `SQLException` para reportar fallos en el acceso a la BD.

## Estructura de la Base de Datos

El proyecto usa la BD `empresaDB.db` con la siguiente tabla:

### Tabla: `CLIENTES`
| Columna | Tipo de dato | Descripción |
| :--- | :--- | :--- |
| `id_cliente` | `INT` | Clave primaria identificadora. |
| `nombre` | `VARCHAR(100)` | Nombre completo del cliente. |
| `ciudad` | `VARCHAR(50)` | Ciudad de residencia. |

## Estructura del Proyecto

* **`Main.java`**: Controla el menú y gestiona las llamadas a la clase `Bd.java`.
* **`Bd.java`**: Controla en su totalidad la BD, usando el **JDBC** para realizar las operaciones.

## Ejecución

Para ejecutar este programa, es necesario descargar el código fuente o el `.jar` de las _releases_.

---
Este proyecto sirve como evidencia del aprendizaje sobre la conexión y gestión de bases de datos relacionales desde Java para la asignatura **Acceso a Datos**.
