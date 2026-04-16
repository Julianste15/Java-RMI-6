# Sistema de Preferencias de Usuarios - Java RMI

Este proyecto es una implementación de un sistema distribuido utilizando **Java RMI** para la consulta de preferencias de usuarios (artistas y géneros más escuchados). El sistema actúa como un middleware que consolida información proveniente de otros microservicios (Canciones y Reproducciones) para entregar un perfil de preferencias a los clientes y notificar a los administradores mediante **Callbacks**.

## Estructura del Proyecto

El proyecto está dividido en tres módulos principales:

- **`servidor_java_rmi`**: Contiene la lógica del servidor, el objeto remoto y la integración con servicios externos mediante clientes REST (Feign). Gestiona el registro de administradores para notificaciones automáticas.
- **`cliente_java_rmi`**: Interfaz de consola que permite a los usuarios interactuar con el servidor RMI para realizar consultas de preferencias.
- **`administrador_java_rmi`**: Módulo que se registra en el servidor para recibir notificaciones en tiempo real cada vez que un usuario consulta sus preferencias.

## Requisitos

- **Java JDK 17** o superior.
- **Apache Maven**.
- Servicios externos de Canciones y Reproducciones (o un simulador como JsonServer) en ejecución.

## Configuración

Todos los módulos cuentan con un archivo `application.properties` en `src/main/resources` para configurar los parámetros de red:

- `ns.host`: Dirección IP del Name Server (Registry).
- `ns.port`: Puerto del Name Server (por defecto `2020`).
- `servidor.canciones.url`: URL del microservicio de canciones.
- `servidor.reproducciones.url`: URL del microservicio de reproducciones.

## Ejecución

### 1. Compilar el proyecto
Desde la raíz del proyecto, compila los tres módulos:

```bash
mvn clean compile -f servidor_java_rmi/pom.xml
mvn clean compile -f cliente_java_rmi/pom.xml
mvn clean compile -f administrador_java_rmi/pom.xml
```

### 2. Iniciar el Servidor
Navega a la carpeta del servidor y ejecuta:

```bash
cd servidor_java_rmi
mvn exec:java "-Dexec.mainClass=co.edu.unicauca.main.Main"
```
*El servidor iniciará automáticamente el registro RMI en el puerto configurado.*

### 3. Iniciar el Administrador
En una nueva terminal, navega a la carpeta del administrador y ejecuta:

```bash
cd administrador_java_rmi
mvn exec:java "-Dexec.mainClass=co.edu.unicauca.main.Main"
```
*El administrador se registrará en el servidor y quedará a la espera de notificaciones.*

### 4. Iniciar el Cliente
En otra terminal, navega a la carpeta del cliente y ejecuta:

```bash
cd cliente_java_rmi
mvn exec:java "-Dexec.mainClass=co.edu.unicauca.main.Main"
```

## Funcionalidad Principal

El sistema permite consultar las preferencias de un usuario y notificar a los administradores registrados. El proceso es:
1. El **Administrador** se registra en el servidor proporcionando su referencia remota (`CallBack`).
2. El **Cliente** solicita las preferencias al objeto remoto `objControladorPreferenciasUsuarios`.
3. El **Servidor RMI** obtiene los datos de Canciones y Reproducciones mediante Feign.
4. El calculador procesa la información y genera el perfil de preferencias.
5. El servidor envía las preferencias al cliente como respuesta.
6. Simultáneamente, el servidor invoca el método `notificar()` en todos los administradores registrados, enviándoles el perfil procesado.

## Tecnologías Utilizadas

- **Java RMI**: Invocación de métodos remotos y mecanismos de Callbacks.
- **Maven**: Gestión de dependencias y construcción.
- **OpenFeign**: Comunicación declarativa con servicios REST.
- **Jackson**: Procesamiento de datos JSON.
- **Lombok**: Reducción de código boilerplate en DTOs.
