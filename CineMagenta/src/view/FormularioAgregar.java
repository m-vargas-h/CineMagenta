package view;

import model.Pelicula;
import util.PeliculaValidador;
import dao.PeliculaDAO;
import view.FormularioBase;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Ventana de formulario para agregar una nueva película al sistema CineMagenta.
 * Permite ingresar los datos básicos de una película, validarlos y almacenarlos en la base de datos.
 *
 * <p>Incluye campos para título, director, año, duración y género, además de botones para guardar y limpiar.</p>
 *
 * <p>Utiliza {@link PeliculaValidador} para validar los datos ingresados y {@link PeliculaDAO} para persistencia.</p>
 *
 * @author Miguel
 */
public class FormularioAgregar extends FormularioBase {

    private JTextField txtTitulo, txtDirector, txtAnno, txtDuracion;
    private JComboBox<String> comboGenero;
    private JButton btnGuardar, btnLimpiar;

    /**
     * Constructor que inicializa y configura la interfaz del formulario.
     * Define los campos de entrada, botones de acción y sus respectivos listeners.
     */
    public FormularioAgregar() {
        super("Agregar Película");

        // Inicialización de campos
        txtTitulo = new JTextField();
        txtDirector = new JTextField();
        txtAnno = new JTextField();
        txtDuracion = new JTextField();
        comboGenero = new JComboBox<>(new String[] {
            "Acción", "Comedia", "Drama", "Suspenso", "Terror", "Animación"
        });

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");

        // Agregar campos al panel con estilo original
        agregarCampo("Título:", txtTitulo);
        agregarCampo("Director:", txtDirector);
        agregarCampo("Año:", txtAnno);
        agregarCampo("Duración (min):", txtDuracion);
        agregarCampo("Género:", comboGenero);
        agregarBotones(btnGuardar, btnLimpiar);

        // Acción del botón Guardar
        btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                Pelicula p = new Pelicula(
                    0,
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtAnno.getText()),
                    Integer.parseInt(txtDuracion.getText()),
                    comboGenero.getSelectedItem().toString()
                );

                PeliculaValidador.validar(p);

                PeliculaDAO dao = new PeliculaDAO();
                boolean exito = dao.insertar(p);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Película registrada correctamente.");
                    btnLimpiar.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo registrar la película.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Año y duración deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción del botón Limpiar
        btnLimpiar.addActionListener((ActionEvent e) -> {
            txtTitulo.setText("");
            txtDirector.setText("");
            txtAnno.setText("");
            txtDuracion.setText("");
            comboGenero.setSelectedIndex(0);
        });

        setVisible(true);
    }
}