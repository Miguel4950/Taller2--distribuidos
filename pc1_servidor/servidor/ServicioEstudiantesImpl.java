package servidor;

import comun.IServicioEstudiantes;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * implementacion del servicio que atiende las peticiones de los clientes.
 * hereda de unicastremoteobject para que rmi maneje la comunicacion de red.
 */
public class ServicioEstudiantesImpl extends UnicastRemoteObject implements IServicioEstudiantes {

    // aca guardamos todos los estudiantes que leimos del archivo
    // usamos un ArrayList porque es lo mas simple y no necesitamos nada fancy
    private ArrayList<Estudiante> estudiantes;

    /**
     * constructor del implementador de servicios.
     * lee el archivo de datos una unica vez al iniciarse el servidor.
     * @param rutaArchivo direccion local del archivo con los datos (.csv o .txt)
     * @throws RemoteException en caso de error interno rmi.
     */
    public ServicioEstudiantesImpl(String rutaArchivo) throws RemoteException {
        super();
        estudiantes = new ArrayList<>();
        cargarDatos(rutaArchivo);
    }

    /**
     * lee el archivo csv linea por linea y alimenta el arraylist.
     * @param rutaArchivo ubicacion del archivo a instanciar.
     */
    private void cargarDatos(String rutaArchivo) {
        // usamos punto y coma como separador porque algunos nombres pueden tener comas
        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            String linea;

            // nos saltamos la primera linea porque es el encabezado
            br.readLine();

            while ((linea = br.readLine()) != null) {
                // si la linea esta vacia la saltamos para que no reviente
                if (linea.trim().isEmpty()) {
                    continue;
                }

                // separamos la linea por punto y coma
                String[] partes = linea.split(";");

                // verificamos que la linea tenga las 5 columnas que esperamos
                if (partes.length == 5) {
                    String grupo = partes[0].trim();
                    String id = partes[1].trim();
                    String nombre = partes[2].trim();
                    double taller1 = Double.parseDouble(partes[3].trim());
                    double taller2 = Double.parseDouble(partes[4].trim());

                    // creamos el estudiante y lo agregamos a la lista
                    Estudiante est = new Estudiante(grupo, id, nombre, taller1, taller2);
                    estudiantes.add(est);
                }
            }

            br.close();
            System.out.println("se cargaron " + estudiantes.size() + " estudiantes del archivo");

        } catch (Exception e) {
            System.out.println("error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * metodo que busca un estudiante por su id en la lista cargada en memoria.
     * @param id identificador unico del estudiante digitado en el cliente.
     * @return el nombre completo del estudiante o un mensaje indicando que no se encontro.
     */
    @Override
    public String consultarNombre(String id) throws RemoteException {
        System.out.println("cliente solicita nombre del estudiante con id: " + id);

        // recorremos la lista buscando el id que nos mandaron
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            if (est.id.equals(id)) {
                return est.nombre;
            }
        }

        return "estudiante no encontrado con id: " + id;
    }

    /**
     * metodo que consulta el promedio de calificaciones de un estudiante.
     * intenta buscar por id, y si falla entonces busca comparando el nombre.
     * @param criterio ya sea el id o el nombre completo del estudiante.
     * @return el promedio exacto de los talleres o un aviso de error si no existe.
     */
    @Override
    public String consultarNotas(String criterio) throws RemoteException {
        System.out.println("cliente solicita notas con criterio: " + criterio);

        // primero intentamos buscar por id recorriendo la lista
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            if (est.id.equals(criterio)) {
                double promedio = est.calcularPromedio();
                return "el promedio de " + est.nombre + " (id: " + est.id + ") es: " + String.format("%.2f", promedio);
            }
        }

        // si no encontramos por id entonces buscamos por nombre
        // hacemos la comparacion ignorando mayusculas para que sea mas flexible
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            if (est.nombre.equalsIgnoreCase(criterio)) {
                double promedio = est.calcularPromedio();
                return "el promedio de " + est.nombre + " (id: " + est.id + ") es: " + String.format("%.2f", promedio);
            }
        }

        return "estudiante no encontrado con criterio: " + criterio;
    }

    /**
     * metodo que obtiene el grupo de trabajo de un estudiante por su id.
     * @param id identificador unico a buscar.
     * @return el grupo asignado al estudiante o un mensaje de error si no existe.
     */
    @Override
    public String consultarGrupo(String id) throws RemoteException {
        System.out.println("cliente solicita grupo del estudiante con id: " + id);

        // recorremos la lista buscando el id
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante est = estudiantes.get(i);
            if (est.id.equals(id)) {
                return "el estudiante " + est.nombre + " pertenece al grupo: " + est.grupo;
            }
        }

        return "estudiante no encontrado con id: " + id;
    }
}
