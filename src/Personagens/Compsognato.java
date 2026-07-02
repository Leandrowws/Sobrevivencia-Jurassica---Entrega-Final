package Personagens;

import Jogo.Tabuleiro;

public class Compsognato extends Dinossauro {
    public Compsognato() {
        super(1, "Compsognato");
    }

    public char getSimbolo() {
        return 'C';
    }

    public Jogador moverDinossauro(Tabuleiro tabuleiro) {
        return tabuleiro.moverDinossauro(this);
    }

    public void morder(Jogador jogador) {
        jogador.perderVida(1);
    }

    public boolean emboscaJogador(boolean jogadorEncontrou) {
        return false;
    }

    public boolean permiteFuga() {
        return false;
    }

}
