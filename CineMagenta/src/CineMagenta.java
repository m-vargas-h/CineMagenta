/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import view.MainFrame;

/**
 * Clase principal del sistema CineMagenta.
 * Inicia la aplicación gráfica cargando la ventana principal {@link MainFrame}.
 * 
 * <p>Utiliza {@link javax.swing.SwingUtilities#invokeLater(Runnable)} para garantizar
 * que la interfaz se construya en el hilo de eventos de Swing, asegurando compatibilidad
 * con el look and feel nativo del sistema operativo.</p>
 * 
 * @author Miguel
 */
public class CineMagenta {

    /**
     * Método principal que lanza la aplicación.
     * Crea y muestra la ventana principal del sistema.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}

