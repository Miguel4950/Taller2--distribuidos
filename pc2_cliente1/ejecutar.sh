#!/bin/bash
# este script ejecuta el cliente y se conecta al servidor
# hay que pasarle la ip del servidor como argumento
# ejemplo: ./ejecutar.sh 192.168.1.50

if [ -z "$1" ]; then
    echo "error: debe pasar la ip del servidor como argumento"
    echo "uso: ./ejecutar.sh <ip_del_servidor>"
    echo "ejemplo: ./ejecutar.sh 192.168.1.50"
    exit 1
fi

java cliente.Cliente $1
