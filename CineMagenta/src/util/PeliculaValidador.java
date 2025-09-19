/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import model.Pelicula;

/**
 *
 * @author mvarg
 */

public class PeliculaValidador {

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
        if (p.getAnio() < 1900 || p.getAnio() > 2100) {
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
