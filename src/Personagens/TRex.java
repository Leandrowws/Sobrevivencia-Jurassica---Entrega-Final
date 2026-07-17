package Personagens;

import Jogo.Tabuleiro;

public class TRex extends Dinossauro {

    public TRex() {
        super(3, "Indomável Tiranossauro Rex");
    }

    public char getSimbolo() {
        return 'R';
    }

    public Jogador moverDinossauro(Tabuleiro tabuleiro) {
        return null;
    }

    public void morder(Jogador jogador) {
        jogador.perderVida(2);
    }

    public boolean receberAtaqueBasico(int dano, Jogador jogador) {
        if (jogador.getJogo() != null) {
            jogador.getJogo().mensagem("Suas mãos não são capazes de ferir o indomável TRex!");
        }
        return false;
    }

    public String getNomeIcone() {
        return "trex";
    }

}
