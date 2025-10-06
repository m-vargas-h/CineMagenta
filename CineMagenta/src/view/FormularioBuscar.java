package view;

import model.Pelicula;
import service.PeliculaService;
import util.DialogUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Formulario para buscar películas por nombre o fracción de nombre.
 * Extiende {@link FormularioBase} para mantener consistencia visual.
 * 
 * Características:
 * - Permite ingresar un texto de búsqueda (título parcial).
 * - Muestra en una tabla todas las coincidencias encontradas.
 * - Permite ordenar las columnas haciendo clic en los encabezados.
 * - Muestra la portada de la película seleccionada en el panel derecho.
 * - La tabla no incluye la columna de ruta de portada (se maneja internamente).
 * 
 * Los mensajes de validación y resultados se muestran mediante {@link DialogUtils}.
 * 
 * @author Miguel
 */
public class FormularioBuscar extends FormularioBase {

    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private final PeliculaService service = new PeliculaService();
    private List<Pelicula> resultadosActuales; // lista en memoria para acceder a la portada

    public FormularioBuscar() {
        super("Buscar Películas");

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        txtBusqueda = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::buscarPeliculas);

        panelBusqueda.add(new JLabel("Título o parte del título:"));
        panelBusqueda.add(txtBusqueda);
        panelBusqueda.add(btnBuscar);

        // Tabla (sin columna de portada)
        String[] columnas = {"ID", "Título", "Director", "Año", "Duración", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);

        // Habilitar ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaResultados.setRowSorter(sorter);

        JScrollPane scrollTabla = new JScrollPane(tablaResultados);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(panelBusqueda, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        agregarPanelCompleto(panelCentral);

        // Listener para mostrar portada
        tablaResultados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaResultados.getSelectedRow() != -1) {
                int fila = tablaResultados.convertRowIndexToModel(tablaResultados.getSelectedRow());
                Pelicula seleccionada = resultadosActuales.get(fila);
                mostrarMiniatura(seleccionada.getRutaPortada());
            }
        });
    }

    /**
     * Ejecuta la búsqueda de películas según el texto ingresado.
     * Muestra advertencias si el campo está vacío y resultados informativos si no hay coincidencias.
     * 
     * @param e Evento de acción generado al presionar el botón "Buscar"
     */
    private void buscarPeliculas(ActionEvent e) {
        String texto = txtBusqueda.getText().trim();
        modeloTabla.setRowCount(0);

        if (texto.isEmpty()) {
            DialogUtils.warning("Ingrese un texto para buscar.");
            return;
        }

        resultadosActuales = service.buscarPeliculasPorTitulo(texto);

        if (resultadosActuales.isEmpty()) {
            DialogUtils.info("No se encontraron películas con ese criterio.");
        } else {
            for (Pelicula p : resultadosActuales) {
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
    }

    @Override
    protected String obtenerInstrucciones() {
        return "Escribe el nombre o parte del nombre de una película, luego "
             + "haz clic en 'Buscar' para ver todas las coincidencias.\n"
             + "Selecciona una fila para ver la portada en el panel derecho.";
    }
}