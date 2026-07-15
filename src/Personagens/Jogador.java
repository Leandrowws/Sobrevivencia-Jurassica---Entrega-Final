package Personagens;

import Jogo.Jogo;
import Itens.Inventario;
import Itens.Arma;
import Itens.ArmaDardos;
import Itens.Item;
import Itens.KitMedico;
import Jogo.Tabuleiro;

public class Jogador extends Personagem {

    private int percepcao;
    private Inventario inventario;
    private int linhaAnterior;
    private int colunaAnterior;
    private Jogo jogo;

    public Jogador(int percepcao) {
        super(5);
        this.percepcao = percepcao;
        inventario = new Inventario();
    }

    public int getLinhaAnterior() {
        return linhaAnterior;
    }

    public int getColunaAnterior() {
        return colunaAnterior;
    }

    public void setPosicaoAnterior(int linha, int coluna) {
        linhaAnterior = linha;
        colunaAnterior = coluna;
    }

    public char getSimbolo() {
        return 'J';
    }

    public int getPercepcao() {
        return percepcao;
    }

    public int getMunicaoDardos() {

        ArmaDardos arma = inventario.getArmaDardos();

        if (arma == null)
            return 0;

        return arma.getMunicao();

    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void adicionarItem(Item item) {
        inventario.adicionar(item);
        if (jogo != null) {
            jogo.mensagem("Voce encontrou um(a) " + item.getNome());
        }
    }

    public boolean usarKitMedico() {

        KitMedico kit = inventario.getKitMedico();

        if (kit == null) {
            if (jogo != null) {
                jogo.mensagem("Você não possui kit médico!");
            }
            return false;
        }

        if (getVida() == 5) {
            if (jogo != null) {
                jogo.mensagem("Sua vida já está cheia!");
            }
            return false;
        }

        kit.usar(this);
        inventario.remover(kit);

        if (jogo != null) {
            jogo.mensagem("Vida recuperada!");
        }

        return true;
    }

    public void mostrarAcoesDisponiveis() {
        if (inventario.temBastao()) {
            System.out.println("1 - Atacar com o Bastão Elétrico");
        } else {
            System.out.println("1 - Atacar com as mãos");
        }
        if (inventario.temArmaDardos())
            System.out.println("2 - Atacar com a Arma de Dardos");
    }

    public int getNumeroAcoesAtaque() {
        return inventario.quantidadeArmas();
    }

    public boolean atacarCorpoACorpo(Dinossauro alvo, int dado) {
        Arma arma = inventario.getArmaCorpoACorpo();
        return arma.atacar(alvo, dado);
    }

    public boolean atirarDardo(Dinossauro alvo) {
        ArmaDardos arma = inventario.getArmaDardos();

        if (arma == null) {
            System.out.println("Você não possui arma.");
            return false;
        }

        return arma.atacar(alvo, 0);
    }

    public Dinossauro mover(Tabuleiro tabuleiro, int deltaLinha, int deltaColuna) {
        return tabuleiro.moverJogador(this, deltaLinha, deltaColuna);
    }

    public void fugir(Tabuleiro tabuleiro) {
        tabuleiro.reposicionarJogador(this, linhaAnterior, colunaAnterior);
    }
}