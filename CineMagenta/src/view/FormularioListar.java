package view;

import model.Genero;
import model.Pelicula;
import service.PeliculaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Formulario para listar películas en un JTable con filtros por género y rango de años.
 * Extiende {@link FormularioBase} para mantener consistencia visual.
 * 
 * Características:
 * - Permite filtrar por género y rango de años.
 * - Permite ordenar las columnas haciendo clic en los encabezados.
 * - Muestra la portada de la película seleccionada en el panel derecho.
 * - La tabla no incluye la columna de ruta de portada (se maneja internamente).
 * 
 * @author Miguel
 */
public class FormularioListar extends FormularioBase {

    private JComboBox<Genero> comboGenero;
    private JTextField txtAnnoDesde, txtAnnoHasta;
    private JButton btnBuscar, btnMostrarTodo;
    private JTable tablaPeliculas;
    private DefaultTableModel modeloTabla;
    private final PeliculaService service = new PeliculaService();
    private List<Pelicula> peliculasActuales; // lista en memoria para acceder a la portada

    /**
     * Constructor que inicializa el formulario de listado.
     */
    public FormularioListar() {
        super("Listar Películas");

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout());
        comboGenero = new JComboBox<>(Genero.values());
        comboGenero.insertItemAt(null, 0); // opción "Todos"
        comboGenero.setSelectedIndex(0);

        txtAnnoDesde = new JTextField(5);
        txtAnnoHasta = new JTextField(5);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::buscarPeliculas);

        btnMostrarTodo = new JButton("Mostrar Todo");
        btnMostrarTodo.addActionListener(e -> cargarPeliculas(null, null, null));

        panelFiltros.add(new JLabel("Género:"));
        panelFiltros.add(comboGenero);
        panelFiltros.add(new JLabel("Año desde:"));
        panelFiltros.add(txtAnnoDesde);
        panelFiltros.add(new JLabel("Año hasta:"));
        panelFiltros.add(txtAnnoHasta);
        panelFiltros.add(btnBuscar);
        panelFiltros.add(btnMostrarTodo);

        // Tabla (sin columna de portada)
        String[] columnas = {"ID", "Título", "Director", "Año", "Duración", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPeliculas = new JTable(modeloTabla);

        // Habilitar ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaPeliculas.setRowSorter(sorter);

        JScrollPane scrollTabla = new JScrollPane(tablaPeliculas);

        // Panel central con filtros + tabla
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelFiltros, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        agregarPanelCompleto(panelCentral);

        // Cargar todas las películas al inicio
        cargarPeliculas(null, null, null);

        // Listener para mostrar portada
        tablaPeliculas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPeliculas.getSelectedRow() != -1) {
                int fila = tablaPeliculas.convertRowIndexToModel(tablaPeliculas.getSelectedRow());
                Pelicula seleccionada = peliculasActuales.get(fila);
                mostrarMiniatura(seleccionada.getRutaPortada());
            }
        });
    }

    private void buscarPeliculas(ActionEvent e) {
        Genero genero = (Genero) comboGenero.getSelectedItem();
        Integer annoDesde = parseEntero(txtAnnoDesde.getText());
        Integer annoHasta = parseEntero(txtAnnoHasta.getText());
        cargarPeliculas(genero, annoDesde, annoHasta);
    }

    private void cargarPeliculas(Genero genero, Integer annoDesde, Integer annoHasta) {
        modeloTabla.setRowCount(0);
        peliculasActuales = service.listarPeliculas(genero, annoDesde, annoHasta);

        for (Pelicula p : peliculasActuales) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getTitulo(),
                p.getDirector(),
                p.getAnno(),
                p.getDuracion(),
                p.getGenero().getEtiqueta()
            });
        }
    }

    private Integer parseEntero(String texto) {
        try {
            return (texto == null || texto.isBlank()) ? null : Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en los campos de año.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    protected String obtenerInstrucciones() {
        return "Visualiza todas las películas registradas en el sistema.\n"
             + "Puedes filtrar por género y rango de años.\n"
             + "También puedes ordenar las columnas haciendo clic en sus encabezados.";
    }
}