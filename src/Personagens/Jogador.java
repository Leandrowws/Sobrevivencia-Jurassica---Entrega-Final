package Personagens;

import Itens.Punhos;
import Itens.Arma;
import java.util.ArrayList;
import Itens.ArmaDardos;
import Itens.BastaoEletrico;
import Itens.Item;
import Itens.KitMedico;
import Jogo.Posicao;
import Jogo.Tabuleiro;
import Caixas.Caixa;

public class Jogador extends Personagem {

    private int percepcao;
    private ArrayList<Item> inventario;
    private int linhaAnterior;
    private int colunaAnterior;

    public Jogador(int percepcao) {
        super(5);
        this.percepcao = percepcao;
        inventario = new ArrayList<>();
    }

    private <T extends Item> T buscarItem(Class<T> tipo) {

        for (Item item : inventario) {

            if (tipo.isInstance(item)) {
                return tipo.cast(item);
            }
        }

        return null;
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

    public boolean possuiKitMedico() {
        return buscarItem(KitMedico.class) != null;
    }

    public boolean possuiBastao() {
        return buscarItem(BastaoEletrico.class) != null;
    }

    public boolean possuiArmaDardos() {
        return buscarItem(ArmaDardos.class) != null;
    }

    public int getMunicaoDardos() {

        ArmaDardos arma = buscarItem(ArmaDardos.class);

        if (arma == null)
            return 0;

        return arma.getMunicao();

    }

    public void adicionarItem(Item item) {
        inventario.add(item);
        System.out.println("Voce encontrou um(a) " + item.getNome());
    }

    public boolean usarKitMedico() {

        KitMedico kit = buscarItem(KitMedico.class);

        if (kit == null) {
            System.out.println("Você não possui kit médico!");
            return false;
        }

        if (getVida() == 5) {
            System.out.println("Sua vida já está cheia!");
            return false;
        }

        kit.usar(this);
        inventario.remove(kit);

        System.out.println("Vida recuperada!");

        return true;
    }

    public void mostrarAcoesDisponiveis() {
        if (possuiBastao()) {
            System.out.println("1 - Atacar com o Bastão Elétrico");
        } else {
            System.out.println("1 - Atacar com as mãos");
        }
        if (possuiArmaDardos())
            System.out.println("2 - Atacar com a Arma de Dardos");
    }

    public boolean atacarCorpoACorpo(Dinossauro alvo, int dado) {
        Arma arma = buscarItem(BastaoEletrico.class);
        if (arma != null) {
            return arma.atacar(alvo, dado);
        } else {
            arma = new Punhos();
            return arma.atacar(alvo, dado);
        }
    }

    public boolean atirarDardo(Dinossauro alvo) {
        ArmaDardos arma = buscarItem(ArmaDardos.class);
        if (arma == null) {
            System.out.println("Você não possui arma.");
            return false;
        }
        return arma.atacar(alvo, 0);
    }

    public Dinossauro mover(Tabuleiro tabuleiro, int deltaLinha, int deltaColuna) {

        int novaLinha = getLinha() + deltaLinha;
        int novaColuna = getColuna() + deltaColuna;

        if (novaLinha < 0 || novaLinha >= tabuleiro.getTamanho()
                || novaColuna < 0 || novaColuna >= tabuleiro.getTamanho()) {
            return null;
        }

        Posicao destino = tabuleiro.getPosicao(novaLinha, novaColuna);

        if (destino.isParede()) {
            return null;
        }

        if (destino.getPersonagem() instanceof Dinossauro) {
            return (Dinossauro) destino.getPersonagem();
        }

        if (destino.getCaixa() != null) {
            Caixa caixa = destino.getCaixa();
            Dinossauro d = null;
            d = caixa.abrir(this);
            destino.setCaixa(null);
            if (d != null) {
                return d;
            }
        }
        setPosicaoAnterior(getLinha(), getColuna());
        tabuleiro.getPosicao(getLinha(), getColuna()).setPersonagem(null);
        setPosicao(novaLinha, novaColuna);
        tabuleiro.getPosicao(novaLinha, novaColuna).setPersonagem(this);
        return null;
    }

    public void fugir(Tabuleiro tabuleiro) {
        tabuleiro.reposicionarJogador(this, linhaAnterior, colunaAnterior);
    }
}