package dao;

import model.*;
import util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO (Data Access Object) para la entidad {@link Pelicula}.
 * Encapsula todas las operaciones de persistencia contra la base de datos.
 *
 * Utiliza JDBC para ejecutar consultas SQL y {@link Logger} para registrar eventos importantes.
 *
 * Métodos principales:
 * - Insertar, actualizar, eliminar películas
 * - Buscar por título exacto o parcial
 * - Listar películas con filtros
 * - Obtener todos los títulos
 *
 * Los errores se registran en logs con nivel SEVERE.
 *
 * @author Miguel
 */
public class PeliculaDAO {

    private static final Logger logger = Logger.getLogger(PeliculaDAO.class.getName());

    /**
     * Inserta una nueva película en la base de datos.
     * Registra el resultado en el log.
     *
     * @param p Película a insertar
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario
     * @throws SQLException si ocurre un error al ejecutar la consulta SQL
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
            logger.info("Película insertada: " + p.getTitulo() + " (" + filas + " fila(s) afectada(s))");
            return filas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar película: " + p.getTitulo(), e);
            throw e;
        }
    }

    /**
     * Verifica si existe una película con el ID especificado en la tabla Cartelera.
     * Registra el resultado en el log.
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
            boolean existe = rs.next();
            logger.info("Verificación de existencia ID=" + id + " → " + existe);
            return existe;
        }
    }

    /**
     * Busca una película por su título exacto.
     * Registra el resultado en el log.
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
                logger.info("Película encontrada: " + titulo);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar película: " + titulo, e);
        }
        return resultado;
    }

    /**
     * Busca películas cuyo título contenga el texto indicado (búsqueda parcial).
     * Registra la cantidad de resultados en el log.
     *
     * @param texto Texto a buscar dentro del título
     * @return Lista de películas que coinciden parcial o totalmente
     */
    public List<Pelicula> buscarPorTituloParcial(String texto) {
        List<Pelicula> resultados = new ArrayList<>();
        String sql = "SELECT * FROM cartelera WHERE LOWER(TITULO) LIKE ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + texto.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pelicula p = new Pelicula(
                        rs.getInt("ID"),
                        rs.getString("TITULO"),
                        rs.getString("DIRECTOR"),
                        rs.getInt("ANNO"),
                        rs.getInt("DURACION"),
                        Genero.desdeEtiqueta(rs.getString("GENERO")),
                        rs.getString("RUTA_PORTADA")
                );
                resultados.add(p);
            }
            logger.info("Búsqueda parcial por título='" + texto + "', resultados=" + resultados.size());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en búsqueda parcial por título: " + texto, e);
        }
        return resultados;
    }

    /**
     * Elimina una película por su título en la tabla Cartelera.
     * Registra el resultado en el log.
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
            logger.info("Película eliminada: " + titulo + " (" + filasAfectadas + " fila(s) afectada(s))");
            return filasAfectadas > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar película: " + titulo, e);
            return false;
        }
    }

    /**
     * Actualiza los datos de una película existente en la tabla Cartelera.
     * Registra el resultado en el log.
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

            int filas = stmt.executeUpdate();
            logger.info("Película actualizada: " + p.getTitulo() + " (" + filas + " fila(s) afectada(s))");
            return filas > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar película: " + p.getTitulo(), e);
            return false;
        }
    }

    /**
     * Obtiene una lista con los títulos de todas las películas en la tabla Cartelera.
     * Registra la cantidad de títulos obtenidos en el log.
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
            logger.info("Se obtuvieron " + titulos.size() + " títulos de películas.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener los títulos de películas", e);
        }
        return titulos;
    }

    /**
     * Obtiene una lista de películas desde la base de datos, con filtros opcionales por género y rango de años.
     * Registra los filtros aplicados y la cantidad de resultados en el log.
     *
     * @param genero     Género a filtrar (puede ser {@code null} para no aplicar filtro)
     * @param annoDesde  Año inicial del rango (puede ser {@code null} para no aplicar filtro)
     * @param annoHasta  Año final del rango (puede ser {@code null} para no aplicar filtro)
     * @return Lista de películas que cumplen con los filtros especificados
     */
    public List<Pelicula> listarPeliculas(Genero genero, Integer annoDesde, Integer annoHasta) {
        List<Pelicula> peliculas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM cartelera WHERE 1=1");

        if (genero != null) sql.append(" AND genero = ?");
        if (annoDesde != null) sql.append(" AND anno >= ?");
        if (annoHasta != null) sql.append(" AND anno <= ?");
        sql.append(" ORDER BY titulo ASC");

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (genero != null) stmt.setString(index++, genero.getEtiqueta());
            if (annoDesde != null) stmt.setInt(index++, annoDesde);
            if (annoHasta != null) stmt.setInt(index++, annoHasta);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pelicula p = new Pelicula(
                        rs.getInt("ID"),
                        rs.getString("TITULO"),
                        rs.getString("DIRECTOR"),
                        rs.getInt("ANNO"),
                        rs.getInt("DURACION"),
                        Genero.desdeEtiqueta(rs.getString("GENERO")),
                        rs.getString("RUTA_PORTADA")
                );
                peliculas.add(p);
            }
            logger.info("Listado de películas con filtros: genero=" + genero + ", desde=" + annoDesde + ", hasta=" + annoHasta + " → resultados=" + peliculas.size());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar películas", e);
        }
        return peliculas;
    }
}