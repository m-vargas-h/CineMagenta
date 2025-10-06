package service;

import dao.PeliculaDAO;
import model.Genero;
import model.Pelicula;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PeliculaServiceTest {

    private PeliculaService service;

    @Before
    public void setUp() {
        PeliculaDAO daoSimulado = new PeliculaDAO() {
            @Override
            public boolean insertar(Pelicula p) { return true; }
            @Override
            public boolean actualizarPelicula(Pelicula p) { return p.getId() > 0; }
            @Override
            public boolean eliminarPorTitulo(String titulo) { return !"inexistente".equalsIgnoreCase(titulo); }
            @Override
            public List<Pelicula> buscarPorTituloParcial(String texto) {
                return List.of(new Pelicula(1, "Matrix", "Wachowski", 1999, 136, Genero.ACCION, "matrix.jpg"));
            }
        };

        service = new PeliculaService(daoSimulado);
    }

    @Test
    public void agregarPelicula_valida_retornaTrue() {
        Pelicula p = new Pelicula(0, "Matrix", "Wachowski", 1999, 136, Genero.ACCION, "matrix.jpg");
        assertTrue(service.agregarPelicula(p));
    }

    @Test
    public void agregarPelicula_invalida_retornaFalse() {
        Pelicula p = new Pelicula(0, "", "Wachowski", 1999, 136, Genero.ACCION, "matrix.jpg");
        assertFalse(service.agregarPelicula(p));
    }

    @Test
    public void modificarPelicula_valida_retornaTrue() {
        Pelicula p = new Pelicula(1, "Matrix", "Wachowski", 1999, 136, Genero.ACCION, "matrix.jpg");
        assertTrue(service.modificarPelicula(p));
    }

    @Test
    public void modificarPelicula_sinId_retornaFalse() {
        Pelicula p = new Pelicula(0, "Matrix", "Wachowski", 1999, 136, Genero.ACCION, "matrix.jpg");
        assertFalse(service.modificarPelicula(p));
    }

    @Test
    public void eliminarPorTitulo_existente_retornaTrue() {
        assertTrue(service.eliminarPorTitulo("Matrix"));
    }

    @Test
    public void eliminarPorTitulo_inexistente_retornaFalse() {
        assertFalse(service.eliminarPorTitulo("inexistente"));
    }

    @Test
    public void buscarPeliculasPorTitulo_retornaResultados() {
        List<Pelicula> resultados = service.buscarPeliculasPorTitulo("Matrix");
        assertEquals(1, resultados.size());
        assertEquals("Matrix", resultados.get(0).getTitulo());
    }
}
