#!/bin/bash
# este script compila todos los archivos java del servidor
# hay que ejecutarlo desde la carpeta pc1_servidor
# ejemplo: ./compilar.sh

# primero convertimos los finales de linea de windows a linux
# porque si copiaron los archivos desde windows van a tener \r
# y java o bash pueden fallar
find . -name "*.java" -exec sed -i 's/\r$//' {} +
find . -name "*.csv" -exec sed -i 's/\r$//' {} +
find . -name "*.sh" -exec sed -i 's/\r$//' {} +

echo "compilando archivos del servidor..."
javac comun/*.java servidor/*.java

if [ $? -eq 0 ]; then
    echo "compilacion exitosa"
else
    echo "hubo errores al compilar revise los mensajes de arriba"
fi
