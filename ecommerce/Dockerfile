# Usa una imagen base de Java
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de tu aplicación Spring Boot al contenedor
COPY ecommerce-0.0.1-SNAPSHOT.jar /app

# Expone el puerto 9090 
EXPOSE 9090

# Comando para ejecutar la aplicación Spring Boot cuando se inicie el contenedor
CMD ["java", "-jar", "ecommerce-0.0.1-SNAPSHOT.jar"]

