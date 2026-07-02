package Personagens;

import Jogo.Tabuleiro;

public class Troodonte extends Dinossauro {
    public Troodonte() {
        super(2, "Troodonte");
    }

    public char getSimbolo() {
        return 'T';
    }

    public Jogador moverDinossauro(Tabuleiro tabuleiro) {
        return tabuleiro.moverDinossauro(this);
    }

    public void morder(Jogador jogador) {
        jogador.perderVida(1);
    }

}
