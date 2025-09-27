package util;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Gestor de ventanas para la aplicación CineMagenta.
 * Permite abrir y gestionar instancias únicas de ventanas (JFrame) en la aplicación.
 * Si una ventana ya está abierta, la trae al frente en lugar de crear una nueva instancia.
 * 
 * <p>Utiliza un mapa estático para rastrear las instancias abiertas de cada clase de ventana.</p>
 * 
 * @author Miguel
 */
public class VentanaManager {

    private static final Map<Class<? extends JFrame>, JFrame> ventanas = new HashMap<>();

    public static void abrirVentana(Class<? extends JFrame> claseVentana, Supplier<JFrame> proveedor) {
        JFrame actual = ventanas.get(claseVentana);

        if (actual == null || !actual.isDisplayable()) {
            JFrame nueva = proveedor.get();
            ventanas.put(claseVentana, nueva);

            nueva.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    ventanas.remove(claseVentana);
                }
            });

            nueva.setVisible(true);
        } else {
            actual.toFront();
        }
    }
}