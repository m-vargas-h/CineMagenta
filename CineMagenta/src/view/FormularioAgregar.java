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

        btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                Pelicula p = new Pelicula(
                    0, // El ID se autogenera
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtAnio.getText()),
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