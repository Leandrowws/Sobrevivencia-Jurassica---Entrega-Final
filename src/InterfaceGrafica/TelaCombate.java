package InterfaceGrafica;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Jogo.Combate;
import Jogo.Jogo;

public class TelaCombate extends JFrame {
    private Jogo jogo;
    private Combate combate;

    private JLabel Titulo;
    private JLabel VidaJogador;
    private JLabel VidaDinossauro;
    private JTextArea areaMensagens;

    private JButton Atacar;
    private JButton Dardo;
    private JButton Kit;
    private JButton Fugir;

    public TelaCombate(Jogo jogo, Combate combate) {
        this.jogo = jogo;
        this.combate = combate;

        configurarJanela();
        criarComponentes();
        atualizarInformacoes();

        setVisible(true);
    }

    private void configurarJanela() {

        setTitle("Combate");

        setSize(500, 350);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(new BorderLayout());
    }

    public void atualizarInformacoes() {

        Titulo.setText("Combate contra " + combate.getNomeDinossauro());

        VidaJogador.setText("Vida do Jogador: " + combate.getVidaJogador());

        VidaDinossauro.setText("Vida do " + combate.getNomeDinossauro() + ": " + combate.getVidaDinossauro());

        Dardo.setEnabled(combate.getNumeroAcoes() >= 2);

        Fugir.setEnabled(combate.podeFugir());
    }

    private void atualizarTela() {

        atualizarInformacoes();

        if (!combate.combateAtivo()) {
            dispose();
            jogo.finalizarCombate();
        }

    }

    private void criarComponentes() {

        Titulo = new JLabel("", SwingConstants.CENTER);

        VidaJogador = new JLabel();

        VidaDinossauro = new JLabel();

        add(Titulo, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new GridLayout(2, 1));

        painelCentro.add(VidaJogador);

        painelCentro.add(VidaDinossauro);

        add(painelCentro, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 2));

        Atacar = new JButton("Atacar");
        Atacar.addActionListener(e -> {
            combate.acaoAtacarCorpoACorpo();
            atualizarTela();
        });

        Dardo = new JButton("Dardo");
        Dardo.addActionListener(e -> {
            combate.acaoDardo();
            atualizarTela();
        });

        Kit = new JButton("Kit Médico");
        Kit.addActionListener(e -> {
            combate.acaoKitMedico();
            atualizarTela();
        });

        Fugir = new JButton("Fugir");
        Fugir.addActionListener(e -> {
            combate.fugir();
            atualizarTela();
        });

        painelBotoes.add(Atacar);

        painelBotoes.add(Dardo);

        painelBotoes.add(Kit);

        painelBotoes.add(Fugir);

        areaMensagens = new JTextArea();
        areaMensagens.setEditable(false);
        areaMensagens.setFocusable(false);
        areaMensagens.setLineWrap(true);
        areaMensagens.setWrapStyleWord(true);
        areaMensagens.setRows(5);

        JScrollPane scrollMensagens = new JScrollPane(areaMensagens);

        JPanel painelInferior = new JPanel(new BorderLayout());

        painelInferior.add(painelBotoes, BorderLayout.CENTER);

        painelInferior.add(scrollMensagens, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);
    }

    public void adicionarMensagem(String texto) {
        areaMensagens.append(texto + "\n");
    }
}