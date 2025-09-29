/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Representa una película dentro del sistema CineMagenta.
 * Contiene información relevante como título, director, año de estreno, duración, género y ruta de portada.
 * 
 * Esta clase forma parte del modelo de datos y se utiliza para mapear registros de la cartelera.
 * Incluye constructores para películas nuevas y existentes, además de métodos de acceso.
 * 
 * Ejemplo de uso:
 * <pre>
 *     Pelicula p = new Pelicula("Matrix", "Wachowski", 1999, 136, Genero.ACCION, "matrix.jpg");
 * </pre>
 * 
 * @author Miguel
 */
public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private int anno;
    private int duracion; // en minutos
    private Genero genero;
    private String rutaPortada;

    /**
     * Constructor completo con ID, usado para registros existentes.
     * 
     * @param id Identificador único de la película
     * @param titulo Título de la película
     * @param director Nombre del director
     * @param anno Año de estreno
     * @param duracion Duración en minutos
     * @param genero Género principal de la película
     * @param rutaPortada Ruta del archivo de la portada
     */
    public Pelicula(int id, String titulo, String director, int anno, int duracion, Genero genero, String rutaPortada) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anno = anno;
        this.duracion = duracion;
        this.genero = genero;
        this.rutaPortada = rutaPortada;
    }

    // Getters

    /**
     * Obtiene el identificador de la película.
     * @return id de la película
     */
    public int getId() { 
        return id; 
    }

    /**
     * Obtiene el título de la película.
     * @return título
     */
    public String getTitulo() { 
        return titulo; 
    }

    /**
     * Obtiene el nombre del director de la película.
     * @return nombre del director
     */
    public String getDirector() { 
        return director; 
    }

    /**
     * Obtiene el año de estreno de la película.
     * @return año de estreno
     */
    public int getAnno() { 
        return anno; 
    }

    /**
     * Obtiene la duración de la película en minutos.
     * @return duración en minutos
     */
    public int getDuracion() { 
        return duracion; 
    }

    /**
     * Obtiene el género principal de la película.
     * @return género
     */
    public Genero getGenero() { 
        return genero; 
    }

    /**
     * Obtiene la ruta del archivo de la portada de la película.
     * @return ruta de la portada
     */
    public String getRutaPortada() {
        return rutaPortada;
    }

    // Setters

    /**
     * Establece el identificador de la película.
     * @param id nuevo identificador
     */
    public void setId(int id) { 
        this.id = id; 
    }

    /**
     * Establece el título de la película.
     * @param titulo nuevo título
     */
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }

    /**
     * Establece el nombre del director de la película.
     * @param director nuevo nombre del director
     */
    public void setDirector(String director) { 
        this.director = director; 
    }

    /**
     * Establece el año de estreno de la película.
     * @param anno nuevo año de estreno
     */
    public void setAnno(int anno) { 
        this.anno = anno; 
    }

    /**
     * Establece la duración de la película en minutos.
     * @param duracion nueva duración en minutos
     */
    public void setDuracion(int duracion) { 
        this.duracion = duracion; 
    }

    /**
     * Establece el género principal de la película.
     * @param genero nuevo género
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Establece la ruta del archivo de la portada de la película.
     * @param rutaPortada nueva ruta de la portada
     */
    public void setRutaPortada(String rutaPortada) {
        this.rutaPortada = rutaPortada;
    }

    /**
     * Devuelve una representación textual de la película.
     * @return cadena con los datos de la película
     */
    @Override
    public String toString() {
        return String.format(
            "Pelicula{id=%d, titulo='%s', director='%s', año=%d, duración=%d min, género='%s', portada='%s'}",
            id, titulo, director, anno, duracion, genero, rutaPortada
        );
    }
}