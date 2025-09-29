package view;

import model.Pelicula;
import service.PeliculaService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Formulario gráfico para eliminar películas del sistema CineMagenta.
 * Permite buscar una película por título, visualizar sus datos y confirmar su eliminación.
 * 
 * Esta clase extiende {@link FormularioBase} para reutilizar componentes visuales comunes
 * y utiliza {@link PeliculaService} para gestionar la operación de borrado en la base de datos.
 * 
 * Muestra información relevante de la película seleccionada, incluyendo director, año, duración, género y portada.
 * 
 * @author Miguel
 */
public class FormularioEliminar extends FormularioBase {

    private JComboBox<String> comboTituloBuscar;
    private JButton btnBuscar, btnEliminar, btnLimpiar;
    private JLabel lblDirector, lblAnno, lblDuracion, lblGenero;
    private Pelicula peliculaActual;

    private final PeliculaService service = new PeliculaService();

    /**
     * Constructor que inicializa los componentes del formulario.
     * Configura el combo de búsqueda, el panel informativo y los botones de acción.
     */
    public FormularioEliminar() {
        super("Eliminar Película");

        // Campo de búsqueda
        comboTituloBuscar = crearComboBusquedaTitulos();
        comboTituloBuscar.setPreferredSize(new Dimension(300, 30));
        agregarCampo("Título a eliminar:", comboTituloBuscar);

        // Panel informativo
        JPanel contenedorInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contenedorInfo.add(construirPanelInformacion());
        agregarPanelCompleto(construirPanelInformacion());

        // Botones
        btnBuscar = crearBoton("Buscar", this::buscarPelicula);
        btnEliminar = crearBoton("Eliminar", this::eliminarPelicula);
        btnLimpiar = crearBoton("Limpiar", e -> limpiarCampos());
        agregarBotones(btnBuscar, btnEliminar, btnLimpiar);

        setVisible(true);
    }

    /**
     * Construye el panel informativo que muestra los datos de la película seleccionada.
     * Incluye etiquetas para director, año, duración y género.
     * 
     * @return Panel con la información estructurada
     */
    private JPanel construirPanelInformacion() {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        Font fuenteInfo = new Font("Arial", Font.PLAIN, 16);

        lblDirector = new JLabel("Director: ");
        lblDirector.setFont(fuenteInfo);
        lblDirector.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblAnno = new JLabel("Año: ");
        lblAnno.setFont(fuenteInfo);
        lblAnno.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblDuracion = new JLabel("Duración: ");
        lblDuracion.setFont(fuenteInfo);
        lblDuracion.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblGenero = new JLabel("Género: ");
        lblGenero.setFont(fuenteInfo);
        lblGenero.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelInfo.add(lblDirector);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(lblAnno);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(lblDuracion);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(lblGenero);

        return panelInfo;
    }

    /**
     * Busca una película por su título seleccionado en el combo.
     * Si se encuentra, muestra sus datos en el panel informativo y carga la portada.
     * 
     * @param e Evento de acción generado al presionar el botón "Buscar"
     */
    private void buscarPelicula(ActionEvent e) {
        String titulo = comboTituloBuscar.getSelectedItem().toString().trim();
        peliculaActual = service.buscarPorTitulo(titulo);

        if (peliculaActual != null) {
            lblDirector.setText("Director: " + peliculaActual.getDirector());
            lblAnno.setText("Año: " + peliculaActual.getAnno());
            lblDuracion.setText("Duración: " + peliculaActual.getDuracion() + " min");
            lblGenero.setText("Género: " + peliculaActual.getGenero().getEtiqueta());
            mostrarMiniatura(peliculaActual.getRutaPortada());
        } else {
            limpiarCampos();
        }
    }

    /**
     * Elimina la película actualmente seleccionada tras confirmar la acción con el usuario.
     * Si la operación es exitosa, muestra un mensaje y limpia los campos del formulario.
     * 
     * @param e Evento de acción generado al presionar el botón "Eliminar"
     */
    private void eliminarPelicula(ActionEvent e) {
        if (peliculaActual == null) return;

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas eliminar esta película?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (service.eliminarPorTitulo(peliculaActual.getTitulo())) {
                JOptionPane.showMessageDialog(this, "Película eliminada correctamente.");
                limpiarCampos();
                peliculaActual = null;
            }
        }
    }

    
    /**
     * Limpia todos los campos del formulario, restableciendo el combo de búsqueda,
     * eliminando la miniatura y reiniciando el panel informativo.
     */
    private void limpiarCampos() {
        comboTituloBuscar.setSelectedIndex(-1);
        mostrarMiniatura(null);
        peliculaActual = null;

        lblDirector.setText("Director: ");
        lblAnno.setText("Año: ");
        lblDuracion.setText("Duración: ");
        lblGenero.setText("Género: ");
    }
}