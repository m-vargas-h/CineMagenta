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
}
