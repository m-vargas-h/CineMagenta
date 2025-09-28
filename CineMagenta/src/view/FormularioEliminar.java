package view;

import dao.PeliculaDAO;
import model.Pelicula;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class FormularioEliminar extends FormularioBase {

    private JComboBox<String> comboTitulo;
    private JTextArea areaResultado;
    private JButton btnBuscar, btnEliminar, btnLimpiar;

    public FormularioEliminar() {
        super("Eliminar Película");

        comboTitulo = new JComboBox<>(new PeliculaDAO().obtenerTodosLosTitulos().toArray(new String[0]));
        comboTitulo.setEditable(true);

        areaResultado = new JTextArea(5, 30);
        areaResultado.setEditable(false);
        areaResultado.setBorder(BorderFactory.createTitledBorder("Datos de la película"));

        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        agregarCampo("Título a eliminar:", comboTitulo);
        agregarBotones(btnBuscar, btnEliminar, btnLimpiar);

        JPanel panelResultado = new JPanel(new BorderLayout());
        panelResultado.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelResultado.add(new JScrollPane(areaResultado), BorderLayout.CENTER);
        add(panelResultado, BorderLayout.SOUTH);

        btnBuscar.addActionListener(this::buscarPelicula);
        btnEliminar.addActionListener(this::eliminarPelicula);
        btnLimpiar.addActionListener(e -> {
            comboTitulo.setSelectedIndex(-1);
            areaResultado.setText("");
        });

        setVisible(true);
    }

    private void buscarPelicula(ActionEvent e) {
        String titulo = comboTitulo.getSelectedItem().toString().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar o seleccionar un título.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PeliculaDAO dao = new PeliculaDAO();
        Pelicula p = dao.buscarPorTitulo(titulo);

        if (p != null) {
            areaResultado.setText(
                "ID: " + p.getId() + "\n" +
                "Título: " + p.getTitulo() + "\n" +
                "Director: " + p.getDirector() + "\n" +
                "Año: " + p.getAnno() + "\n" +
                "Duración: " + p.getDuracion() + " min\n" +
                "Género: " + p.getGenero()
            );
        } else {
            areaResultado.setText("Película no encontrada.");
        }
    }

    private void eliminarPelicula(ActionEvent e) {
        String titulo = comboTitulo.getSelectedItem().toString().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar o seleccionar un título.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar esta película?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        PeliculaDAO dao = new PeliculaDAO();
        boolean exito = dao.eliminarPorTitulo(titulo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Película eliminada correctamente.");
            comboTitulo.setSelectedIndex(-1);
            areaResultado.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la película.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}