/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mvarg
 */

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/Cine_DB";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Ajusta según tu configuración

    private ConexionDB() {
        // Constructor privado para evitar instanciación
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
    }
}