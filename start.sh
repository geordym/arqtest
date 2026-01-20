#!/bin/bash

# Establecer JAVA_HOME
export JAVA_HOME=/home/geordy/.jdks/openjdk-25.0.1
export PATH=$JAVA_HOME/bin:$PATH

# Verificar que Java esté disponible
echo "=========================================="
echo "Usando Java: $($JAVA_HOME/bin/java -version 2>&1 | head -n 1)"
echo "=========================================="

# Descargar OpenTelemetry Java Agent si no existe
if [ ! -f "opentelemetry-javaagent.jar" ]; then
    echo "Descargando OpenTelemetry Java Agent..."
    curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar -o opentelemetry-javaagent.jar
    echo "✅ Agent descargado"
fi

# Limpiar y compilar
echo "=========================================="
echo "Compilando aplicación..."
echo "=========================================="
./gradlew clean build -x test

# Verificar que la compilación fue exitosa
if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación"
    exit 1
fi

# Encontrar el JAR generado
JAR_FILE=$(find build/libs -name "*.jar" -not -name "*-plain.jar" | head -n 1)

if [ -z "$JAR_FILE" ]; then
    echo "❌ No se encontró el JAR compilado"
    exit 1
fi

echo "✅ Compilación exitosa: $JAR_FILE"

# Arrancar la aplicación con OpenTelemetry
echo "=========================================="
echo "Iniciando aplicación con OpenTelemetry..."
echo "=========================================="

$JAVA_HOME/bin/java \
  -javaagent:opentelemetry-javaagent.jar \
  -Dotel.service.name=app \
  -Dotel.exporter.otlp.endpoint=http://localhost:4318 \
  -Dotel.logs.exporter=otlp \
  -Dotel.metrics.exporter=otlp \
  -Dotel.traces.exporter=otlp \
  -Dotel.instrumentation.log4j-appender.experimental.capture-mdc-attributes=* \
  -jar "$JAR_FILE"