package view;

import model.*;
import service.PeliculaService;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Formulario gráfico para modificar los datos de una película existente en el sistema CineMagenta.
 * Permite buscar una película por título, visualizar sus datos actuales, editarlos y guardar los cambios.
 * 
 * Esta clase extiende {@link FormularioBase} para reutilizar componentes visuales comunes
 * y utiliza {@link PeliculaService} para validar y actualizar los datos en la base de datos.
 * 
 * @author Miguel
 */
public class FormularioModificar extends FormularioBase {

    private JComboBox<String> comboTituloBuscar;
    private JTextField txtTitulo, txtDirector, txtAnno, txtDuracion;
    private JComboBox<Genero> comboGenero;
    private JButton btnBuscar, btnGuardar, btnLimpiar, btnSeleccionarPortada;
    private String rutaPortadaSeleccionada;
    private Pelicula peliculaActual;

    private final PeliculaService service = new PeliculaService();

    /**
     * Constructor que inicializa los componentes del formulario.
     * Configura el combo de búsqueda, los campos editables, el combo de género y los botones de acción.
     */
    public FormularioModificar() {
        super("Modificar Película");

        comboTituloBuscar = crearComboBusquedaTitulos();
        txtTitulo = crearCampoTexto("Título:");
        txtDirector = crearCampoTexto("Director:");
        txtAnno = crearCampoTexto("Año:");
        txtDuracion = crearCampoTexto("Duración (min):");
        comboGenero = new JComboBox<>(Genero.values());

        btnBuscar = crearBoton("Buscar", this::buscarPelicula);
        btnGuardar = crearBoton("Guardar Cambios", this::guardarCambios);
        btnLimpiar = crearBoton("Limpiar", e -> limpiarCampos());
        btnSeleccionarPortada = crearBoton("Cambiar portada", e -> seleccionarImagen());

        agregarCampo("Título a buscar:", comboTituloBuscar);
        agregarCampo("Género:", comboGenero);
        agregarCampo("Portada:", btnSeleccionarPortada);
        agregarBotones(btnBuscar, btnGuardar, btnLimpiar);

        setVisible(true);
    }

    /**
     * Busca una película por su título seleccionado en el combo.
     * Si se encuentra, carga sus datos en los campos del formulario y muestra la portada.
     * 
     * @param e Evento de acción generado al presionar el botón "Buscar"
     */
    private void buscarPelicula(ActionEvent e) {
        String titulo = comboTituloBuscar.getSelectedItem().toString().trim();
        peliculaActual = service.buscarPorTitulo(titulo);

        if (peliculaActual != null) {
            txtTitulo.setText(peliculaActual.getTitulo());
            txtDirector.setText(peliculaActual.getDirector());
            txtAnno.setText(String.valueOf(peliculaActual.getAnno()));
            txtDuracion.setText(String.valueOf(peliculaActual.getDuracion()));
            comboGenero.setSelectedItem(peliculaActual.getGenero());
            rutaPortadaSeleccionada = peliculaActual.getRutaPortada();
            mostrarMiniatura(rutaPortadaSeleccionada);
        } else {
            limpiarCampos();
        }
    }

    /**
     * Abre un selector de archivos para elegir una nueva imagen de portada desde el disco.
     * Al seleccionar una imagen válida, se actualiza la miniatura en el formulario.
     */
    private void seleccionarImagen() {
        rutaPortadaSeleccionada = seleccionarPortadaDesdeDisco();
        mostrarMiniatura(rutaPortadaSeleccionada);
    }

    /**
     * Valida los campos numéricos y actualiza los datos de la película actual.
     * Si la validación es exitosa, se envía la película al servicio para ser actualizada en la base de datos.
     * Muestra un mensaje de confirmación y limpia los campos si la operación fue exitosa.
     * 
     * @param e Evento de acción generado al presionar el botón "Guardar Cambios"
     */
    private void guardarCambios(ActionEvent e) {
        if (peliculaActual == null || !validarNumeros(txtAnno.getText().trim(), txtDuracion.getText().trim())) return;

        peliculaActual.setTitulo(txtTitulo.getText().trim());
        peliculaActual.setDirector(txtDirector.getText().trim());
        peliculaActual.setAnno(Integer.parseInt(txtAnno.getText().trim()));
        peliculaActual.setDuracion(Integer.parseInt(txtDuracion.getText().trim()));
        Genero generoSeleccionado = (Genero) comboGenero.getSelectedItem();
        peliculaActual.setGenero(generoSeleccionado);
        peliculaActual.setRutaPortada(rutaPortadaSeleccionada);

        if (service.modificarPelicula(peliculaActual)) {
            JOptionPane.showMessageDialog(this, "Película modificada correctamente.");
            limpiarCampos();
            peliculaActual = null;
        }
    }

    /**
     * Limpia todos los campos del formulario, restableciendo el combo de búsqueda y eliminando la miniatura.
     * También reinicia la referencia a la película actual.
     */
    private void limpiarCampos() {
        comboTituloBuscar.setSelectedIndex(-1);
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnno.setText("");
        txtDuracion.setText("");
        comboGenero.setSelectedIndex(0);
        rutaPortadaSeleccionada = null;
        mostrarMiniatura(null);
    }
}