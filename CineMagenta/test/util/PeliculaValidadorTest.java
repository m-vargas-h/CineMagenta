package util;

import model.Genero;
import model.Pelicula;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase {@link PeliculaValidador}.
 * Verifica que los datos inválidos lancen excepciones y que los datos válidos pasen sin errores.
 *
 * @author Miguel
 */
public class PeliculaValidadorTest {

    @Test
    public void peliculaValida_noLanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 1999, 136, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p); // No lanza excepción
    }

    @Test(expected = IllegalArgumentException.class)
    public void peliculaNula_lanzaExcepcion() {
        PeliculaValidador.validar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tituloVacio_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "", "Wachowski", 1999, 136, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tituloSoloEspacios_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "   ", "Wachowski", 1999, 136, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void directorVacio_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "", 1999, 136, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void annoMenorA1800_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 1700, 136, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void annoMayorA2100_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 2200, 136, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void duracionNegativa_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 1999, -10, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void duracionCero_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 1999, 0, Genero.CIENCIA_FICCION, "matrix.jpg");
        PeliculaValidador.validar(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generoNulo_lanzaExcepcion() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 1999, 136, null, "matrix.jpg");
        PeliculaValidador.validar(p);
    }
}
