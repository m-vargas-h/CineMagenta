package model;

/**
 * Enumeración que representa los géneros disponibles para las películas en el sistema CineMagenta.
 * Cada género tiene una etiqueta legible en español que se utiliza en la interfaz gráfica.
 * 
 * Este enum permite una representación tipada y segura de los géneros, evitando errores por cadenas mal escritas.
 * También incluye métodos utilitarios para obtener etiquetas y convertir desde texto.
 * 
 * Ejemplo de uso:
 * <pre>
 *     Genero g = Genero.ACCION;
 *     String etiqueta = g.getEtiqueta(); // "Acción"
 * </pre>
 * 
 * @author Miguel
 */
public enum Genero {
    ACCION("Acción"),
    COMEDIA("Comedia"),
    DRAMA("Drama"),
    SUSPENSO("Suspenso"),
    TERROR("Terror"),
    ANIMACION("Animación"),
    MUSICAL("Musical"),
    DOCUMENTAL("Documental"),
    ROMANCE("Romance");

    private final String etiqueta;

    /**
     * Constructor privado que asigna la etiqueta legible al género.
     * 
     * @param etiqueta Texto descriptivo del género
     */
    Genero(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve la etiqueta legible del género.
     * 
     * @return Etiqueta en español (ej. "Comedia", "Terror")
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Devuelve la representación en cadena del género.
     * 
     * @return La etiqueta del género.
     */
    @Override
    public String toString() {
        return etiqueta;
    }

    /**
     * Convierte una etiqueta textual en su correspondiente valor del enum.
     * 
     * @param texto Etiqueta textual (ej. "Drama")
     * @return Instancia de {@code Genero} correspondiente, o {@code null} si no coincide
     */
    public static Genero desdeEtiqueta(String texto) {
        for (Genero g : values()) {
            if (g.etiqueta.equalsIgnoreCase(texto.trim())) {
                return g;
            }
        }
        return null;
    }
}