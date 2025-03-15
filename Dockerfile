# Usar la imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Crear el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado en el build de Spring Boot
COPY target/*.jar app.jar

# Exponer el puerto donde corre la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
