package Personagens;

import Jogo.Tabuleiro;

public class Troodonte extends Dinossauro implements DinossauroMovel {
    public Troodonte() {
        super(2, "Troodonte");
    }

    public char getSimbolo() {
        return 'T';
    }

    public Jogador mover(Tabuleiro tabuleiro) {
        return tabuleiro.moverDinossauroEmDirecao(this, tabuleiro.getJogador());
    }

    public void morder(Jogador jogador) {
        jogador.perderVida(1);
    }

    public String getNomeIcone() {
        return "troodonte";
    }

}
