package util;

import javax.swing.*;

/**
 * Utilidad para mostrar diálogos estandarizados en el sistema.
 * Centraliza los mensajes de información, advertencia, error y confirmación.
 * 
 * @author Miguel
 */
public class DialogUtils {

    /**
     * Muestra un mensaje de información.
     * 
     * @param mensaje Texto del mensaje
     */
    public static void info(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de advertencia.
     * 
     * @param mensaje Texto del mensaje
     */
    public static void warning(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un mensaje de error.
     * 
     * @param mensaje Texto del mensaje
     */
    public static void error(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de confirmación.
     * 
     * @param mensaje Texto del mensaje
     * @return {@code true} si el usuario confirma, {@code false} si cancela
     */
    public static boolean confirmar(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Confirmar", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra un cuadro de entrada de texto.
     * 
     * @param mensaje Texto del mensaje
     * @return Texto ingresado por el usuario, o {@code null} si cancela
     */
    public static String input(String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje);
    }
}