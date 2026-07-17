package Personagens;

import Jogo.Tabuleiro;

public class Velociraptor extends Dinossauro implements DinossauroMovel {
    public Velociraptor() {
        super(2, "Velociraptor");
    }

    public Jogador mover(Tabuleiro tabuleiro) {
        Jogador jogador = tabuleiro.moverDinossauro(this);
        if (jogador != null) {
            return jogador;
        }
        return tabuleiro.moverDinossauro(this);
    }

    public void morder(Jogador jogador) {
        jogador.perderVida(1);
    }

    public void receberDardo(int dano, Jogador jogador) {
        if (jogador.getJogo() != null) {
            jogador.getJogo().mensagem("O velociraptor desviou do disparo!");
        }
    }

    public String getNomeIcone() {
        return "velociraptor";
    }

}
