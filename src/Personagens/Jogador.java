package Personagens;

import Jogo.Caixa;
import Jogo.Posicao;
import Jogo.Tabuleiro;
import Jogo.TipoCaixa;

public class Jogador extends Personagem {

    private int percepcao;
    private boolean possuiBastao;
    private boolean possuiKitMedico;
    private boolean possuiArmaDardos;
    private int linhaAnterior;
    private int colunaAnterior;
    private int municaoDardos;

    public Jogador(int percepcao) {
        super(5);
        this.percepcao = percepcao;
        possuiBastao = false;
        possuiKitMedico = false;
        possuiArmaDardos = false;
        municaoDardos = 0;
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
        return possuiKitMedico;
    }

    public boolean possuiBastao() {
        return possuiBastao;
    }

    public boolean possuiArmaDardos() {
        return possuiArmaDardos;
    }

    public int getMunicaoDardos() {
        return municaoDardos;
    }

    public void receberKitMedico() {
        possuiKitMedico = true;
    }

    public void receberBastao() {
        possuiBastao = true;
    }

    public void receberArmaDardos() {
        if (!possuiArmaDardos) {
            possuiArmaDardos = true;
            System.out.println();
            System.out.println("Você encontrou uma arma de dardos e duas munições (use com cuidado)!");
        } else {
            System.out.println();
            System.out.println("Você recebeu mais duas munições para a arma de dardos!");
        }
        adicionarMunicao();
    }

    public void adicionarMunicao() {
        municaoDardos += 2;
    }

    public boolean usarKitMedico() {

        if (!possuiKitMedico) {
            System.out.println();
            System.out.println("Voce nao possui kit medico!");
            return false;
        }

        if (getVida() >= 5) {
            System.out.println();
            System.out.println("Sua vida ja esta cheia!");
            return false;
        }

        recuperarVida();
        if (getVida() < 5)
            recuperarVida();

        possuiKitMedico = false;
        System.out.println();
        System.out.println("Voce recuperou vida!");

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
        if (!possuiBastao()) {
            if (dado <= 2) {
                System.out.println("Você errou o ataque!");
            } else if (dado <= 5) {
                if (alvo.receberAtaqueBasico(1))
                    System.out.println("Você causou 1 de dano!");
            } else {
                if (alvo.receberAtaqueBasico(2))
                    System.out.println("Acerto crítico! Causou 2 de dano!");
            }
        } else {
            if (dado <= 1) {
                System.out.println("Você errou!");
            } else if (dado <= 4) {
                System.out.println("O bastão acertou! Causou 1 de dano!");
                alvo.receberBastaoEletrico(1);
            } else {
                System.out.println("O bastão acertou de forma crítica! Causou 2 de dano!");
                alvo.receberBastaoEletrico(2);
            }
        }
        return true;
    }

    public boolean atirarDardo(Dinossauro alvo) {
        if (!possuiArmaDardos()) {
            System.out.println("Você não possui uma arma de dardos!");
            return false;
        }
        if (municaoDardos <= 0) {
            System.out.println("Sem munição!");
            return false;
        }
        municaoDardos--;
        alvo.receberDardo(2);
        return true;
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
            if (caixa.getTipo() == TipoCaixa.COMPSOGNATO_SURPRESA) {
                destino.setCaixa(null);
                System.out.println("Você encontrou um Compsognato dentro da caixa!");
                return new Compsognato();
            }
            caixa.abrir(this);
            destino.setCaixa(null);
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