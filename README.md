# Taller de Sistemas Distribuidos: RMI (Cliente-Servidor)

Este repositorio contiene la implementación de un componente/sistema distribuido basado en **Java RMI** (Remote Method Invocation). Permite gestionar la información pre-cargada de estudiantes de un curso a través de un esquema Cliente-Servidor.

## Arquitectura del Proyecto (Despliegue para 3 Máquinas Virtuales)

La distribución se ha adaptado específicamente para tus **3 Máquinas Virtuales Ubuntu**:

- **Máquina 1 (IP: `10.43.98.198`):** Alojará al Servidor Central y al Cliente 1 (`pc1_servidor` y `pc2_cliente1`).
- **Máquina 2 (IP: `10.43.98.199`):** Alojará al Cliente 2 (`pc3_cliente2`).
- **Máquina 3 (IP: `10.43.99.183`):** Alojará al Cliente 3 (`pc4_cliente3`).

---

## Manual de Instalación y Funcionamiento (Copiar y Pegar)

A continuación se detalla el paso a paso exacto para descargar, configurar y arrancar todo en las 3 máquinas.

### 1. Preparación del Sistema y Descarga (Aplica a las 3 VMs)

Abre la terminal en **cada una de las 3 máquinas virtuales** y pega estos comandos. Instalarán Java, clonarán tu proyecto de GitHub y darán los permisos necesarios en un solo golpe:

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk git
git clone https://github.com/Miguel4950/Taller2--distribuidos.git
cd Taller2--distribuidos
find . -name "*.sh" -exec chmod +x {} +
```

---

## Ejecución del Sistema: Comandos Exactos

> **IMPORTANTE:** Primero se levanta el servidor en la Máquina 1, y luego los clientes se pueden ir encendiendo en cualquier máquina.

### PASO 1: Arrancar el Servidor (HACER SOLO EN LA MÁQUINA 1 - IP: 10.43.98.198)

En la terminal de la Máquina 1 (`10.43.98.198`), navega hasta la carpeta del servidor, compílalo e inícialo pasándole su propia IP para el binding de RMI:

```bash
cd pc1_servidor
./compilar.sh
./ejecutar.sh 10.43.98.198
```
Verás el mensaje de confirmación *"esperando conexiones de los clientes..."*. **¡No cierres esta terminal!** Debes dejarla abierta para que el servidor siga corriendo.

### PASO 2: Arrancar el Cliente 1 (TAMBIÉN EN MÁQUINA 1)

Como tienes 3 VMs, colocarás el primer cliente compartiendo el espacio con el servidor. Abre una **nueva pestaña** o ventana de terminal en esa misma Máquina 1 y ejecuta:
```bash
cd ~/Taller2--distribuidos/pc2_cliente1
./compilar.sh
./ejecutar.sh 10.43.98.198
```

### PASO 3: Arrancar el Cliente 2 (HACER EN MÁQUINA 2 - IP: 10.43.98.199)

Ve a tu segunda máquina virtual, asegúrate de estar dentro de la carpeta `Taller2--distribuidos` (que descargaste en el paso de Preparación) y ejecuta:
```bash
cd pc3_cliente2
./compilar.sh
./ejecutar.sh 10.43.98.198
```

### PASO 4: Arrancar el Cliente 3 (HACER EN MÁQUINA 3 - IP: 10.43.99.183)

Ve a tu tercera máquina virtual y repite el proceso:
```bash
cd pc4_cliente3
./compilar.sh
./ejecutar.sh 10.43.98.198
```

¡Has completado el despliegue! Todas tus máquinas estarán ahora comunicadas enviando las consultas al servidor en `10.43.98.198:1099`.
