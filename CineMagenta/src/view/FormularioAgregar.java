/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.Pelicula;
import util.PeliculaValidador;
import dao.PeliculaDAO;

import javax.swing.*;
import java.awt.*;
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
public class FormularioAgregar extends JFrame {

    private JTextField txtTitulo, txtDirector, txtAnno, txtDuracion;
    private JComboBox<String> comboGenero;
    private JButton btnGuardar, btnLimpiar;

    /**
     * Constructor que inicializa y configura la interfaz del formulario.
     * Define los campos de entrada, botones de acción y sus respectivos listeners.
     */
    public FormularioAgregar() {

        setTitle("Agregar Película");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel superior con el logo centrado
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelLogo.add(crearLogo());
        add(panelLogo, BorderLayout.NORTH);

        // Panel central con los campos y botones
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos
        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Director:"));
        txtDirector = new JTextField();
        panelFormulario.add(txtDirector);

        panelFormulario.add(new JLabel("Año:"));
        txtAnno = new JTextField();
        panelFormulario.add(txtAnno);

        panelFormulario.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        panelFormulario.add(txtDuracion);

        panelFormulario.add(new JLabel("Género:"));
        comboGenero = new JComboBox<>(new String[] {
            "Acción", "Comedia", "Drama", "Suspenso", "Terror", "Animación"
        });
        panelFormulario.add(comboGenero);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnLimpiar);

        add(panelFormulario, BorderLayout.CENTER);

        /**
         * Acción del botón Guardar.
         * Crea una instancia de {@link Pelicula}, la valida y la inserta en la base de datos.
         * Muestra mensajes de éxito o error según el resultado.
         */
        btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                Pelicula p = new Pelicula(
                    0, // El ID se autogenera
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
                    btnLimpiar.doClick(); // Limpia los campos después de guardar
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

        /**
         * Acción del botón Limpiar.
         * Restablece todos los campos del formulario a sus valores por defecto.
         */
        btnLimpiar.addActionListener((ActionEvent e) -> {
            txtTitulo.setText("");
            txtDirector.setText("");
            txtAnno.setText("");
            txtDuracion.setText("");
            comboGenero.setSelectedIndex(0);
        });

        setVisible(true);
    }

    /**
     * Crea un JLabel con el logo cargado desde recursos.
     * Escala la imagen para ajustarse al ancho del formulario.
     *
     * @return JLabel con el logo visual
     */
    private JLabel crearLogo() {
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
        
        if (rawIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar el logo. Verifica la ruta y el archivo.");
            return new JLabel("Logo no disponible");
        }

        Image img = rawIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel etiqueta = new JLabel(scaledIcon);
        etiqueta.setHorizontalAlignment(JLabel.CENTER);
        return etiqueta;
    }
}