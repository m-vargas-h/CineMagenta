package view;

import dao.PeliculaDAO;
import model.Pelicula;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FormularioModificar extends FormularioBase {

    private JComboBox<String> comboTituloBuscar;
    private JTextField txtTitulo, txtDirector, txtAnno, txtDuracion;
    private JComboBox<String> comboGenero;
    private JButton btnBuscar, btnGuardar, btnLimpiar;

    private Pelicula peliculaActual;

    public FormularioModificar() {
        super("Modificar Película");

        comboTituloBuscar = new JComboBox<>(new PeliculaDAO().obtenerTodosLosTitulos().toArray(new String[0]));
        comboTituloBuscar.setEditable(true);

        txtTitulo = new JTextField();
        txtDirector = new JTextField();
        txtAnno = new JTextField();
        txtDuracion = new JTextField();
        comboGenero = new JComboBox<>(new String[] {
            "Acción", "Comedia", "Drama", "Ciencia Ficción", "Terror", "Romance", "Musical", "Documental", "Animación"
        });

        btnBuscar = new JButton("Buscar");
        btnGuardar = new JButton("Guardar Cambios");
        btnLimpiar = new JButton("Limpiar");

        agregarCampo("Título a buscar:", comboTituloBuscar);
        agregarCampo("Título:", txtTitulo);
        agregarCampo("Director:", txtDirector);
        agregarCampo("Año:", txtAnno);
        agregarCampo("Duración (min):", txtDuracion);
        agregarCampo("Género:", comboGenero);
        agregarBotones(btnBuscar, btnGuardar, btnLimpiar);

        btnBuscar.addActionListener(this::buscarPelicula);
        btnGuardar.addActionListener(this::guardarCambios);
        btnLimpiar.addActionListener(e -> limpiarCampos());

        setVisible(true);
    }

    private void buscarPelicula(ActionEvent e) {
        String titulo = comboTituloBuscar.getSelectedItem().toString().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar o seleccionar un título.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PeliculaDAO dao = new PeliculaDAO();
        peliculaActual = dao.buscarPorTitulo(titulo);

        if (peliculaActual != null) {
            txtTitulo.setText(peliculaActual.getTitulo());
            txtDirector.setText(peliculaActual.getDirector());
            txtAnno.setText(String.valueOf(peliculaActual.getAnno()));
            txtDuracion.setText(String.valueOf(peliculaActual.getDuracion()));
            comboGenero.setSelectedItem(peliculaActual.getGenero());
        } else {
            JOptionPane.showMessageDialog(this, "Película no encontrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        }
    }

    private void guardarCambios(ActionEvent e) {
        if (peliculaActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debes buscar una película.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            peliculaActual.setTitulo(txtTitulo.getText().trim());
            peliculaActual.setDirector(txtDirector.getText().trim());
            peliculaActual.setAnno(Integer.parseInt(txtAnno.getText().trim()));
            peliculaActual.setDuracion(Integer.parseInt(txtDuracion.getText().trim()));
            peliculaActual.setGenero((String) comboGenero.getSelectedItem());

            PeliculaDAO dao = new PeliculaDAO();
            boolean exito = dao.actualizarPelicula(peliculaActual);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Película modificada correctamente.");
                limpiarCampos();
                peliculaActual = null;
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo modificar la película.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Año y duración deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        comboTituloBuscar.setSelectedIndex(-1);
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnno.setText("");
        txtDuracion.setText("");
        comboGenero.setSelectedIndex(0);
    }
}