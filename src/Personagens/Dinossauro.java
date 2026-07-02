package Personagens;

import Jogo.Tabuleiro;

public abstract class Dinossauro extends Personagem {

    private String especie;

    public String getEspecie() {
        return especie;
    }

    public Dinossauro(int v, String e) {
        super(v);
        especie = e;
    }

    public boolean receberAtaqueBasico(int dano) {
        perderVida(dano);
        return true;
    }

    public void receberBastaoEletrico(int dano) {
        perderVida(dano);

    }

    public boolean emboscaJogador(boolean jogadorEncontrou) {
        return jogadorEncontrou;
    }

    public boolean permiteFuga() {
        return true;
    }

    public void receberDardo(int dano) {
        perderVida(dano);
        System.out.println();
        System.out.println("O dardo atingiu o alvo!");
    }

    public abstract Jogador moverDinossauro(Tabuleiro tabuleiro);

    public abstract void morder(Jogador jogador);
}
