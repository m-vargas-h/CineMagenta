package util;

import model.Pelicula;

import java.util.logging.Logger;

/**
 * Clase utilitaria encargada de validar los datos de una instancia de {@link Pelicula}
 * antes de ser procesada o almacenada en el sistema CineMagenta.
 * 
 * <p>Verifica que los campos obligatorios estén presentes y que los valores numéricos
 * cumplan con rangos lógicos definidos.</p>
 * 
 * <p>Esta clase lanza excepciones {@link IllegalArgumentException} en caso de datos inválidos
 * y registra los errores mediante {@link Logger}.</p>
 * 
 * @author Miguel
 */
public class PeliculaValidador {

    private static final Logger logger = Logger.getLogger(PeliculaValidador.class.getName());

    /**
     * Valida los datos de una instancia de {@link Pelicula}.
     * Verifica que los campos obligatorios no estén vacíos y que los valores numéricos sean válidos.
     * 
     * <p>Si algún dato es inválido, lanza una excepción {@link IllegalArgumentException}
     * y registra el motivo en el log.</p>
     * 
     * @param p La instancia de {@link Pelicula} a validar.
     * @throws IllegalArgumentException Si algún dato es inválido.
     */
    public static void validar(Pelicula p) throws IllegalArgumentException {
        if (p == null) {
            logger.warning("Validación fallida: película nula.");
            throw new IllegalArgumentException("La película no puede ser nula.");
        }
        if (p.getTitulo() == null || p.getTitulo().trim().isEmpty()) {
            logger.warning("Validación fallida: título vacío.");
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (p.getDirector() == null || p.getDirector().trim().isEmpty()) {
            logger.warning("Validación fallida: director vacío.");
            throw new IllegalArgumentException("El director no puede estar vacío.");
        }
        if (p.getAnno() < 1800 || p.getAnno() > 2100) {
            logger.warning("Validación fallida: año fuera de rango (" + p.getAnno() + ").");
            throw new IllegalArgumentException("El año debe estar entre 1800 y 2100.");
        }
        if (p.getDuracion() <= 0) {
            logger.warning("Validación fallida: duración inválida (" + p.getDuracion() + ").");
            throw new IllegalArgumentException("La duración debe ser mayor a cero.");
        }
        if (p.getGenero() == null) {
            logger.warning("Validación fallida: género nulo.");
            throw new IllegalArgumentException("El género no puede estar vacío.");
        }
    }
}