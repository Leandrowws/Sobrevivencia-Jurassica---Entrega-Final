package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Jogo.Jogo;

public class TelaFim extends JFrame {

    private Jogo jogo;
    private boolean vitoria;

    private JLabel lblTitulo;
    private JButton btnNovoJogo;
    private JButton btnReiniciar;

    public TelaFim(Jogo jogo, boolean vitoria) {
        this.jogo = jogo;
        this.vitoria = vitoria;

        configurarJanela();
        criarComponentes();

        setVisible(true);
    }

    private void configurarJanela() {

        if (vitoria) {
            setTitle("Vitória!");
        } else {
            setTitle("Derrota...");
        }

        setSize(400, 200);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    private void criarComponentes() {

        if (vitoria) {
            lblTitulo = new JLabel("Todos os dinossauros foram derrotados!", SwingConstants.CENTER);
        } else {
            lblTitulo = new JLabel("Você não resistiu aos dinossauros...!", SwingConstants.CENTER);
        }

        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2));

        btnNovoJogo = new JButton("Novo Jogo");
        btnNovoJogo.addActionListener(e -> novoJogo());

        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(e -> reiniciar());

        painelBotoes.add(btnNovoJogo);
        painelBotoes.add(btnReiniciar);

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void novoJogo() {
        dispose();
        new TelaInicio(jogo);
    }

    private void reiniciar() {
        dispose();
        jogo.reiniciarJogo();
    }
}