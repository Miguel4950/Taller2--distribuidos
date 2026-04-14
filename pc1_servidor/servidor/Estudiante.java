package servidor;

/**
 * clase que representa a un estudiante y sus datos basicos.
 */
public class Estudiante {

    // aca guardamos los datos del estudiante como atributos simples
    // no usamos getters ni setters porque no es necesario para algo tan basico
    String grupo;
    String id;
    String nombre;
    double taller1;
    double taller2;

    /**
     * constructor que inicializa todos los datos del estudiante leyendo el csv.
     * @param grupo el grupo al que pertenece
     * @param id el identificador
     * @param nombre el nombre completo
     * @param taller1 la nota del primer taller
     * @param taller2 la nota del segundo taller
     */
    public Estudiante(String grupo, String id, String nombre, double taller1, double taller2) {
        this.grupo = grupo;
        this.id = id;
        this.nombre = nombre;
        this.taller1 = taller1;
        this.taller2 = taller2;
    }

    /**
     * metodo que calcula el promedio de los dos talleres.
     * @return el promedio matematico de las dos notas.
     */
    public double calcularPromedio() {
        // simplemente suma las dos notas y divide entre 2
        return (taller1 + taller2) / 2.0;
    }
}
