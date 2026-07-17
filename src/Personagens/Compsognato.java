package Personagens;

import Jogo.Tabuleiro;

public class Compsognato extends Dinossauro implements DinossauroMovel {
    public Compsognato() {
        super(1, "Compsognato");
    }

    public Jogador mover(Tabuleiro tabuleiro) {
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

    public String getNomeIcone() {
        return "compsognato";
    }

}
