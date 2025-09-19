/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mvarg
 */

public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private int anio;
    private int duracion; // en minutos
    private String genero;

    public Pelicula(int id, String titulo, String director, int anio, int duracion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDirector() { return director; }
    public int getAnio() { return anio; }
    public int getDuracion() { return duracion; }
    public String getGenero() { return genero; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDirector(String director) { this.director = director; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String toString() {
        return String.format("Pelicula{id=%d, titulo='%s', director='%s', año=%d, duración=%d min, género='%s'}",
                id, titulo, director, anio, duracion, genero);
    }
}
