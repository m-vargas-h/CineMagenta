/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
import util.VentanaManager;

/**
 * Ventana principal del sistema CineMagenta.
 * Presenta una barra de herramientas con opciones para gestionar la cartelera de películas,
 * incluyendo agregar, modificar, eliminar, listar y buscar.
 * 
 * <p>Esta clase extiende {@link JFrame} y sirve como punto de entrada visual para el usuario.</p>
 * 
 * <p>Actualmente, el botón "Agregar" abre el formulario {@link FormularioAgregar}.
 * Los demás botones están disponibles para futuras extensiones.</p>
 * 
 * @author Miguel
 */
public class MainFrame extends JFrame {

    /**
     * Constructor que inicializa la interfaz principal del sistema.
     * Configura la ventana, la barra de herramientas y las acciones de los botones.
     */
    public MainFrame() {
        setTitle("Cine Magenta - Cartelera");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Asegura distribución por regiones


        // Barra de herramientas
        JToolBar toolBar = new JToolBar();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");
        JButton btnBuscar = new JButton("Buscar");

        // Estilo de botones
        Font botonFont = new Font("SansSerif", Font.BOLD, 20);
        btnAgregar.setFont(botonFont);
        btnModificar.setFont(botonFont);
        btnEliminar.setFont(botonFont);
        btnListar.setFont(botonFont);
        btnBuscar.setFont(botonFont);

        toolBar.add(btnAgregar);
        toolBar.add(btnModificar);
        toolBar.add(btnEliminar);
        toolBar.add(btnListar);
        toolBar.add(btnBuscar);

        add(toolBar, BorderLayout.NORTH);
        add(crearBanner(), BorderLayout.CENTER);
        
        /**
         * Acción del botón "Agregar".
         * Abre el formulario {@link FormularioAgregar} para registrar una nueva película.
         */
        btnAgregar.addActionListener(e -> {
            VentanaManager.abrirVentana(FormularioAgregar.class, FormularioAgregar::new);
        });

        /**
         * Acción del botón "Eliminar".
         * Abre el formulario {@link FormularioEliminar} para eliminar una película existente.
         */
        btnEliminar.addActionListener(e -> {
            VentanaManager.abrirVentana(FormularioEliminar.class, FormularioEliminar::new);
        });

        /**
         * Acción del botón "Modificar".
         * Abre el formulario {@link FormularioModificar} para editar los datos de una película.
         */
        btnModificar.addActionListener(e -> {
            VentanaManager.abrirVentana(FormularioModificar.class, FormularioModificar::new);
        });

        //TODO: En futuras versiones otros botones tendrán sus respectivas acciones

        setVisible(true);
    }

    /**
     * Crea un JLabel con el banner cargado desde recursos.
     * Escala la imagen para ajustarse al ancho de la ventana.
     *
     * @return JLabel con el banner visual
     */
    private JLabel crearBanner() {
        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/resources/banner.png"));
        Image img = rawIcon.getImage().getScaledInstance(800, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel etiqueta = new JLabel(scaledIcon);
        etiqueta.setHorizontalAlignment(JLabel.CENTER);
        return etiqueta;
    }
}