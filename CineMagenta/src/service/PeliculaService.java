package service;

import dao.PeliculaDAO;
import model.Genero;
import model.Pelicula;
import util.DialogUtils;
import util.PeliculaValidador;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de servicio que encapsula la lógica de negocio relacionada con películas.
 * Valida los datos antes de delegar en {@link PeliculaDAO} para operaciones de persistencia.
 * Forma parte de la capa de servicio del sistema CineMagenta.
 * 
 * Utiliza {@link PeliculaValidador} para asegurar que los datos sean correctos antes de insertarlos o modificarlos.
 * Los mensajes de error e información se muestran mediante {@link DialogUtils}.
 * Los eventos importantes se registran mediante {@link Logger}.
 * 
 * @author Miguel
 */
public class PeliculaService {

    private final PeliculaDAO dao;
    private static final Logger logger = Logger.getLogger(PeliculaService.class.getName());

    /**
     * Constructor principal que utiliza el DAO real.
     */
    public PeliculaService() {
        this.dao = new PeliculaDAO();
    }

    /**
     * Constructor alternativo para pruebas, permite inyectar un DAO simulado.
     *
     * @param dao DAO personalizado para pruebas
     */
    public PeliculaService(PeliculaDAO dao) {
        this.dao = dao;
    }

    /**
     * Busca una película por su título exacto.
     * Valida que el título no esté vacío y registra el resultado en el log.
     *
     * @param titulo Título de la película a buscar
     * @return Instancia de {@link Pelicula} si se encuentra, {@code null} si no existe o si el título es inválido
     */
    public Pelicula buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            DialogUtils.error("El título no puede estar vacío.");
            logger.warning("Búsqueda fallida: título vacío.");
            return null;
        }
        Pelicula p = dao.buscarPorTitulo(titulo.trim());
        logger.info("Búsqueda por título: '" + titulo + "' → " + (p != null ? "encontrada" : "no encontrada"));
        return p;
    }

    /**
     * Agrega una nueva película tras validarla.
     * Registra el intento y el resultado en el log.
     *
     * @param p Película a agregar
     * @return {@code true} si fue agregada correctamente, {@code false} si ocurrió un error o no pasó la validación
     */
    public boolean agregarPelicula(Pelicula p) {
        if (!validarPelicula(p)) {
            logger.warning("Película inválida para agregar: " + p);
            return false;
        }

        try {
            boolean resultado = dao.insertar(p);
            logger.info("Película agregada: " + p.getTitulo() + " → " + resultado);
            return resultado;
        } catch (Exception ex) {
            mostrarError("Error al agregar la película", ex);
            return false;
        }
    }

    /**
     * Modifica una película existente tras validarla.
     * Verifica que tenga ID válido, registra el intento y el resultado en el log.
     *
     * @param p Película con los datos actualizados
     * @return {@code true} si fue modificada correctamente, {@code false} si ocurrió un error o no pasó la validación
     */
    public boolean modificarPelicula(Pelicula p) {
        if (p.getId() <= 0) {
            DialogUtils.error("La película debe tener un ID válido para modificar.");
            logger.warning("Modificación fallida: ID inválido para " + p.getTitulo());
            return false;
        }
        if (!validarPelicula(p)) {
            logger.warning("Película inválida para modificar: " + p);
            return false;
        }

        try {
            boolean resultado = dao.actualizarPelicula(p);
            logger.info("Película modificada: " + p.getTitulo() + " → " + resultado);
            return resultado;
        } catch (Exception ex) {
            mostrarError("Error al modificar la película", ex);
            return false;
        }
    }

    /**
     * Elimina una película por su título.
     * Valida que el título no esté vacío y registra el resultado en el log.
     *
     * @param titulo Título de la película a eliminar
     * @return {@code true} si fue eliminada correctamente, {@code false} si no se encontró o el título es inválido
     */
    public boolean eliminarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            DialogUtils.error("Debes ingresar un título válido para eliminar.");
            logger.warning("Eliminación fallida: título vacío.");
            return false;
        }

        try {
            boolean resultado = dao.eliminarPorTitulo(titulo.trim());
            logger.info("Película eliminada: " + titulo + " → " + resultado);
            return resultado;
        } catch (Exception ex) {
            mostrarError("Error al eliminar la película", ex);
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los títulos de películas en el sistema.
     * Registra la cantidad obtenida en el log.
     *
     * @return Lista de títulos de películas
     */
    public List<String> obtenerTodosLosTitulos() {
        List<String> titulos = dao.obtenerTodosLosTitulos();
        logger.info("Títulos obtenidos: " + titulos.size());
        return titulos;
    }

    /**
     * Valida los datos de una película antes de insertarla o modificarla.
     * Muestra mensajes al usuario y registra advertencias en el log si los datos son inválidos.
     *
     * @param p La película a validar
     * @return {@code true} si los datos son válidos, {@code false} en caso contrario
     */
    private boolean validarPelicula(Pelicula p) {
        if (p.getTitulo().isEmpty() || p.getDirector().isEmpty()) {
            DialogUtils.error("Título y director son obligatorios.");
            logger.warning("Validación fallida: título/director vacío.");
            return false;
        }
        if (p.getAnno() <= 1800 || p.getDuracion() <= 0) {
            DialogUtils.error("Año y duración deben ser valores válidos.");
            logger.warning("Validación fallida: año/duración inválidos.");
            return false;
        }
        return true;
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo y registra la excepción en el log.
     *
     * @param mensaje Mensaje a mostrar al usuario
     * @param ex Excepción que causó el error
     */
    private void mostrarError(String mensaje, Exception ex) {
        DialogUtils.error(mensaje + ": " + ex.getMessage());
        logger.log(Level.SEVERE, mensaje, ex);
    }

    /**
     * Obtiene una lista de películas aplicando filtros opcionales por género y rango de años.
     * Registra los filtros aplicados y el resultado en el log.
     *
     * @param genero    Género a filtrar (puede ser {@code null} para no aplicar filtro)
     * @param annoDesde Año inicial del rango (puede ser {@code null} para no aplicar filtro)
     * @param annoHasta Año final del rango (puede ser {@code null} para no aplicar filtro)
     * @return Lista de películas que cumplen con los filtros especificados, o una lista vacía si ocurre un error
     */
    public List<Pelicula> listarPeliculas(Genero genero, Integer annoDesde, Integer annoHasta) {
        try {
            List<Pelicula> lista = dao.listarPeliculas(genero, annoDesde, annoHasta);
            logger.info("Listado de películas: genero=" + genero + ", desde=" + annoDesde + ", hasta=" + annoHasta + " → " + lista.size() + " resultado(s)");
            return lista;
        } catch (Exception ex) {
            mostrarError("Error al listar películas", ex);
            return new ArrayList<>();
        }
    }

    /**
     * Busca películas cuyo título contenga el texto indicado (búsqueda parcial).
     * Registra el texto buscado y la cantidad de resultados en el log.
     *
     * @param texto Texto a buscar dentro del título
     * @return Lista de películas que coinciden parcial o totalmente, o una lista vacía si ocurre un error
     */
    public List<Pelicula> buscarPeliculasPorTitulo(String texto) {
        try {
            List<Pelicula> resultados = dao.buscarPorTituloParcial(texto);
            logger.info("Búsqueda parcial por título='" + texto + "' → " + resultados.size() + " resultado(s)");
            return resultados;
        } catch (Exception ex) {
            mostrarError("Error al buscar películas", ex);
            return List.of();
        }
    }
}