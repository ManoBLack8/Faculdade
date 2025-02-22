import javax.swing.SwingUtilities;

import view.MenuViewer;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuViewer menu = new MenuViewer();
            menu.executar();
        });
    }
}
