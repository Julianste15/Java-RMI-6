# Sistema de Preferencias de Usuarios - Java RMI

Este proyecto es una implementación de un sistema distribuido utilizando **Java RMI** para la consulta de preferencias de usuarios (artistas y géneros más escuchados). El sistema actúa como un middleware que consolida información proveniente de otros microservicios (Canciones y Reproducciones) para entregar un perfil de preferencias a los clientes.

## Estructura del Proyecto

El proyecto está dividido en dos módulos principales:

- **`servidor_java_rmi`**: Contiene la lógica del servidor, el objeto remoto y la integración con servicios externos mediante clientes REST (Feign).
- **`cliente_java_rmi`**: Interfaz de consola que permite a los usuarios interactuar con el servidor RMI para realizar consultas.

## Requisitos

- **Java JDK 17** o superior.
- **Apache Maven**.
- Servicios externos de Canciones y Reproducciones en ejecución (opcional para el arranque, pero requerido para datos reales).

## Configuración

Ambos módulos cuentan con un archivo `application.properties` en `src/main/resources` para configurar los parámetros de red:

- `ns.host`: Dirección IP del Name Server (Registry).
- `ns.port`: Puerto del Name Server (por defecto `2020`).
- `servidor.canciones.url`: URL del microservicio de canciones.
- `servidor.reproducciones.url`: URL del microservicio de reproducciones.

## Ejecución

### 1. Compilar el proyecto
Desde la raíz del proyecto, compila ambos módulos:

```bash
cd servidor_java_rmi
mvn clean compile
cd ../cliente_java_rmi
mvn clean compile
```

### 2. Iniciar el Servidor
Navega a la carpeta del servidor y ejecuta:

```bash
mvn exec:java "-Dexec.mainClass=co.edu.unicauca.main.Main"
```
*El servidor iniciará automáticamente el registro RMI en el puerto configurado.*

### 3. Iniciar el Cliente
En una nueva terminal, navega a la carpeta del cliente y ejecuta:

```bash
mvn exec:java "-Dexec.mainClass=co.edu.unicauca.main.Main"
```

## Funcionalidad Principal

El sistema permite consultar las preferencias de un usuario por su ID. El proceso interno es:
1. El cliente solicita las preferencias al objeto remoto `objControladorPreferenciasUsuarios`.
2. El servidor RMI recibe la petición y utiliza clientes Feign para obtener:
   - La lista de canciones desde el servidor de canciones.
   - El historial de reproducciones del usuario desde el servidor de reproducciones.
3. Un componente calculador analiza los datos y determina el artista y género preferido.
4. Se retorna un objeto `PreferenciasDTORespuesta` al cliente con la información procesada.

## Tecnologías Utilizadas

- **Java RMI**: Invocación de métodos remotos.
- **Maven**: Gestión de dependencias y construcción.
- **OpenFeign**: Comunicación declarativa con servicios REST.
- **Jackson**: Procesamiento de datos JSON.
- **Lombok**: Reducción de código boilerplate en DTOs.
