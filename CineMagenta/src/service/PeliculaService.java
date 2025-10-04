package service;

import dao.PeliculaDAO;
import model.Genero;
import model.Pelicula;
import util.PeliculaValidador;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de servicio que encapsula la lógica de negocio relacionada con películas.
 * Valida los datos antes de delegar en {@link PeliculaDAO} para operaciones de persistencia.
 * Forma parte de la capa de servicio del sistema CineMagenta.
 * 
 * Utiliza {@link PeliculaValidador} para asegurar que los datos sean correctos antes de insertarlos o modificarlos.
 * 
 * @author Miguel
 */
public class PeliculaService {

    private final PeliculaDAO dao = new PeliculaDAO();
    
    /**
     * Busca una película por su título.
     * 
     * @param titulo Título de la película a buscar
     * @return Instancia de {@link Pelicula} si se encuentra, {@code null} si no existe
     */
    public Pelicula buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return dao.buscarPorTitulo(titulo.trim());
    }

    /**
     * Agrega una nueva película tras validarla.
     * 
     * @param p Película a agregar
     * @return {@code true} si fue agregada correctamente, {@code false} si ocurrió un error
     */
    public boolean agregarPelicula(Pelicula p) {
        if (!validarPelicula(p)) return false;

        try {
            return dao.insertar(p);
        } catch (Exception ex) {
            mostrarError("Error al agregar la película", ex);
            return false;
        }
    }

    /**
     * Modifica una película existente tras validarla.
     * 
     * @param p Película con los datos actualizados
     * @return {@code true} si fue modificada correctamente, {@code false} si ocurrió un error
     */
    public boolean modificarPelicula(Pelicula p) {
        if (p.getId() <= 0) {
            JOptionPane.showMessageDialog(null, "La película debe tener un ID válido para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarPelicula(p)) return false;

        try {
            return dao.actualizarPelicula(p);
        } catch (Exception ex) {
            mostrarError("Error al modificar la película", ex);
            return false;
        }
    }

    /**
     * Elimina una película por su título.
     * 
     * @param titulo Título de la película a eliminar
     * @return {@code true} si fue eliminada correctamente, {@code false} si no se encontró
     */
    public boolean eliminarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar un título válido para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            return dao.eliminarPorTitulo(titulo.trim());
        } catch (Exception ex) {
            mostrarError("Error al eliminar la película", ex);
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los títulos de películas en el sistema.
     * @return Lista de títulos de películas.
     */
    public List<String> obtenerTodosLosTitulos() {
        return dao.obtenerTodosLosTitulos();
    }

    /**
     * Valida los datos de una película.
     * @param p La película a validar.
     * @return true si los datos son válidos, false en caso contrario.
     */
    private boolean validarPelicula(Pelicula p) {
        if (p.getTitulo().isEmpty() || p.getDirector().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Título y director son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (p.getAnno() <= 1800 || p.getDuracion() <= 0) {
            JOptionPane.showMessageDialog(null, "Año y duración deben ser valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     * @param mensaje El mensaje a mostrar.
     * @param ex La excepción que causó el error.
     */
    private void mostrarError(String mensaje, Exception ex) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    /**
     * Obtiene una lista de películas aplicando filtros opcionales por género y rango de años.
     * 
     * @param genero    Género a filtrar (puede ser {@code null} para no aplicar filtro)
     * @param annoDesde Año inicial del rango (puede ser {@code null} para no aplicar filtro)
     * @param annoHasta Año final del rango (puede ser {@code null} para no aplicar filtro)
     * @return Lista de películas que cumplen con los filtros especificados
     */
    public List<Pelicula> listarPeliculas(Genero genero, Integer annoDesde, Integer annoHasta) {
        try {
            return dao.listarPeliculas(genero, annoDesde, annoHasta);
        } catch (Exception ex) {
            mostrarError("Error al listar películas", ex);
            return new ArrayList<>();
        }
    }

    public List<Pelicula> buscarPeliculasPorTitulo(String texto) {
        try {
            return dao.buscarPorTituloParcial(texto);
        } catch (Exception ex) {
            mostrarError("Error al buscar películas", ex);
            return List.of();
        }
    }

}