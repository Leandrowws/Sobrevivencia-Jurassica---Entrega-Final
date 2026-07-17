package Jogo;

import InterfaceGrafica.TelaFim;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import Personagens.DinossauroMovel;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import InterfaceGrafica.TelaCombate;
import InterfaceGrafica.TelaJogo;
import Personagens.Dinossauro;
import Personagens.Jogador;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogador;
    private long semente;
    private Combate combate;
    private String dificuldade;
    private File mapaAtual;
    private TelaJogo tela;
    private ArrayList<Thread> threadsDinossauros;
    private TelaCombate telaCombate;

    public void iniciar(int percepcao, String dificuldade) {
        semente = System.currentTimeMillis();
        this.dificuldade = dificuldade;
        jogador = new Jogador(percepcao);
        jogador.setJogo(this);
        tabuleiro = new Tabuleiro(10, semente);
        threadsDinossauros = new ArrayList<>();
        combate = new Combate(semente);
        combate.setJogo(this);
        Random random = new Random(semente);
        int numero = random.nextInt(5) + 1;
        try {
            mapaAtual = new File("src/Mapas/mapa" + numero + ".txt");
            tabuleiro.carregarMapa(mapaAtual, jogador);
        } catch (IOException e) {
            System.out.println("Erro ao carregar mapa.");
            e.printStackTrace();
        }
        tela = new TelaJogo(this, tabuleiro.getTamanho());
        tela.atualizar(tabuleiro);
        iniciarThreadsDinossauros();
    }

    public void reiniciarJogo() {
        pararThreadsDinossauros();
        int percepcao = jogador.getPercepcao();
        jogador = new Jogador(percepcao);
        jogador.setJogo(this);
        tabuleiro = new Tabuleiro(10, semente);
        combate = new Combate(semente);
        combate.setJogo(this);
        try {
            tabuleiro.carregarMapa(mapaAtual, jogador);
        } catch (IOException e) {
            System.out.println("Erro ao carregar o mapa.");
            e.printStackTrace();
        }
        tela.dispose();
        tela = new TelaJogo(this, tabuleiro.getTamanho());
        tela.atualizar(tabuleiro);
        iniciarThreadsDinossauros();
    }

    public void executarTurnos() {
        tela.atualizar(tabuleiro);
    }

    private synchronized void iniciarThreadsDinossauros() {
        threadsDinossauros.clear();

        for (Dinossauro d : tabuleiro.getDinossauros()) {
            if (d instanceof DinossauroMovel) {
                MovimentoDinossauro movimento = new MovimentoDinossauro(d, tabuleiro, 1000, this);
                Thread thread = new Thread(movimento);
                threadsDinossauros.add(thread);
                thread.start();
            }
        }
    }

    private synchronized void pararThreadsDinossauros() {

        for (Thread thread : threadsDinossauros) {
            thread.interrupt();
        }

        threadsDinossauros.clear();
    }

    public Jogador getJogador() {
        return jogador;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    private void processarCombate(Dinossauro inimigo, boolean jogadorEncontrou) {
        pararThreadsDinossauros();

        combate.iniciar(jogador, inimigo, tabuleiro, jogadorEncontrou);
        SwingUtilities.invokeLater(() -> {
            telaCombate = new TelaCombate(this, combate);
            telaCombate.setVisible(true);
        });
    }

    public void atualizarTelaPrincipal() {

        SwingUtilities.invokeLater(() -> tela.atualizar(tabuleiro));
        tela.atualizar(tabuleiro);

    }

    public synchronized void iniciarCombateDinossauro(Dinossauro d) {
        processarCombate(d, false);
    }

    public synchronized void finalizarCombate() {

        telaCombate = null;

        if (combate.jogadorMorreu()) {
            tela.dispose();
            new TelaFim(this, false);
            return;
        }

        atualizarTelaPrincipal();

        if (tabuleiro.todosDinossaurosDerrotados()) {
            tela.dispose();
            new TelaFim(this, true);
        } else {
            iniciarThreadsDinossauros();
        }
    }

    public void mensagem(String texto) {
        SwingUtilities.invokeLater(() -> {
            tela.adicionarMensagem(texto);
            if (telaCombate != null) {
                telaCombate.adicionarMensagem(texto);
            }
        });
    }

    public void desistir() {
        pararThreadsDinossauros();
        tela.dispose();
        new TelaFim(this, false);
    }

    public void processarComando(char comando) {
        Dinossauro inimigo = null;

        switch (Character.toUpperCase(comando)) {
            case 'W':
                inimigo = tabuleiro.moverJogador(jogador, -1, 0);
                break;
            case 'S':
                inimigo = tabuleiro.moverJogador(jogador, 1, 0);
                break;
            case 'A':
                inimigo = tabuleiro.moverJogador(jogador, 0, -1);
                break;
            case 'D':
                inimigo = tabuleiro.moverJogador(jogador, 0, 1);
                break;
            case 'H':
                jogador.usarKitMedico();
                break;
            default:
                return;
        }

        tela.atualizar(tabuleiro);

        if (inimigo != null) {

            processarCombate(inimigo, true);

            tela.atualizar(tabuleiro);
        }
    }
}