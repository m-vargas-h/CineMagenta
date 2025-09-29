/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.*;
import util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO encargada de gestionar operaciones de persistencia para la entidad {@link Pelicula}.
 * Utiliza JDBC para interactuar con la base de datos a través de {@link ConexionDB}.
 * Forma parte de la capa de acceso a datos del sistema CineMagenta.
 * 
 * Métodos disponibles:
 * <ul>
 *   <li>Insertar nueva película</li>
 *   <li>Actualizar película existente</li>
 *   <li>Eliminar película por título</li>
 *   <li>Buscar película por título</li>
 *   <li>Verificar existencia por ID</li>
 *   <li>Obtener lista de títulos</li>
 * </ul>
 * 
 * @author Miguel
 */
public class PeliculaDAO {

    /**
     * Inserta una nueva película en la base de datos.
     * 
     * @param p Película a insertar
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario
     */
    public boolean insertar(Pelicula p) throws SQLException {
        String sql = "INSERT INTO Cartelera (titulo, director, anno, duracion, genero, ruta_portada) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getTitulo());
            stmt.setString(2, p.getDirector());
            stmt.setInt(3, p.getAnno());
            stmt.setInt(4, p.getDuracion());
            stmt.setString(5, p.getGenero().getEtiqueta());
            stmt.setString(6, p.getRutaPortada());

            int filas = stmt.executeUpdate();
            return filas > 0;
        }
    }

    /**
     * Verifica si existe una película con el ID especificado en la tabla Cartelera.
     *
     * @param id Identificador único de la película
     * @return {@code true} si el ID existe en la base de datos; {@code false} si no se encuentra
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     */
    public boolean existeId(int id) throws SQLException {
        String sql = "SELECT id FROM Cartelera WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    
    /**
     * Busca una película por su título.
     * 
     * @param titulo Título de la película a buscar
     * @return Instancia de {@link Pelicula} si se encuentra, {@code null} si no existe
     */
    public Pelicula buscarPorTitulo(String titulo) {
        Pelicula resultado = null;
        String sql = "SELECT * FROM cartelera WHERE TITULO = ?";

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resultado = new Pelicula(
                    rs.getInt("ID"),
                    rs.getString("TITULO"),
                    rs.getString("DIRECTOR"),
                    rs.getInt("ANNO"),
                    rs.getInt("DURACION"),
                    Genero.desdeEtiqueta(rs.getString("GENERO")),
                    rs.getString("RUTA_PORTADA")
                );
            }

        } catch (Exception e) {
            System.err.println("Error al buscar película: " + e.getMessage());
        }

        return resultado;
    }

    /**
     * Elimina una película por su título en la tabla PELICULA.
     *
     * @param titulo Título de la película a eliminar
     * @return {@code true} si la eliminación fue exitosa; {@code false} si no se encontró o no se pudo eliminar
     */
    public boolean eliminarPorTitulo(String titulo) {
        String sql = "DELETE FROM cartelera WHERE TITULO = ?";

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;

        } catch (Exception e) {
            System.err.println("Error al eliminar película: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de una película existente en la tabla PELICULA.
     *
     * @param p Objeto {@link Pelicula} con los datos actualizados
     * @return {@code true} si la actualización fue exitosa; {@code false} si no se encontró o no se pudo actualizar
     */
    public boolean actualizarPelicula(Pelicula p) {
        String sql = "UPDATE cartelera SET titulo=?, director=?, anno=?, duracion=?, genero=?, ruta_portada=? WHERE id=?";


        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getTitulo());
            stmt.setString(2, p.getDirector());
            stmt.setInt(3, p.getAnno());
            stmt.setInt(4, p.getDuracion());
            stmt.setString(5, p.getGenero().getEtiqueta());
            stmt.setString(6, p.getRutaPortada());
            stmt.setInt(7, p.getId());


            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error al actualizar película: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene una lista con los títulos de todas las películas en la tabla PELICULA.
     *
     * @return Lista de títulos de películas; lista vacía si no hay películas
     */
    public List<String> obtenerTodosLosTitulos() {
        List<String> titulos = new ArrayList<>();
        String sql = "SELECT TITULO FROM cartelera ORDER BY TITULO ASC";

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                titulos.add(rs.getString("TITULO"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los títulos: " + e.getMessage());
        }

        return titulos;
    }

}
