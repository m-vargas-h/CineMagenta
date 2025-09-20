/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Cine Magenta - Cartelera");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Barra de herramientas
        JToolBar toolBar = new JToolBar();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");
        JButton btnBuscar = new JButton("Buscar");

        toolBar.add(btnAgregar);
        toolBar.add(btnModificar);
        toolBar.add(btnEliminar);
        toolBar.add(btnListar);
        toolBar.add(btnBuscar);

        add(toolBar, BorderLayout.NORTH);

        // Acciones (puedes conectar con formularios específicos)
        btnAgregar.addActionListener((ActionEvent e) -> {
            new FormularioAgregar().setVisible(true);
        });

        // Otros botones pueden abrir sus respectivos formularios

        setVisible(true);
    }
}