/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Pelicula;
import util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar operaciones de persistencia relacionadas con la entidad {@link Pelicula}.
 * Utiliza JDBC para interactuar con la base de datos a través de la clase {@link ConexionDB}.
 * 
 * Esta clase forma parte de la capa DAO (Data Access Object) del sistema CineMagenta.
 * 
 * @author Miguel
 */

public class PeliculaDAO {

    /**
     * Inserta una nueva película en la tabla Cartelera de la base de datos.
     *
     * @param p Objeto {@link Pelicula} que contiene los datos a insertar
     * @return {@code true} si la inserción fue exitosa; {@code false} en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
     */
    public boolean insertar(Pelicula p) throws SQLException {
        String sql = "INSERT INTO Cartelera (titulo, director, anno, duracion, genero) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getTitulo());
            stmt.setString(2, p.getDirector());
            stmt.setInt(3, p.getAnno());
            stmt.setInt(4, p.getDuracion());
            stmt.setString(5, p.getGenero());

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
     * Busca una película por su título en la tabla PELICULA.
     *
     * @param titulo Título de la película a buscar
     * @return Objeto {@link Pelicula} si se encuentra; {@code null} si no existe
     */
    public Pelicula buscarPorTitulo(String titulo) {
        Pelicula resultado = null;
        String sql = "SELECT * FROM CARTELERA WHERE TITULO = ?";

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
                    rs.getString("GENERO")
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
        String sql = "DELETE FROM CARTELERA WHERE TITULO = ?";

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

    public boolean actualizarPelicula(Pelicula p) {
        String sql = "UPDATE CARTELERA SET TITULO = ?, DIRECTOR = ?, ANNO = ?, DURACION = ?, GENERO = ? WHERE ID = ?";

        try (Connection conn = ConexionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getTitulo());
            stmt.setString(2, p.getDirector());
            stmt.setInt(3, p.getAnno());
            stmt.setInt(4, p.getDuracion());
            stmt.setString(5, p.getGenero());
            stmt.setInt(6, p.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error al actualizar película: " + e.getMessage());
            return false;
        }
    }
}
