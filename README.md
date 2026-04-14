# Taller de Sistemas Distribuidos: RMI (Cliente-Servidor)

Este repositorio contiene la implementación de un componente/sistema distribuido basado en **Java RMI** (Remote Method Invocation). Permite gestionar la información pre-cargada de estudiantes de un curso a través de un esquema Cliente-Servidor desplegado en distintas Máquinas Virtuales.

## Arquitectura del Proyecto

El sistema está diseñado para correr en una infraestructura de **4 PCs o Máquinas Virtuales (VMs)**. Consta de:
- **1 Servidor Central:** Carga los datos desde un archivo `.csv` en memoria a través de su arranque y atiende las llamadas por red exponiendo un servicio remoto.
- **3 Clientes:** Interfaz de consola que se conecta mediante una IP al servidor principal para las consultas. No poseen datos locales.

---

## Manual de Instalación y Funcionamiento

A continuación se detalla el paso a paso para configurar este despliegue desde cero, asumiendo una instalación completamente limpia en las 4 Máquinas Virtuales (ej. Ubuntu Server/Desktop).

### 1. Preparación del Sistema (Aplica a las 4 PCs)

Dado que las computadoras (o VMs) vienen completamente de fábrica, el primer paso en **cada una de las 4 máquinas** es instalar Java Development Kit (JDK 17).
Abre una terminal y ejecuta rigurosamente los siguientes comandos:

```bash
# Refrescar los repositorios de paquetes
sudo apt update

# Instalar Java 17 y herramientas de compilación
sudo apt install -y openjdk-17-jdk

# Comprobar que la instalación fue exitosa (Debe imprimir versión 17.x)
java --version
javac --version
```

### 2. Distribución de los Archivos

Debes pasar el código fuente a cada máquina según su rol. Puedes usar clonación de Git o enviar las carpetas por SCP/WinSCP/USB.
Distribuir de esta manera exacta:

- **En la PC 1 (Servidor):** Descarga/Copia la carpeta entera `pc1_servidor`
- **En la PC 2 (Cliente 1):** Descarga/Copia la carpeta entera `pc2_cliente1`
- **En la PC 3 (Cliente 2):** Descarga/Copia la carpeta entera `pc3_cliente2`
- **En la PC 4 (Cliente 3):** Descarga/Copia la carpeta entera `pc4_cliente3`

### 3. Otorgar Permisos a los Scripts (Aplica a las 4 PCs)

Una vez ubicada la respectiva carpeta en cada máquina virtual, navega mediante la terminal dentro de tu carpeta (`cd pcX...`) y ejecuta este comando para dar permisos de ejecución a los scripts de apoyo:

```bash
chmod +x compilar.sh ejecutar.sh
```

---

## Ejecución del Sistema

> **NOTA IMPORTANTE ANTES DE INICIAR:** Debes encender **PRIMERO** el servidor. Para ello, necesitas conocer la IP local de esa Máquina 1 (IP interna en tu red). Puedes averiguarlo escribiendo `ip a` (Ubuntu) o `ifconfig` en la terminal de esa PC. (Ejemplo asumido: `192.168.1.50`).

### Paso 1: Levantar el Servidor (PC 1)

Dirígete a la PC 1, abre una terminal en la carpeta `pc1_servidor` y compila el código:
```bash
./compilar.sh
```

Levanta el servidor pasándole LA IP DE ESA MISMA MÁQUINA (esto es indispensable para que RMI asigne correctamente los localizadores a los clientes):
```bash
./ejecutar.sh 192.168.1.50
```
Verás el mensaje de confirmación *"servidor encendido y escuchando en el puerto 1099"*. **Déjalo corriendo.**

### Paso 2: Levantar los Clientes (PC 2, PC 3, PC 4)

Dirígete a cualquier máquina de los clientes, abre una terminal en su carpeta respectiva (ej. `pc2_cliente1`) y compila su código:
```bash
./compilar.sh
```

Ahora, conéctate referenciando **la IP del Servidor** (La PC 1):
```bash
./ejecutar.sh 192.168.1.50
```

Se abrirá el Menú Interactivo del Sistema de Consulta y ya puedes realizar solicitudes.

---

## Tolerancia a Fallos
El sistema incorpora control interno de fallos de red (`RemoteException` y bloques `Try/Catch`), lo que garantiza que:
1. El consultar por un **ID inválido** no interrumpe el servicio, sino que avisa debidamente que el estudiante no se encontró.
2. Si un cliente pierde repentinamente conexión al servidor mientras utiliza la app (apagas la PC 1 abruptamente), el software detecta el colapso sin morir con un `NullPointerException`, retornando la interrupción formalmente a la consola. Y permite seguir esperando u operando.
