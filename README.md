# Reserva Butacas

Este proyecto de pruebas es una aplicación desarrollada en **Java** utilizando **Spring Boot** y **PostgreSQL** como base de datos. Permite gestionar reservas de butacas en salas de cine.

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes componentes:

- **Java 17** o superior
- **Maven 3.8** o superior
- **PostgreSQL** (versión 12 o superior)
- **Git** (opcional, para clonar el repositorio)

## Configuración de la base de datos

1. Crea una base de datos en PostgreSQL con el nombre `reserva_butacas`:
   ```sql
   CREATE DATABASE reserva_butacas;
   ```
2. Configura las credenciales de la base de datos en el archivo `src/main/resources/application.yml`:
   ```yaml
    spring:
      datasource:
      url: jdbc:postgresql://localhost:5432/reserva_butacas
      username: usuario
      password: clave
   ``` 
   
## Ejecutar la aplicación
Esto puede hacerse de dos maneras:
1. **Usando Maven**:
   - Abre una terminal y navega hasta la carpeta del proyecto.
   - Ejecuta el siguiente comando:
     ```bash
     ./mvnw[.cmd] spring-boot:run
     ```
2. **Usando el IDE de preferencia**:
   - Abre el proyecto en tu IDE favorito (Eclipse, IntelliJ, etc.).
   - Ejecuta la clase principal `ReservaButacasApplication.java` como una aplicación Java.

Esto ejecutará las migraciones de la base de datos (tener en cuenta la version del archivo de migración en caso de problemas`src/main/resources/db/migration/V2__insert_testing_data.sql`) y levantará el servidor en el puerto `8080` por defecto. Puedes acceder a la aplicación a través de `http://localhost:8080`.

## Ejecutar pruebas
Para ejecutar las pruebas unitarias y de integración, puedes usar el 1 de los siguientes comandos Maven:
```bash
# para ejecutar una clase de prueba específica
./mvnw[.cmd] test -Dtest=NombreClaseDePrueba
# para ejecutar todas las pruebas
./mvnw[.cmd] test

```
O utilizando el IDE de preferencia