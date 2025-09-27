package view;

import dao.PeliculaDAO;
import model.Pelicula;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Ventana para eliminar una película del sistema CineMagenta.
 * Permite buscar por título, mostrar los datos y confirmar la eliminación.
 * 
 * @author Miguel
 */
public class FormularioEliminar extends JFrame {

    private JTextField txtTitulo;
    private JButton btnBuscar, btnEliminar, btnLimpiar;
    private JTextArea areaResultado;

    public FormularioEliminar() {
        setTitle("Eliminar Película");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel superior con logo
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelLogo.add(crearLogo());
        add(panelLogo, BorderLayout.NORTH);

        // Panel central con campos
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelCampos.add(new JLabel("Título a eliminar:"));
        txtTitulo = new JTextField();
        panelCampos.add(txtTitulo);

        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelCampos.add(btnBuscar);
        panelCampos.add(btnEliminar);
        panelCampos.add(btnLimpiar);
        panelCampos.add(new JLabel()); // espacio vacío

        add(panelCampos, BorderLayout.CENTER);

        // Panel inferior con resultado
        areaResultado = new JTextArea(5, 30);
        areaResultado.setEditable(false);
        areaResultado.setBorder(BorderFactory.createTitledBorder("Datos de la película"));
        add(new JScrollPane(areaResultado), BorderLayout.SOUTH);

        // Listeners
        btnBuscar.addActionListener(this::buscarPelicula);
        btnEliminar.addActionListener(this::eliminarPelicula);
        btnLimpiar.addActionListener(e -> {
            txtTitulo.setText("");
            areaResultado.setText("");
        });

        setVisible(true);
    }

    private void buscarPelicula(ActionEvent e) {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un título.", "Error", JOptionPane.ERROR_MESSAGE);
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
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un título.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta película?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        PeliculaDAO dao = new PeliculaDAO();
        boolean exito = dao.eliminarPorTitulo(titulo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Película eliminada correctamente.");
            txtTitulo.setText("");
            areaResultado.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la película.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel crearLogo() {
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
        if (rawIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar el logo.");
            return new JLabel("Logo no disponible");
        }

        Image img = rawIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel etiqueta = new JLabel(scaledIcon);
        etiqueta.setHorizontalAlignment(JLabel.CENTER);
        return etiqueta;
    }
}