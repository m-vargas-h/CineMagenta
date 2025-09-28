package view;

import javax.swing.*;
import java.awt.*;

public abstract class FormularioBase extends JFrame {

    protected JPanel panelFormulario;

    public FormularioBase(String tituloVentana) {
        setTitle(tituloVentana);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        agregarLogo();
        inicializarFormulario();
    }

    protected void agregarLogo() {
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel logo = crearLogo();
        panelLogo.add(logo);
        add(panelLogo, BorderLayout.NORTH);
    }

    protected JLabel crearLogo() {
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/resources/logo.png"));
        if (rawIcon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar el logo.");
            return new JLabel("Logo no disponible");
        }
        Image img = rawIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel etiqueta = new JLabel(scaledIcon);
        etiqueta.setHorizontalAlignment(JLabel.CENTER);
        return etiqueta;
    }

    protected void inicializarFormulario() {
        panelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelFormulario, BorderLayout.CENTER);
    }

    protected void agregarCampo(String etiqueta, JComponent campo) {
        JLabel lbl = new JLabel(etiqueta);
        aplicarEstilo(lbl);
        aplicarEstilo(campo);
        panelFormulario.add(lbl);
        panelFormulario.add(campo);
    }

    protected void agregarBotones(JButton... botones) {
        for (JButton b : botones) aplicarEstilo(b);
        for (JButton b : botones) panelFormulario.add(b);
    }

    protected void aplicarEstilo(JComponent comp) {
        comp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        if (comp instanceof JTextField || comp instanceof JComboBox) {
            comp.setBackground(Color.WHITE);
        }
    }
}