package view;

import dao.PeliculaDAO;
import model.Pelicula;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Ventana para modificar los datos de una película en el sistema CineMagenta.
 * Permite buscar por título, editar los campos y guardar los cambios.
 * 
 * @author Miguel
 */
public class FormularioModificar extends JFrame {

    private JTextField txtTituloBuscar, txtTitulo, txtDirector, txtAnno, txtDuracion;
    private JComboBox<String> comboGenero;
    private JButton btnBuscar, btnGuardar, btnLimpiar;

    private Pelicula peliculaActual;

    public FormularioModificar() {
        setTitle("Modificar Película");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel superior con logo
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelLogo.add(crearLogo());
        add(panelLogo, BorderLayout.NORTH);

        // Panel central con campos
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        panelFormulario.add(new JLabel("Título a buscar:"));
        txtTituloBuscar = new JTextField();
        panelFormulario.add(txtTituloBuscar);

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
            "Acción", "Comedia", "Drama", "Ciencia Ficción", "Terror", "Romance", "Musical", "Documental", "Animación"
        });
        panelFormulario.add(comboGenero);

        btnBuscar = new JButton("Buscar");
        btnGuardar = new JButton("Guardar Cambios");
        btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(btnBuscar);
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnLimpiar);
        panelFormulario.add(new JLabel()); // espacio vacío

        add(panelFormulario, BorderLayout.CENTER);

        // Listeners
        btnBuscar.addActionListener(this::buscarPelicula);
        btnGuardar.addActionListener(this::guardarCambios);
        btnLimpiar.addActionListener(e -> limpiarCampos());

        setVisible(true);
    }

    private void buscarPelicula(ActionEvent e) {
        String titulo = txtTituloBuscar.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un título para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
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
        txtTituloBuscar.setText("");
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnno.setText("");
        txtDuracion.setText("");
        comboGenero.setSelectedIndex(0);
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