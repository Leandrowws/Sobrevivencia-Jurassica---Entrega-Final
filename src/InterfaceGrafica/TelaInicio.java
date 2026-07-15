package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import Jogo.Jogo;

public class TelaInicio extends JFrame {

    private Jogo jogo;

    private JLabel Titulo;
    private JButton Facil;
    private JButton Medio;
    private JButton Dificil;

    public TelaInicio(Jogo jogo) {
        this.jogo = jogo;

        configurarJanela();
        criarComponentes();

        setVisible(true);
    }

    private void configurarJanela() {

        setTitle("Sobrevivência Jurássica");

        setSize(400, 200);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    private void criarComponentes() {

        Titulo = new JLabel("Escolha a dificuldade", SwingConstants.CENTER);

        add(Titulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 3));

        Facil = new JButton("Fácil");
        Facil.addActionListener(e -> iniciarJogo(3, "Fácil"));

        Medio = new JButton("Médio");
        Medio.addActionListener(e -> iniciarJogo(2, "Médio"));

        Dificil = new JButton("Difícil");
        Dificil.addActionListener(e -> iniciarJogo(1, "Difícil"));

        painelBotoes.add(Facil);
        painelBotoes.add(Medio);
        painelBotoes.add(Dificil);

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void iniciarJogo(int percepcao, String dificuldade) {
        dispose();
        jogo.iniciar(percepcao, dificuldade);
    }
}