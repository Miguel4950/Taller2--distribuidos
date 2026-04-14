package comun;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaz remota que comparten el servidor y los clientes.
 * es el contrato que define los metodos que puede invocar el cliente.
 */
public interface IServicioEstudiantes extends Remote {

    /**
     * metodo que retorna el nombre completo de un estudiante dado su id.
     * @param id identificador unico del estudiante.
     * @return el nombre completo o un mensaje de error si no existe.
     * @throws RemoteException si hay un problema con la red o rmi.
     */
    String consultarNombre(String id) throws RemoteException;

    /**
     * metodo que busca el promedio de las notas del estudiante.
     * @param criterio puede ser el id o el nombre del estudiante.
     * @return el promedio de las evaluaciones o un mensaje si no se encuentra.
     * @throws RemoteException por errores de comunicacion rmi.
     */
    String consultarNotas(String criterio) throws RemoteException;

    /**
     * metodo que retorna el grupo de un estudiante dado su id.
     * @param id identificador unico del estudiante.
     * @return el grupo asignado o un error.
     * @throws RemoteException en caso de error de red.
     */
    String consultarGrupo(String id) throws RemoteException;
}
