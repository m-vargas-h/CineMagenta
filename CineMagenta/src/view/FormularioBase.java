package view;

import dao.PeliculaDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Clase abstracta que define la estructura base para los formularios del sistema CineMagenta.
 * Proporciona métodos reutilizables para crear campos de texto, botones, combos y paneles,
 * así como para mostrar miniaturas de portadas seleccionadas.
 * 
 * Las clases concretas como {@link FormularioAgregar}, {@link FormularioModificar} y {@link FormularioEliminar}
 * extienden esta clase para mantener una interfaz gráfica consistente y modular.
 * 
 * @author Miguel
 */
public abstract class FormularioBase extends JFrame {

    protected JPanel panelCampos;
    protected JLabel lblMiniatura;

    /**
     * Constructor que inicializa el formulario con el título especificado.
     * Configura el layout principal, el panel de campos y la zona de miniatura.
     * 
     * @param tituloVentana Título de la ventana del formulario
     */
    public FormularioBase(String tituloVentana) {
        setTitle(tituloVentana);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        construirEncabezado();
        inicializarFormulario();
    }

    private void construirEncabezado() {
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Logo a la izquierda
        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(120, 180));
        lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
        lblLogo.setIcon(cargarLogo());

        // Panel central con título e instrucciones
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel lblTitulo = new JLabel(getTitle());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea txtInstrucciones = new JTextArea(obtenerInstrucciones());
        txtInstrucciones.setFont(new Font("Arial", Font.PLAIN, 14));
        txtInstrucciones.setLineWrap(true);
        txtInstrucciones.setWrapStyleWord(true);
        txtInstrucciones.setEditable(false);
        txtInstrucciones.setOpaque(false);
        txtInstrucciones.setBorder(null);
        txtInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtInstrucciones.setMaximumSize(new Dimension(400, 100)); // Ajusta según tu layout

        panelCentro.add(lblTitulo);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(txtInstrucciones);

        // Miniatura a la derecha
        lblMiniatura = new JLabel();
        lblMiniatura.setPreferredSize(new Dimension(120, 180));
        lblMiniatura.setHorizontalAlignment(SwingConstants.RIGHT);
        lblMiniatura.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelEncabezado.add(lblLogo, BorderLayout.WEST);
        panelEncabezado.add(panelCentro, BorderLayout.CENTER);
        panelEncabezado.add(lblMiniatura, BorderLayout.EAST);

        add(panelEncabezado, BorderLayout.NORTH);
    }

    private void inicializarFormulario() {
        panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setBorder(new EmptyBorder(10, 30, 10, 30));
        add(panelCampos, BorderLayout.CENTER);
    }

    /**
     * Agrega un campo al panel principal del formulario, compuesto por una etiqueta y un componente.
     * 
     * @param etiqueta Texto descriptivo del campo
     * @param campo Componente gráfico (campo de texto, combo, etc.)
     */
    protected void agregarCampo(String etiqueta, JComponent campo) {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
        JLabel lbl = new JLabel(etiqueta);
        lbl.setPreferredSize(new Dimension(150, 30));
        campo.setPreferredSize(new Dimension(300, 30));
        fila.add(lbl, BorderLayout.WEST);
        fila.add(campo, BorderLayout.CENTER);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panelCampos.add(fila);
        panelCampos.add(Box.createVerticalStrut(10));
    }

    /**
     * Agrega un conjunto de botones al panel principal del formulario.
     * 
     * @param botones Botones a agregar
     */
    protected void agregarBotones(JButton... botones) {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        for (JButton boton : botones) {
            panelBotones.add(boton);
        }
        add(panelBotones, BorderLayout.SOUTH);
    }

    protected void agregarPanelCompleto(JComponent panel) {
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        panelCampos.add(panel);
        panelCampos.add(Box.createVerticalStrut(10));
    }

    protected JTextField crearCampoTexto(String etiqueta) {
        JTextField campo = new JTextField();
        agregarCampo(etiqueta, campo);
        return campo;
    }

    protected JComboBox<String> crearComboGenero() {
        String[] generos = {
            "Acción", "Comedia", "Drama", "Ciencia Ficción", "Terror",
            "Romance", "Musical", "Documental", "Animación"
        };
        JComboBox<String> combo = new JComboBox<>(generos);
        combo.setSelectedIndex(0);
        return combo;
    }

    protected JComboBox<String> crearComboBusquedaTitulos() {
        JComboBox<String> combo = new JComboBox<>(new PeliculaDAO().obtenerTodosLosTitulos().toArray(new String[0]));
        combo.setEditable(true);
        return combo;
    }

    /**
     * Crea un botón con texto y una acción asociada.
     * 
     * @param texto Texto del botón
     * @param listener Acción a ejecutar al presionar el botón
     * @return Botón creado
     */
    protected JButton crearBoton(String texto, ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.addActionListener(listener);
        return boton;
    }

    protected boolean validarNumeros(String anno, String duracion) {
        try {
            Integer.parseInt(anno);
            Integer.parseInt(duracion);
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Año y duración deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    protected String seleccionarPortadaDesdeDisco() {
        File carpetaPortadas = new File("docs/portadas");
        carpetaPortadas.mkdirs();

        JFileChooser chooser = new JFileChooser(carpetaPortadas);
        chooser.setDialogTitle("Seleccionar portada");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            String ruta = "docs/portadas/" + archivo.getName();
            try {
                Files.copy(archivo.toPath(), new File(ruta).toPath(), StandardCopyOption.REPLACE_EXISTING);
                return ruta;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al copiar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    /**
     * Muestra una imagen en miniatura en el panel lateral del formulario.
     * 
     * @param ruta Ruta del archivo de imagen a mostrar
     */
    protected void mostrarMiniatura(String ruta) {
        String rutaFinal = (ruta != null && !ruta.isEmpty()) ? ruta : "docs/portadas/default.jpg";
        File archivo = new File(rutaFinal);

        if (archivo.exists()) {
            ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            lblMiniatura.setIcon(new ImageIcon(img));
        } else {
            lblMiniatura.setIcon(new ImageIcon("docs/portadas/default.jpg"));
        }
    }

    protected ImageIcon cargarLogo() {
        try {
            ImageIcon rawIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));

            // Obtener dimensiones del JLabel destino
            int ancho = 130;
            int alto = 130;

            Image img = rawIcon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
            return null;
        }
    }

    protected String obtenerInstrucciones() {
        if (getTitle().contains("Agregar")) {
            return "Completa los campos obligatorios como título, director, año y duración, "
                + "luego selecciona una portada desde tu equipo y verifica los datos ingresados.\n"
                + "El nombre de la portada no debe contener espacios para evitar fallas en la base"
                + " de datos y debe copiarse previamente en la carpeta 'docs/portadas'.\n";
        } else if (getTitle().contains("Modificar")) {
            return "Selecciona o escribe el título de una película existente.\n"
                + "Edita los campos que desees actualizar y cambia la portada si lo necesitas.\n"
                + "Haz clic en 'Guardar Cambios' para aplicar las modificaciones.";
        } else if (getTitle().contains("Eliminar")) {
            return "Selecciona o escribe el título de una película que deseas eliminar.\n"
                + "Verifica la portada y confirma que es la película correcta.\n"
                + "Haz clic en 'Eliminar' y acepta la confirmación para borrarla.";
        }
        return "";
    }
}