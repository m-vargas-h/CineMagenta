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

public class PeliculaDAO {

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
