package servidor;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * clase que inicia el servidor, crea el registro rmi y publica el servicio.
 */
public class Servidor {

    /**
     * metodo principal de ejecucion del servidor.
     * levanta el registro rmi en el puerto indicado.
     * @param args argumentos enviados por consola (no usados en servidor).
     */
    public static void main(String[] args) {
        try {
            // definimos el puerto donde va a escuchar el servidor
            // usamos 1099 que es el puerto estandar de rmi
            int puerto = 1099;

            System.out.println("iniciando servidor...");

            // bueno aqui creamos el registro en el puerto que dijo el profe
            // para que los clientes se conecten
            // esto es como el "directorio" donde publicamos nuestro servicio
            LocateRegistry.createRegistry(puerto);

            // creamos la instancia del servicio que implementa los metodos remotos
            // le pasamos la ruta del archivo con los datos de los estudiantes
            // el archivo se lee una sola vez aqui y queda en memoria
            ServicioEstudiantesImpl servicio = new ServicioEstudiantesImpl("datos/estudiantes.csv");

            // publicamos el servicio en el registro con un nombre
            // los clientes van a usar este nombre para encontrar el servicio
            // usamos rebind por si ya habia algo publicado con ese nombre
            Naming.rebind("rmi://localhost:" + puerto + "/ServicioEstudiantes", servicio);

            System.out.println("servidor encendido y escuchando en el puerto " + puerto);
            System.out.println("esperando conexiones de los clientes...");
            System.out.println("para apagar el servidor presione ctrl+c");

        } catch (Exception e) {
            System.out.println("error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
