package Jogo;

import javax.swing.SwingUtilities;
import InterfaceGrafica.TelaInicio;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogo jogo = new Jogo();
            new TelaInicio(jogo);
        });
    }
}
