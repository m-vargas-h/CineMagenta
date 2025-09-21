/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import model.Pelicula;

/**
 * Clase utilitaria encargada de validar los datos de una instancia de {@link Pelicula}
 * antes de ser procesada o almacenada en el sistema CineMagenta.
 * 
 * <p>Verifica que los campos obligatorios estén presentes y que los valores numéricos
 * cumplan con rangos lógicos definidos.</p>
 * 
 * <p>Esta clase lanza excepciones {@link IllegalArgumentException} en caso de datos inválidos.</p>
 * 
 * @author Miguel
 */
public class PeliculaValidador {

    /**
     * Valida los datos de una instancia de {@link Pelicula}.
     * 
     * @param p La instancia de {@link Pelicula} a validar.
     * @throws IllegalArgumentException Si algún dato es inválido.
     */
    public static void validar(Pelicula p) throws IllegalArgumentException {
        if (p == null) {
            throw new IllegalArgumentException("La película no puede ser nula.");
        }
        if (p.getTitulo() == null || p.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (p.getDirector() == null || p.getDirector().trim().isEmpty()) {
            throw new IllegalArgumentException("El director no puede estar vacío.");
        }
        if (p.getAnno() < 1900 || p.getAnno() > 2100) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y 2100.");
        }
        if (p.getDuracion() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a cero.");
        }
        if (p.getGenero() == null || p.getGenero().trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío.");
        }
    }
}