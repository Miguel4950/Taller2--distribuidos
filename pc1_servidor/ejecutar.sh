#!/bin/bash
# este script arranca el servidor rmi
# hay que pasarle la ip de esta maquina como argumento
# esto es importante porque rmi necesita saber la ip real del servidor
# para que los clientes puedan conectarse desde otras maquinas
# ejemplo: ./ejecutar.sh 192.168.1.50

if [ -z "$1" ]; then
    echo "error: debe pasar la ip de esta maquina como argumento"
    echo "uso: ./ejecutar.sh <ip_de_esta_maquina>"
    echo "ejemplo: ./ejecutar.sh 192.168.1.50"
    exit 1
fi

# el flag java.rmi.server.hostname le dice a rmi cual es la ip real
# del servidor para que los stubs que envie a los clientes tengan
# la ip correcta y no localhost
java -Djava.rmi.server.hostname=$1 servidor.Servidor
