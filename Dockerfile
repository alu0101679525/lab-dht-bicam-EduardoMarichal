# ==============================================================================
# Dockerfile multietapa para la aplicacion BiCIAM (Practica 5 - LDH - ULL)
# ==============================================================================

# ------------------------------------------------------------------------------
# Etapa 1: Builder (Compilacion y empaquetado con Maven y JDK 17)
# ------------------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiar descriptor de dependencias Maven
COPY pom.xml .

# Descargar dependencias en capa cacheada
RUN mvn dependency:go-offline -B || true

# Copiar codigo fuente del proyecto
COPY src ./src

# Compilar y empaquetar el JAR ejecutable
RUN mvn clean package -DskipTests

# ------------------------------------------------------------------------------
# Etapa 2: Imagen final de ejecucion ligera (JRE 17)
# ------------------------------------------------------------------------------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Metadatos de la imagen
LABEL maintainer="Laboratorio de Desarrollo y Herramientas - ULL"
LABEL description="Contenedor ligero de despliegue para el framework BiCIAM"
LABEL version="1.0.0"

# Copiar el artefacto generado desde la etapa builder
COPY --from=builder /app/target/biciam-1.0.0-SNAPSHOT.jar app.jar

# Crear usuario sin privilegios por buenas practicas de seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Comando por defecto para ejecutar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]
