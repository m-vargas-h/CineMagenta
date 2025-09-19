package view;

import model.Pelicula;
import util.PeliculaValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioAgregar extends JFrame {
    private JTextField txtTitulo, txtDirector, txtAnio, txtDuracion;
    private JComboBox<String> comboGenero;
    private JButton btnGuardar, btnLimpiar;

    public FormularioAgregar() {
        setTitle("Agregar Película");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Campos
        add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        add(txtTitulo);

        add(new JLabel("Director:"));
        txtDirector = new JTextField();
        add(txtDirector);

        add(new JLabel("Año:"));
        txtAnio = new JTextField();
        add(txtAnio);

        add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        add(txtDuracion);

        add(new JLabel("Género:"));
        comboGenero = new JComboBox<>(new String[] {
            "Acción", "Comedia", "Drama", "Suspenso", "Terror", "Animación"
        });
        add(comboGenero);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        add(btnGuardar);
        add(btnLimpiar);

        // Acción guardar
        btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                Pelicula p = new Pelicula(
                    0, // El id se asignará automáticamente en la BD
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtAnio.getText()),
                    Integer.parseInt(txtDuracion.getText()),
                    comboGenero.getSelectedItem().toString()
                );

                PeliculaValidator.validar(p);

                // Aquí iría la lógica para insertar en la BD (DAO)
                JOptionPane.showMessageDialog(this, "Película registrada correctamente.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Año y duración deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción limpiar
        btnLimpiar.addActionListener((ActionEvent e) -> {
            txtTitulo.setText("");
            txtDirector.setText("");
            txtAnio.setText("");
            txtDuracion.setText("");
            comboGenero.setSelectedIndex(0);
        });

        setVisible(true);
    }
}