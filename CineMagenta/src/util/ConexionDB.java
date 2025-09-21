/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión con la base de datos MySQL del sistema CineMagenta.
 * Proporciona un método estático para obtener una instancia de {@link Connection} utilizando JDBC.
 * 
 * Esta clase utiliza un constructor privado para evitar instanciación directa.
 * 
 * <p><strong>Base de datos:</strong> Cine_DB</p>
 * <p><strong>Usuario:</strong> root</p>
 * <p><strong>Contraseña:</strong> duoc_2025</p>
 * 
 * @author Miguel
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/Cine_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "duoc_2025";

    /**
     * Constructor privado para evitar instanciación de la clase utilitaria.
     */
    private ConexionDB() {

    }

    /**
     * Establece y devuelve una conexión activa con la base de datos.
     *
     * @return objeto {@link Connection} conectado a la base de datos
     * @throws SQLException si ocurre un error al intentar conectarse
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
    }
}