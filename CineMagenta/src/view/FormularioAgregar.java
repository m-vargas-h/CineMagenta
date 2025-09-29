package view;

import model.Genero;
import model.Pelicula;
import service.PeliculaService;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Formulario gráfico para agregar nuevas películas al sistema CineMagenta.
 * Permite ingresar título, director, año, duración, género y seleccionar una portada desde disco.
 * 
 * Esta clase extiende {@link FormularioBase} para reutilizar componentes visuales comunes
 * y utiliza {@link PeliculaService} para validar e insertar los datos en la base de datos.
 * 
 * @author Miguel
 */
public class FormularioAgregar extends FormularioBase {

    private JTextField txtTitulo, txtDirector, txtAnno, txtDuracion;
    private JComboBox<Genero> comboGenero;
    private JButton btnGuardar, btnLimpiar, btnSeleccionarPortada;
    private String rutaPortadaSeleccionada;

    private final PeliculaService service = new PeliculaService();

    /**
     * Constructor que inicializa los campos del formulario y configura los botones de acción.
     * Crea campos de texto, combo de género, botón para seleccionar portada y botones para guardar o limpiar.
     */
    public FormularioAgregar() {
        super("Agregar Película");

        txtTitulo = crearCampoTexto("Título:");
        txtDirector = crearCampoTexto("Director:");
        txtAnno = crearCampoTexto("Año:");
        txtDuracion = crearCampoTexto("Duración (min):");
        comboGenero = new JComboBox<>(Genero.values());

        btnSeleccionarPortada = crearBoton("Seleccionar portada", e -> seleccionarImagen());
        btnGuardar = crearBoton("Guardar", this::guardarPelicula);
        btnLimpiar = crearBoton("Limpiar", e -> limpiarCampos());

        agregarCampo("Género:", comboGenero);
        agregarCampo("Portada:", btnSeleccionarPortada);
        agregarBotones(btnGuardar, btnLimpiar);

        setVisible(true);
    }

    /**
     * Abre un selector de archivos para elegir una imagen de portada desde el disco.
     * Al seleccionar una imagen válida, se muestra una miniatura en el formulario.
     */
    private void seleccionarImagen() {
        rutaPortadaSeleccionada = seleccionarPortadaDesdeDisco();
        mostrarMiniatura(rutaPortadaSeleccionada);
    }

    /**
     * Valida los campos numéricos y crea una nueva instancia de {@link Pelicula} con los datos ingresados.
     * Si la validación es exitosa, se envía la película al servicio para ser insertada en la base de datos.
     * Muestra un mensaje de confirmación y limpia los campos si la operación fue exitosa.
     * 
     * @param e Evento de acción generado al presionar el botón "Guardar"
     */
    private void guardarPelicula(ActionEvent e) {
        if (!validarNumeros(txtAnno.getText().trim(), txtDuracion.getText().trim())) return;

        Genero generoSeleccionado = (Genero) comboGenero.getSelectedItem();

        Pelicula p = new Pelicula(
            0,
            txtTitulo.getText().trim(),
            txtDirector.getText().trim(),
            Integer.parseInt(txtAnno.getText().trim()),
            Integer.parseInt(txtDuracion.getText().trim()),
            generoSeleccionado,
            rutaPortadaSeleccionada
        );

        if (service.agregarPelicula(p)) {
            JOptionPane.showMessageDialog(this, "Película agregada correctamente.");
            limpiarCampos();
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnno.setText("");
        txtDuracion.setText("");
        comboGenero.setSelectedIndex(0);
        rutaPortadaSeleccionada = null;
        mostrarMiniatura(null);
    }
}