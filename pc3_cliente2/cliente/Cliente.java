package cliente;

import comun.IServicioEstudiantes;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * cliente generico de rmi que contacta al servidor y despliega el menu iterativo.
 * el cliente no carga datos ni objetos logicamente pesados, solo delega al servidor.
 */
public class Cliente {

    /**
     * arranca el menu ciclico de consultas.
     * recibe obligatoriamente la ip del servidor por linea de comandos.
     * @param args array de strings, se espera la ip en args[0].
     */
    public static void main(String[] args) {

        // verificamos que el usuario haya pasado la ip del servidor como argumento
        if (args.length < 1) {
            System.out.println("uso: java cliente.Cliente <ip_del_servidor>");
            System.out.println("ejemplo: java cliente.Cliente 192.168.1.100");
            return;
        }

        String ipServidor = args[0];
        int puerto = 1099;

        // aca intentamos conectarnos al servidor
        // si el servidor esta apagado esto va a fallar y le avisamos al usuario
        IServicioEstudiantes servicio = null;
        try {
            // nos conectamos al servidor usando la ip que nos dieron
            // el nombre "ServicioEstudiantes" tiene que ser el mismo que puso el servidor
            System.out.println("conectando al servidor en " + ipServidor + ":" + puerto + "...");
            servicio = (IServicioEstudiantes) Naming.lookup(
                "rmi://" + ipServidor + ":" + puerto + "/ServicioEstudiantes"
            );
            System.out.println("conexion exitosa con el servidor");
        } catch (Exception e) {
            // si no se puede conectar al inicio el programa no puede seguir
            System.out.println("error: no se pudo contactar al servidor en " + ipServidor);
            System.out.println("verifique que el servidor este encendido y la ip sea correcta");
            System.out.println("detalle del error: " + e.getMessage());
            return;
        }

        // creamos el scanner para leer lo que el usuario escribe en la consola
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        // este es el loop principal del menu
        // se repite hasta que el usuario escoja salir
        while (continuar) {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE CONSULTA DE ESTUDIANTES");
            System.out.println("========================================");
            System.out.println("1. Consultar nombre por ID");
            System.out.println("2. Consultar notas (por ID o nombre)");
            System.out.println("3. Consultar grupo por ID");
            System.out.println("4. Salir");
            System.out.println("========================================");
            System.out.print("seleccione una opcion: ");

            // leemos la opcion como string para evitar errores
            // si el usuario mete letras en vez de numeros
            String opcion = scanner.nextLine().trim();

            // aca usamos un switch para ver que opcion escogio el usuario
            // cada caso llama al metodo remoto correspondiente en el servidor
            // cada llamada remota tiene su propio try-catch por si el servidor
            // se cae en medio de la sesion que el cliente no se muera
            switch (opcion) {
                case "1":
                    // consultar nombre por id
                    System.out.print("ingrese el id del estudiante: ");
                    String idNombre = scanner.nextLine().trim();
                    try {
                        // llamamos al metodo remoto del servidor
                        String nombre = servicio.consultarNombre(idNombre);
                        System.out.println(">>> resultado: " + nombre);
                    } catch (RemoteException e) {
                        // si el servidor se cayo o hubo un problema de red cae aca
                        System.out.println("error al consultar: no se pudo comunicar con el servidor");
                        System.out.println("detalle: " + e.getMessage());
                    }
                    break;

                case "2":
                    // consultar notas por id o por nombre
                    System.out.print("ingrese el id o nombre del estudiante: ");
                    String criterio = scanner.nextLine().trim();
                    try {
                        // aca el servidor se encarga de detectar si es id o nombre
                        String notas = servicio.consultarNotas(criterio);
                        System.out.println(">>> resultado: " + notas);
                    } catch (RemoteException e) {
                        System.out.println("error al consultar: no se pudo comunicar con el servidor");
                        System.out.println("detalle: " + e.getMessage());
                    }
                    break;

                case "3":
                    // consultar grupo por id
                    System.out.print("ingrese el id del estudiante: ");
                    String idGrupo = scanner.nextLine().trim();
                    try {
                        String grupo = servicio.consultarGrupo(idGrupo);
                        System.out.println(">>> resultado: " + grupo);
                    } catch (RemoteException e) {
                        System.out.println("error al consultar: no se pudo comunicar con el servidor");
                        System.out.println("detalle: " + e.getMessage());
                    }
                    break;

                case "4":
                    // salir del programa
                    continuar = false;
                    System.out.println("cerrando cliente...");
                    break;

                default:
                    // si meten cualquier otra cosa que no sea 1 2 3 o 4
                    System.out.println("opcion no valida por favor seleccione 1 2 3 o 4");
                    break;
            }
        }

        scanner.close();
    }
}
