package Personagens;

import Jogo.Tabuleiro;

public class Velociraptor extends Dinossauro {
    public Velociraptor() {
        super(2, "Velociraptor");
    }

    public char getSimbolo() {
        return 'V';
    }

    public Jogador moverDinossauro(Tabuleiro tabuleiro) {
        Jogador jogador = tabuleiro.moverDinossauro(this);
        if (jogador != null) {
            return jogador;
        }
        return tabuleiro.moverDinossauro(this);
    }

    public void morder(Jogador jogador) {
        jogador.perderVida(1);
    }

    public void receberDardo(int dano) {
        System.out.println("O velociraptor desviou do disparo!");
    }

}
