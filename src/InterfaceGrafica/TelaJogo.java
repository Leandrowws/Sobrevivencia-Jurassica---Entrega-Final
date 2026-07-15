package InterfaceGrafica;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import Jogo.Jogo;
import Jogo.Posicao;
import Jogo.Tabuleiro;
import Personagens.Jogador;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JScrollPane;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Icon;

public class TelaJogo extends JFrame {
    private JButton[][] botoes;
    private Jogo jogo;
    private boolean modoDebug = false;
    private Tabuleiro tabuleiroAtual;
    private JPanel painelTabuleiro;
    private JPanel painelInfo;
    private JLabel Dificuldade;
    private JLabel Vida;
    private JLabel Municao;
    private JButton btnFechar;
    private JTextArea areaMensagens;
    private final Icon parede = carregarIcone("/InterfaceGrafica/Imagens/parede.png", 100, 100);
    private final Icon jogador = carregarIcone("/InterfaceGrafica/Imagens/jogador.png", 100, 100);
    private final Icon caixa = carregarIcone("/InterfaceGrafica/Imagens/caixa.png", 100, 100);
    private final Icon chao = carregarIcone("/InterfaceGrafica/Imagens/chao.png", 100, 100);
    private final Icon compsognato = carregarIcone("/InterfaceGrafica/Imagens/compsognato.png", 100, 100);
    private final Icon troodonte = carregarIcone("/InterfaceGrafica/Imagens/troodonte.png", 100, 100);
    private final Icon velociraptor = carregarIcone("/InterfaceGrafica/Imagens/velociraptor.png", 100, 100);
    private final Icon trex = carregarIcone("/InterfaceGrafica/Imagens/trex.png", 100, 100);

    public TelaJogo(Jogo jogo, int tamanho) {

        this.jogo = jogo;

        setTitle("Sobrevivência Jurássica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        painelTabuleiro = new JPanel(new GridLayout(tamanho, tamanho));

        botoes = new JButton[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {

            for (int j = 0; j < tamanho; j++) {
                botoes[i][j] = new JButton();
                botoes[i][j].setFocusable(false);
                botoes[i][j].setBorderPainted(false);
                botoes[i][j].setContentAreaFilled(false);
                botoes[i][j].setPreferredSize(new Dimension(100, 100));
                painelTabuleiro.add(botoes[i][j]);
            }
        }

        add(painelTabuleiro, BorderLayout.CENTER);
        criarPainelInfo();
        add(painelInfo, BorderLayout.EAST);

        configurarTeclas();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Icon carregarIcone(String caminho, int largura, int altura) {
        Image img = new ImageIcon(getClass().getResource(caminho)).getImage();
        Image imgRedimensionada = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(imgRedimensionada);
    }

    private void criarPainelInfo() {

        painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setPreferredSize(new Dimension(240, 0));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelInfo.add(criarPainelStatus());
        painelInfo.add(Box.createVerticalStrut(10));
        painelInfo.add(criarPainelMensagens());

        add(painelInfo, BorderLayout.EAST);
    }

    private JPanel criarPainelStatus() {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder("Status"));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dificuldade = new JLabel("Dificuldade: " + jogo.getDificuldade());
        Vida = new JLabel("Vida: " + jogo.getJogador().getVida());
        Municao = new JLabel("Munição: " + jogo.getJogador().getMunicaoDardos());

        Dificuldade.setAlignmentX(Component.LEFT_ALIGNMENT);
        Vida.setAlignmentX(Component.LEFT_ALIGNMENT);
        Municao.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnFechar = new JButton("Fechar Jogo");
        btnFechar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnFechar.addActionListener(e -> jogo.desistir());

        painel.add(Dificuldade);
        painel.add(Box.createVerticalStrut(8));
        painel.add(Vida);
        painel.add(Box.createVerticalStrut(8));
        painel.add(Municao);
        painel.add(Box.createVerticalStrut(15));
        painel.add(btnFechar);

        return painel;
    }

    private JPanel criarPainelMensagens() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Mensagens"));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        areaMensagens = new JTextArea();
        areaMensagens.setEditable(false);
        areaMensagens.setFocusable(false);
        areaMensagens.setLineWrap(true);
        areaMensagens.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaMensagens);

        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void configurarTeclas() {

        InputMap input = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actions = getRootPane().getActionMap();

        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "cima");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "esquerda");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "baixo");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "direita");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "debug");

        actions.put("cima", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jogo.processarComando('W');
            }
        });

        actions.put("baixo", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jogo.processarComando('S');
            }
        });

        actions.put("esquerda", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jogo.processarComando('A');
            }
        });

        actions.put("direita", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jogo.processarComando('D');
            }
        });

        actions.put("debug", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                alternarModoDebug();
            }
        });

    }

    private void alternarModoDebug() {
        modoDebug = !modoDebug;

        if (tabuleiroAtual != null) {
            atualizar(tabuleiroAtual);
        }
    }

    public void atualizar(Tabuleiro tabuleiro) {

        this.tabuleiroAtual = tabuleiro;
        int tamanho = tabuleiro.getTamanho();

        Jogador jogadorAtual = jogo.getJogador();
        boolean[][] visivel = tabuleiro.calcularVisibilidade(jogadorAtual);

        for (int i = 0; i < tamanho; i++) {

            for (int j = 0; j < tamanho; j++) {

                boolean posicaoVisivel = modoDebug || visivel[i][j];
                atualizarBotao(botoes[i][j], tabuleiro.getPosicao(i, j), posicaoVisivel);

            }
        }

        atualizarInfo();

        revalidate();
        repaint();
    }

    private void atualizarInfo() {
        Vida.setText("Vida: " + jogo.getJogador().getVida());
        Municao.setText("Munição: " + jogo.getJogador().getMunicaoDardos());
    }

    private void atualizarBotao(JButton botao, Posicao posicao, boolean visivel) {

        if (!visivel) {
            botao.setIcon(null);
            botao.setText("");
            botao.setOpaque(true);
            botao.setContentAreaFilled(true);
            botao.setBackground(Color.BLACK);
            return;
        }

        botao.setText("");
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);

        botao.setText("");
        if (posicao.isParede()) {
            botao.setIcon(parede);
        } else if (posicao.getPersonagem() != null) {
            if (posicao.getPersonagem().getSimbolo() == 'J') {
                botao.setIcon(jogador);
            } else if (posicao.getPersonagem().getSimbolo() == 'C') {
                botao.setIcon(compsognato);
            } else if (posicao.getPersonagem().getSimbolo() == 'T') {
                botao.setIcon(troodonte);
            } else if (posicao.getPersonagem().getSimbolo() == 'V') {
                botao.setIcon(velociraptor);
            } else if (posicao.getPersonagem().getSimbolo() == 'R') {
                botao.setIcon(trex);
            }
        } else if (posicao.getCaixa() != null) {
            botao.setIcon(caixa);
        } else {
            botao.setIcon(chao);
        }
    }

    public void adicionarMensagem(String texto) {
        areaMensagens.append(texto + "\n");
    }
}